/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcshdwr.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.jpa.CdrDsHstry;
import org.adorsys.adcshdwr.jpa.CdrDsInfo;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;
import org.adorsys.adcshdwr.payementevent.DirectSale;
import org.adorsys.adcshdwr.payementevent.PaymentEvent;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEJB;
import org.adorsys.adcshdwr.rest.CdrDsArtItemEJB;
import org.adorsys.adcshdwr.rest.CdrDsHstryEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *@author guymoyo
 */
@Stateless
public class CdrDrctSalesManager {

	@Inject
	private CdrDrctSalesEJB cdrDrctSalesEJB;

	@Inject
	private CdrDsArtItemEJB cdrDsArtItemEJB;

	@Inject
	private CdrCshDrawerEJB cshDrawerEJB;

	@Inject
	private CdrDsHstryEJB cdrDsHstryEJB;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
    @DirectSale
    Event<PaymentEvent> directSaleEvent;
	@Inject
	CdrDsPymntItemEJB cdrDsPymntItemEJB;

	public CdrDsArtHolder updateOrder(CdrDsArtHolder cdrDsArtHolder) throws AdException{
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
		CdrCshDrawer activeCshDrawer = cshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new AdException("No opened cash drawer found for this session, please open one.");
		cdrDrctSales.setCdrNbr(activeCshDrawer.getCdrNbr());
		if(StringUtils.isBlank(cdrDrctSales.getRcptNbr())) {
			cdrDrctSales.setRcptNbr("-");
		}
		if(StringUtils.isBlank(cdrDrctSales.getId())) {
			cdrDrctSales = cdrDrctSalesEJB.create(cdrDrctSales);
		}
//		boolean modified = false;
		boolean itemModified = deleteHolders(cdrDsArtHolder);

		List<CdrDsArtItemHolder> cdrDsArtItemHolders = cdrDsArtHolder.getItems();
		if(cdrDsArtItemHolders == null) cdrDsArtItemHolders = new ArrayList<CdrDsArtItemHolder>();

		for (CdrDsArtItemHolder cdrDsArtItemHolder : cdrDsArtItemHolders) {
			CdrDsArtItem cdrDsArtItem = cdrDsArtItemHolder.getItem();

			if(StringUtils.isBlank(cdrDsArtItem.getDsNbr()))
				cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(cdrDsArtItem.getArtPic()))
				throw new AdException("Missing article identification code.");

			if(StringUtils.isNotBlank(cdrDsArtItem.getId())){
				CdrDsArtItem persDi = cdrDsArtItemEJB.findById(cdrDsArtItem.getId());
				if(persDi==null) throw new AdException("Missing directsales item with id: " + cdrDsArtItem.getId());
				if(!cdrDsArtItem.contentEquals(persDi)){
					cdrDsArtItem.copyTo(persDi);
					cdrDsArtItem = cdrDsArtItemEJB.update(persDi);
					itemModified = true;
				}
			} else {
				/*cdrDsArtItem.evlte();
				cdrDsArtItem.setObjctOrgUnit(securityUtil.getCurrentOrgUnit().getIdentif());
				cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
				cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
				itemModified = true;*/
				if (StringUtils.isNotBlank(cdrDsArtItem.getId())) {
					CdrDsArtItem persDi = cdrDsArtItemEJB.findById(cdrDsArtItem.getDsNbr());
					if(persDi!=null){
						if(!cdrDsArtItem.contentEquals(persDi)){
							cdrDsArtItem.copyTo(persDi);
							cdrDsArtItem = cdrDsArtItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						cdrDsArtItem.evlte();
						cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
						cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
						itemModified = true;
					}
				} else {
					cdrDsArtItem.evlte();
					if(StringUtils.isBlank(cdrDsArtItem.getObjctOrgUnit())) {
						cdrDsArtItem.setObjctOrgUnit(securityUtil.getCurrentOrgUnit().getIdentif());
					}
					cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
					itemModified = true;
				}
			}

			cdrDsArtItemHolder.setItem(cdrDsArtItem);
		}

		if(itemModified){
			recomputeOrder(cdrDrctSales);
			//			cdrDrctSales.setPoStatus(BaseProcessStatusEnum.ONGOING.name());
			cdrDrctSales = cdrDrctSalesEJB.update(cdrDrctSales);
			PaymentEvent paymentEvent = new PaymentEvent(CdrPymntMode.CASH, cdrDrctSales.getNetAmtToPay(), cdrDsArtHolder.getPaidAmt(), new Date(), cdrDrctSales.getDsNbr());
			directSaleEvent.fire(paymentEvent);
			cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		}
		/*		if(modified || itemModified){
			createModifiedOrderHistory(cdrDrctSales);
			updateCshDrawer(cdrDrctSales);
		}*/
		return cdrDsArtHolder;
	}


	private boolean deleteHolders(CdrDsArtHolder cdrDsArtHolder){
		List<CdrDsArtItemHolder> cdrDsArtItemHolders = cdrDsArtHolder.getItems();
		List<CdrDsArtItemHolder> oiToRemove = new ArrayList<CdrDsArtItemHolder>();
		boolean modified = false;
		for (CdrDsArtItemHolder itemHolder : cdrDsArtItemHolders) {
			if(itemHolder.isDeleted()){
				CdrDsArtItem cdrDsArtItem = itemHolder.getItem();
				String id = StringUtils.isNotBlank(cdrDsArtItem.getId())?cdrDsArtItem.getId():cdrDsArtItem.getDsNbr();
				if(StringUtils.isNotBlank(id)){
					cdrDsArtItemEJB.deleteById(id);
					modified = true;					
				}
				oiToRemove.add(itemHolder);
			}
		}
		cdrDsArtItemHolders.removeAll(oiToRemove);
		return modified;
	}


