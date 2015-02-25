package org.adorsys.adprocmt.api;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adprocmt.jpa.PrcmtAbstractDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItem;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctn;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryHstryEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDeliveryManager {
	
	@Inject
	private PrcmtDeliveryEJB deliveryEJB;
	
	@Inject
	private PrcmtDlvryItemEJB dlvryItemEJB; 
	
	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private PrcmtDeliveryHstryEJB deliveryHstryEJB;

	/**
	 * 
	 * Process an incoming delivery. The delivery holds :
	 * 	- a partial list of delivery items holders,
	 * 	- a complete list of receiving organization units holders,
	 * 
	 * The
	 * @param deliveryHolder
	 * @return
	 */
	public PrcmtDeliveryHolder updateDelivery(PrcmtDeliveryHolder deliveryHolder){
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		
		// Create the delivery object if necessary
		delivery = createDeliveryObject(delivery, currentLoginName, now);
		deliveryHolder.setDelivery(delivery);
		boolean modified = false;
		
		modified |= synchProcmtOrderNbrs(deliveryHolder);
		
		modified |= synchRcvngOrgUnits(deliveryHolder);
		
		boolean itemModified = deleteHolders(deliveryHolder);
		
		List<PrcmtDeliveryItemHolder> deliveryItems = deliveryHolder.getDeliveryItems();
		if(deliveryItems==null) deliveryItems=new ArrayList<PrcmtDeliveryItemHolder>();
		
		for (PrcmtDeliveryItemHolder itemHolder : deliveryItems) {
			PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();

			if(StringUtils.isBlank(dlvryItem.getDlvryNbr()))
				dlvryItem.setDlvryNbr(delivery.getDlvryNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(dlvryItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(dlvryItem.getId())){
				// todo check mdified
				PrcmtDlvryItem persDi = dlvryItemEJB.findById(dlvryItem.getId());
				if(persDi==null) throw new IllegalStateException("Missing delivery item with id: " + dlvryItem.getId());
				if(!dlvryItem.contentEquals(persDi)){
					dlvryItem.copyTo(persDi);
					dlvryItem = dlvryItemEJB.update(persDi);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(dlvryItem.getDlvryItemNbr())) {
					PrcmtDlvryItem persDi = dlvryItemEJB.findById(dlvryItem.getId());
					if(persDi!=null){
						if(!dlvryItem.contentEquals(persDi)){
							dlvryItem.copyTo(persDi);
							dlvryItem = dlvryItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						dlvryItem.evlte();//evaluate different amount before save
						dlvryItem.setDlvryNbr(delivery.getDlvryNbr());
						dlvryItem = dlvryItemEJB.create(dlvryItem);
						itemModified = true;
					}
				} else {
					dlvryItem.evlte();//evaluate different amount before save
					dlvryItem = dlvryItemEJB.create(dlvryItem);
					itemModified = true;
				}
			}

			itemHolder.setDlvryItem(dlvryItem);
			itemModified |= processItems2PoItems(itemHolder);
			itemModified |= processItems2RecvngOus(itemHolder, deliveryHolder);
			itemModified |= processItem2StrgSctns(itemHolder);
		}
		
		if(itemModified){
			// Create or update the delivery.
			recomputeDelivery(delivery);
			delivery.setDlvryStatus(BaseProcessStatusEnum.ONGOING.name());
			delivery = deliveryEJB.update(delivery);
			deliveryHolder.setDelivery(delivery);		
		}
		if(modified || itemModified){
			createModifiedDeliveryHistory(delivery);
		}
		return deliveryHolder;
	}
	
	private boolean deleteHolders(PrcmtDeliveryHolder deliveryHolder){
		List<PrcmtDeliveryItemHolder> deliveryItems = deliveryHolder.getDeliveryItems();
		List<PrcmtDeliveryItemHolder> diToRemove = new ArrayList<PrcmtDeliveryItemHolder>();
		boolean modified = false;
		for (PrcmtDeliveryItemHolder itemHolder : deliveryItems) {
			if(itemHolder.isDeleted()){
				PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
				String id = StringUtils.isNotBlank(dlvryItem.getId())?dlvryItem.getId():dlvryItem.getDlvryItemNbr();
				if(StringUtils.isNotBlank(id)){
					dlvryItemEJB.deleteById(dlvryItem.getId());
					modified = true;					
				}
				diToRemove.add(itemHolder);
			}
		}
		deliveryItems.removeAll(diToRemove);
		return modified;
	}

	private boolean processItem2StrgSctns(PrcmtDeliveryItemHolder itemHolder) {
		PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		boolean modified = false;
		List<PrcmtDlvryItem2StrgSctnHolder> strgSctns = itemHolder.getStrgSctns();
		List<PrcmtDlvryItem2StrgSctnHolder> itemsToRemove = new ArrayList<PrcmtDlvryItem2StrgSctnHolder>();
		for (PrcmtDlvryItem2StrgSctnHolder ouHolder : strgSctns) {
			PrcmtDlvryItem2StrgSctn strgSctn = ouHolder.getStrgSctn();
			PrcmtDlvryItem2StrgSctn persStrgSctn = dlvryItemEJB.findDlvryItem2StrgSctn(dlvryItemNbr, strgSctn.getStrgSection());
			if(ouHolder.isDeleted()){
				if(persStrgSctn!=null){
					dlvryItemEJB.deletePoItem(dlvryItemNbr, strgSctn.getStrgSection());
					modified=true;
				}
				itemsToRemove.add(ouHolder);
				continue;
			}
			if(persStrgSctn==null){
				strgSctn = dlvryItemEJB.addDlvryItem2StrgSctn(dlvryItem, strgSctn.getStrgSection(), strgSctn.getStkQtyPreDlvry(), strgSctn.getQtyStrd());
				ouHolder.setStrgSctn(strgSctn);
				modified=true;
			} else {
				if(!strgSctn.contentEquals(persStrgSctn)){
					strgSctn = dlvryItemEJB.updateDlvryItem2StrgSctn(dlvryItem, strgSctn);
					ouHolder.setStrgSctn(strgSctn);
					modified=true;
				}
			}
		}
		strgSctns.removeAll(itemsToRemove);
		return modified;
	}

	private boolean processItems2RecvngOus(PrcmtDeliveryItemHolder itemHolder, PrcmtDeliveryHolder deliveryHolder) {
		PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		boolean modified = false;
		List<PrcmtDlvryItem2RcvngOrgUnitHolder> recvngOus = itemHolder.getRecvngOus();
		if(!recvngOus.isEmpty()) {
			List<PrcmtDlvryItem2RcvngOrgUnitHolder> itemsToRemove = new ArrayList<PrcmtDlvryItem2RcvngOrgUnitHolder>();
			for (PrcmtDlvryItem2RcvngOrgUnitHolder ouHolder : recvngOus) {
				PrcmtDlvryItem2Ou orgUnit = ouHolder.getRcvngOrgUnit();
				PrcmtDlvryItem2Ou persOrgUnit = dlvryItemEJB.findDlvryItem2Ou(dlvryItemNbr, orgUnit.getRcvngOrgUnit());
				if(ouHolder.isDeleted()){
					if(persOrgUnit!=null){
						dlvryItemEJB.deleteOu(dlvryItemNbr, orgUnit.getRcvngOrgUnit());
						modified=true;
					}
					itemsToRemove.add(ouHolder);
					continue;
				}
				if(persOrgUnit==null){
					orgUnit = dlvryItemEJB.addDlvryItem2Ou(dlvryItem, orgUnit.getRcvngOrgUnit(), orgUnit.getQtyDlvrd(), orgUnit.getFreeQty());
					ouHolder.setRcvngOrgUnit(orgUnit);
					modified=true;
				} else {
					if(!orgUnit.contentEquals(persOrgUnit)){
						orgUnit = dlvryItemEJB.updateDlvryItem2Ou(dlvryItem, orgUnit);
						ouHolder.setRcvngOrgUnit(orgUnit);
						modified=true;
					}
				}
			}
			recvngOus.removeAll(itemsToRemove);
		} else { // Share according to the proportions of the receiving orgunit.
			recvngOus = new ArrayList<PrcmtDlvryItem2RcvngOrgUnitHolder>();
			List<PrcmtDlvryRcvngOrgUnitHolder> rcvngOrgUnitHolders = deliveryHolder.getRcvngOrgUnits();
			for (PrcmtDlvryRcvngOrgUnitHolder rcvngOrgUnitHolder : rcvngOrgUnitHolders) {
				PrcmtDlvry2Ou rcvngOrgUnit = rcvngOrgUnitHolder.getRcvngOrgUnit();
				PrcmtDlvryItem2RcvngOrgUnitHolder itemOuHolder = new PrcmtDlvryItem2RcvngOrgUnitHolder();
				PrcmtDlvryItem2Ou dlvryItem2Ou = new PrcmtDlvryItem2Ou();
				itemOuHolder.setRcvngOrgUnit(dlvryItem2Ou);
				dlvryItem2Ou.setDlvryItemNbr(dlvryItemNbr);
				dlvryItem2Ou.setFreeQty(BigDecimalUtils.basePercentOfRatePct(rcvngOrgUnit.getQtyPct(), dlvryItem.getFreeQty(), RoundingMode.HALF_EVEN));
				dlvryItem2Ou.setQtyDlvrd(BigDecimalUtils.basePercentOfRatePct(rcvngOrgUnit.getQtyPct(), dlvryItem.getQtyDlvrd(), RoundingMode.HALF_EVEN));
				dlvryItem2Ou.setRcvngOrgUnit(rcvngOrgUnit.getRcvngOrgUnit());
				recvngOus.add(itemOuHolder);
			}
		}
		return modified;
	}

	private boolean processItems2PoItems(PrcmtDeliveryItemHolder itemHolder) {
		PrcmtDlvryItem dlvryItem = itemHolder.getDlvryItem();
		String dlvryItemNbr = dlvryItem.getDlvryItemNbr();
		boolean modified = false;
		List<PrcmtDlvryItem2PoItemHolder> poItems = itemHolder.getPoItems();
		List<PrcmtDlvryItem2PoItemHolder> itemsToRemove = new ArrayList<PrcmtDlvryItem2PoItemHolder>();
		for (PrcmtDlvryItem2PoItemHolder poItemHolder : poItems) {
			PrcmtDlvryItem2POItem poItem = poItemHolder.getPoItem();
			PrcmtDlvryItem2POItem persPOItem = dlvryItemEJB.findDlvryItem2POItem(dlvryItemNbr, poItem.getPoItemNbr());
			if(poItemHolder.isDeleted()){
				if(persPOItem!=null){
					dlvryItemEJB.deletePoItem(dlvryItemNbr, poItem.getPoItemNbr());
					modified=true;
				}
				itemsToRemove.add(poItemHolder);
				continue;
			}
			if(persPOItem==null){
				poItem = dlvryItemEJB.addDlvryItem2POItem(dlvryItem, poItem.getPoItemNbr(), poItem.getQtyOrdered(), poItem.getQtyDlvrd(), poItem.getFreeQty());
				poItemHolder.setPoItem(poItem);
				modified=true;
			} else {
				if(!poItem.contentEquals(persPOItem)){
					poItem = dlvryItemEJB.updateDlvryItem2POItem(dlvryItem, poItem);
					poItemHolder.setPoItem(poItem);
					modified=true;
				}
			}
		}
		poItems.removeAll(itemsToRemove);
		return modified;
	}

	private boolean synchRcvngOrgUnits(PrcmtDeliveryHolder deliveryHolder){
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		String dlvryNbr = delivery.getDlvryNbr();
		boolean modified = false;
		
		// Process associated org units.
		List<PrcmtDlvryRcvngOrgUnitHolder> rcvngOrgUnits = deliveryHolder.getRcvngOrgUnits();
		List<PrcmtDlvryRcvngOrgUnitHolder> orgUnitToRemove = new ArrayList<PrcmtDlvryRcvngOrgUnitHolder>();
		if(!rcvngOrgUnits.isEmpty()){
			for (PrcmtDlvryRcvngOrgUnitHolder ouHolder : rcvngOrgUnits) {
				PrcmtDlvry2Ou dlvry2Ou = deliveryEJB.findOrgUnit(dlvryNbr, ouHolder.getRcvngOrgUnit().getRcvngOrgUnit());
				if(ouHolder.isDeleted()){
					if(dlvry2Ou!=null){
						deliveryEJB.deleteOrgUnit(dlvry2Ou.getId());
						orgUnitToRemove.add(ouHolder);
						modified = true;
						continue;
					}
				}
				if(dlvry2Ou==null){
					dlvry2Ou = deliveryEJB.addOrgUnit(delivery, ouHolder.getRcvngOrgUnit().getRcvngOrgUnit(), ouHolder.getRcvngOrgUnit().getQtyPct());
					ouHolder.setRcvngOrgUnit(dlvry2Ou);
					modified = true;
				} else {
					PrcmtDlvry2Ou rcvngOrgUnit = ouHolder.getRcvngOrgUnit();
					if(!rcvngOrgUnit.contentEquals(dlvry2Ou)){
						rcvngOrgUnit.copyTo(dlvry2Ou);
						dlvry2Ou.setId(rcvngOrgUnit.getId());
						dlvry2Ou = deliveryEJB.updateOrgUnit(dlvry2Ou);
						ouHolder.setRcvngOrgUnit(dlvry2Ou);
						modified = true;
					}
				}
			}
		} else if(StringUtils.isNotBlank(deliveryHolder.getDelivery().getRcvngOrgUnit())){
			PrcmtDlvry2Ou dlvry2Ou = deliveryEJB.addOrgUnit(deliveryHolder.getDelivery(), deliveryHolder.getDelivery().getRcvngOrgUnit(), new BigDecimal("100"));
			PrcmtDlvryRcvngOrgUnitHolder ouHolder =new PrcmtDlvryRcvngOrgUnitHolder();
			ouHolder.setRcvngOrgUnit(dlvry2Ou);
			rcvngOrgUnits.add(ouHolder);
		}
		rcvngOrgUnits.removeAll(orgUnitToRemove);
		return modified;
	}
	
	private boolean synchProcmtOrderNbrs(PrcmtDeliveryHolder deliveryHolder){
		PrcmtDelivery delivery = deliveryHolder.getDelivery();
		String dlvryNbr = delivery.getDlvryNbr();
		boolean modified = false;
		// Process associated orders
		List<PrcmtOrderNbrHolder> procmtOrderNbrs = deliveryHolder.getProcmtOrderNbrs();
		List<PrcmtOrderNbrHolder> poToRemove = new ArrayList<PrcmtOrderNbrHolder>();
		for (PrcmtOrderNbrHolder poNbrHolder : procmtOrderNbrs) {
			PrcmtAbstractDlvry2PO dlvryPO = deliveryEJB.findProcOrder(dlvryNbr, poNbrHolder.getProcmtOrderNbr());
			if(poNbrHolder.isDeleted()){
				if(dlvryPO!=null){
					deliveryEJB.deleteProcOrder(dlvryPO.getId());
				}
				poToRemove.add(poNbrHolder);
				modified = true;
				continue;
			}
			if(dlvryPO==null){
				dlvryPO = deliveryEJB.addProcOrder(delivery, poNbrHolder.getProcmtOrderNbr());
				modified = true;
			}
		}
		procmtOrderNbrs.removeAll(poToRemove);
		
		return modified;
		
	}
	
	private PrcmtDelivery createDeliveryObject(PrcmtDelivery delivery, String currentLoginName, Date now){
		if(StringUtils.isBlank(delivery.getId())){
			delivery.setCreatingUsr(currentLoginName);
			delivery.setCreationDt(now);
			if(delivery.getDlvryDt()==null) delivery.setDlvryDt(now);
			delivery.setDlvryStatus(BaseProcessStatusEnum.ONGOING.name());
			delivery = deliveryEJB.create(delivery);
			createInitialDeliveryHistory(delivery);
		}
		return delivery;
	}
	
	public PrcmtDeliveryHolder closeDelivery(PrcmtDeliveryHolder deliveryHolder){
		deliveryHolder = updateDelivery(deliveryHolder);
		PrcmtDelivery delivery = deliveryHolder.getDelivery();

		recomputeDelivery(delivery);
		delivery.setDlvryStatus(BaseProcessStatusEnum.CLOSING.name());
		delivery = deliveryEJB.update(delivery);
		deliveryHolder.setDelivery(delivery);
		createClosingDeliveryHistory(delivery);// Status closed

		return deliveryHolder;
	}	

	public void closeDelivery(PrcmtDelivery delivery){
		delivery = deliveryEJB.findById(delivery.getId());
		if(!StringUtils.equals(delivery.getDlvryStatus(), BaseProcessStatusEnum.CLOSING.name())) return;
		delivery.setDlvryStatus(BaseProcessStatusEnum.CLOSED.name());
		delivery = deliveryEJB.update(delivery);
		createClosedDeliveryHistory(delivery);// Status closed
	}	
	
	private void recomputeDelivery(final PrcmtDelivery delivery){
		// update delivery object.
		String dlvryNbr = delivery.getDlvryNbr();
		Long count = dlvryItemEJB.countByDlvryNbr(dlvryNbr);
		int start = 0;
		int max = 100;
		delivery.clearAmts();
		while(start<=count){
			List<PrcmtDlvryItem> list = dlvryItemEJB.findByDlvryNbr(dlvryNbr, start, max);
			start +=max;
			for (PrcmtDlvryItem prcmtDlvryItem : list) {
				delivery.addGrossPPPreTax(prcmtDlvryItem.getGrossPPPreTax());
				delivery.addRebate(prcmtDlvryItem.getRebateAmt());
				delivery.addNetPPPreTax(prcmtDlvryItem.getNetPPPreTax());
				delivery.addVatAmount(prcmtDlvryItem.getVatAmt());
				delivery.addNetPPTaxIncl(prcmtDlvryItem.getNetPPTaxIncl());
			}
		}
		delivery.evlte();
	}
	
	private void createClosingDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setComment(BaseHistoryTypeEnum.CLOSING.name());
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(BaseHistoryTypeEnum.CLOSING.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(BaseProcStepEnum.CLOSING.name());
		deliveryHstryEJB.create(deliveryHstry);
	}

	private void createInitialDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setComment(BaseHistoryTypeEnum.INITIATED.name());
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(BaseHistoryTypeEnum.INITIATED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(BaseProcStepEnum.INITIATING.name());
		deliveryHstryEJB.create(deliveryHstry);
	}

	private void createModifiedDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setComment(BaseHistoryTypeEnum.MODIFIED.name());
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		deliveryHstryEJB.create(deliveryHstry);
	}
	
	private void createClosedDeliveryHistory(PrcmtDelivery delivery){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		PrcmtDeliveryHstry deliveryHstry = new PrcmtDeliveryHstry();
		deliveryHstry.setAddtnlInfo(DeliveryInfo.prinInfo(delivery));
		deliveryHstry.setComment(BaseHistoryTypeEnum.CLOSED.name());
		deliveryHstry.setEntIdentif(delivery.getId());
		deliveryHstry.setEntStatus(delivery.getDlvryStatus());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(BaseHistoryTypeEnum.CLOSED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(BaseProcStepEnum.CLOSING.name());
		deliveryHstryEJB.create(deliveryHstry);
	}
	
}