	private void recomputeOrder(final CdrDrctSales cdrDrctSales){
		// recompute sales object.
		String dsNbr = cdrDrctSales.getDsNbr();
		Long count = cdrDsArtItemEJB.countByDsNbr(dsNbr);
		int start = 0;
		int max = 100;
		cdrDrctSales.clearAmts();
		while(start<=count){
			List<CdrDsArtItem> list = cdrDsArtItemEJB.findByDsNbr(dsNbr, start, max);
			start +=max;
			for (CdrDsArtItem cdrDsArtItem : list) {
				cdrDrctSales.addGrossSPPreTax(cdrDsArtItem.getGrossSPPreTax());
				cdrDrctSales.addRebate(cdrDsArtItem.getRebate());
				cdrDrctSales.addNetSPPreTax(cdrDsArtItem.getNetSPPreTax());
				cdrDrctSales.addVatAmount(cdrDsArtItem.getVatAmount());
				cdrDrctSales.addNetSPTaxIncl(cdrDsArtItem.getNetSPTaxIncl());
			}
		}
		cdrDrctSales.evlte();
	}

	public CdrDsArtHolder closeSales(CdrDsArtHolder cdrDsArtHolder) throws AdException{
		cdrDsArtHolder = updateOrder(cdrDsArtHolder);
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
		recomputeOrder(cdrDrctSales);
		//		procOrder.setPoStatus(BaseProcessStatusEnum.CLOSED.name());
		cdrDrctSales = cdrDrctSalesEJB.update(cdrDrctSales);
		cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		createClosedSalesHistory(cdrDrctSales);// closed, no need processor?

		return cdrDsArtHolder;
	}

	public CdrDsArtHolder findOrder(String id){
		CdrDsArtHolder cdrDsArtHolder = new CdrDsArtHolder();
		CdrDrctSales cdrDrctSales = cdrDrctSalesEJB.findById(id);
		cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		List<CdrDsArtItem> cdrDsArtItems = cdrDsArtItemEJB.findByDsNbr(cdrDrctSales.getDsNbr());
		for(CdrDsArtItem cdrDsArtItem:cdrDsArtItems) {
			CdrDsArtItemHolder cdrDsArtItemHolder = new CdrDsArtItemHolder();
			cdrDsArtItemHolder.setItem(cdrDsArtItem);
			cdrDsArtHolder.getItems().add(cdrDsArtItemHolder);
		}
		return cdrDsArtHolder;
	}


	private void createClosedSalesHistory(CdrDrctSales cdrDrctSales){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		CdrDsHstry cdrDsHstry = new CdrDsHstry();

		cdrDsHstry.setComment(BaseHistoryTypeEnum.CLOSED.name());
		cdrDsHstry.setAddtnlInfo(CdrDsInfo.prinInfo(cdrDrctSales));
		cdrDsHstry.setEntIdentif(cdrDrctSales.getId());
		//		cdrDsHstry.setEntStatus(prcmtOrder.getPoStatus());
		cdrDsHstry.setHstryDt(new Date());
		cdrDsHstry.setHstryType(BaseHistoryTypeEnum.CLOSED.name());	
		cdrDsHstry.setOrignLogin(callerPrincipal.getName());
		cdrDsHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		cdrDsHstry.setProcStep(BaseProcStepEnum.CLOSING.name());
		cdrDsHstry.makeHistoryId(true);
		cdrDsHstryEJB.create(cdrDsHstry);
	}


	public CdrDsArtHolder findCdrDsArtHolder(String id) {
		CdrDsArtHolder cdrDsArtHolder = new CdrDsArtHolder();
		CdrDrctSales cdrDrctSales = cdrDrctSalesEJB.findById(id);
		cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		List<CdrDsArtItem> listItem = cdrDsArtItemEJB.findByDsNbr(cdrDrctSales.getDsNbr());
		for(CdrDsArtItem item:listItem){
			CdrDsArtItemHolder cdrDsArtItemHolder = new CdrDsArtItemHolder();
			cdrDsArtItemHolder.setItem(item);
			cdrDsArtItemHolder.setArtName(item.getArtName());
			cdrDsArtHolder.getItems().add(cdrDsArtItemHolder);
		}
		CdrDsPymntItem cdrDsPymntItem = cdrDsPymntItemEJB.findByDsNbr(cdrDrctSales.getDsNbr()).get(0);
		cdrDsArtHolder.setPaidAmt(cdrDsPymntItem.getRcvdAmt());
		cdrDsArtHolder.setChangeAmt(cdrDsPymntItem.getDiffAmt());		
		return cdrDsArtHolder;
	}


	public CdrDsArtHolder returnProduct(CdrDsArtHolder cdrDsArtHolder) {
		List<CdrDsArtItemHolder> items = cdrDsArtHolder.getItems();
		for(CdrDsArtItemHolder item:items){
			if(item.getItem().getReturnedQty() != null && item.getItem().getReturnedQty().compareTo(BigDecimal.ZERO) == 1 ){
				cdrDsArtItemEJB.update(item.getItem());
			}
		}	
		return cdrDsArtHolder;
	}

}