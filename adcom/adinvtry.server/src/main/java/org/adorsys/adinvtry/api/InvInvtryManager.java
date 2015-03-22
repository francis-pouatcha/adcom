package org.adorsys.adinvtry.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adcore.utils.FormatedValidFrom;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryHstryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryManager {
	
	@Inject
	private InvInvtryEJB inventoryEJB;
	
	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 
	
	@Inject
	private SecurityUtil securityUtil;

	@Inject
	private InvInvtryHstryEJB invtryHstryEJB;

	@Inject
	private StkArticleLot2StrgSctnLookup sctnLookup;
	
	@Inject
	private StkArticleLotLookup articleLotLookup;
	
	public InvInvtry prepareInventory(InvInvtry invtry, String accessingUser) {
		// Create the delivery object if necessary
		Date now = new Date();
		return createInventoryObject(invtry, accessingUser, now);
	}
	
	/**
	 * Process an incoming inventory. The inventory holds :
	 * 	- a partial list of inventory
	 * 
	 * @param invtryHolder
	 * @return
	 */
	public InvInvtry updateInventory(InvInvtry invtry){
		return inventoryEJB.update(invtry);
//		InvInvtry invtry = invtryHolder.getInvtry();
//		String currentLoginName = securityUtil.getCurrentLoginName();
//		Date now = new Date();
//		
//		// Create the delivery object if necessary
//		invtry = createInventoryObject(invtry, currentLoginName, now);
//		
//		invtryHolder.setInvtry(invtry);
//		boolean modified = false;
//
//		boolean itemModified = deleteHolders(invtryHolder);
//		
//		List<InvInvtryItemHolder> invInvtryItemHolders = invtryHolder.getInvtryItemHolders();
//		if(invInvtryItemHolders==null) invInvtryItemHolders=new ArrayList<InvInvtryItemHolder>();
//		
//		for (InvInvtryItemHolder itemHolder : invInvtryItemHolders) {
//			InvInvtryItem invInvtryItem = itemHolder.getInvtryItem();
//			invInvtryItem.setAcsngUser(securityUtil.getCurrentLoginName());
//			if(StringUtils.isBlank(invInvtryItem.getInvtryNbr()))
//				invInvtryItem.setInvtryNbr(invtry.getInvtryNbr());
//			// check presence of the article pic
//			if(StringUtils.isBlank(invInvtryItem.getArtPic()))
//				throw new IllegalStateException("Missing article identification code.");
//
//			if(StringUtils.isNotBlank(invInvtryItem.getId())){
//				// todo check mdified
//				InvInvtryItem persInvItem = invInvtryItemEJB.findById(invInvtryItem.getId());
//				if(persInvItem==null) throw new IllegalStateException("Missing inventory item with id: " + invInvtryItem.getId());
//				if(!invInvtryItem.contentEquals(persInvItem)){
//					invInvtryItem.copyTo(persInvItem);
//					invInvtryItem = invInvtryItemEJB.update(persInvItem);
//					itemModified = true;
//				}
//			} else {
//				if (StringUtils.isNotBlank(invInvtryItem.getId())) {
//					InvInvtryItem persDi = invInvtryItemEJB.findById(invInvtryItem.getId());
//					if(persDi!=null){
//						if(!invInvtryItem.contentEquals(persDi)){
//							invInvtryItem.copyTo(persDi);
//							invInvtryItem = invInvtryItemEJB.update(persDi);
//							itemModified = true;
//						}
//					} else {
//						invInvtryItem.evlte();//evaluate different amount before save
//						invInvtryItem = invInvtryItemEJB.create(invInvtryItem);
//						itemModified = true;
//					}
//				} else {
//					invInvtryItem.evlte();//evaluate different amount before save
//					invInvtryItem = invInvtryItemEJB.create(invInvtryItem);
//					itemModified = true;
//				}
//			}
//
//			itemHolder.setInvtryItem(invInvtryItem);
			
//		}
//
//		if(itemModified){
//			// Create or update the delivery.
//			recomputeInventory(invtry);
//			invtry.setInvtryStatus(InvInvtryStatus.ONGOING);
//			invtry = inventoryEJB.update(invtry);
//			invtryHolder.setInvtry(invtry);		
//		}
//		if(modified || itemModified){
//			createModifiedInventoryHistory(invtry);
//		}
//		return invtryHolder;
	}
	
	public InvInvtry closeInventory(InvInvtry invtry){
//		inventoryHolder = updateInventory(inventoryHolder);
//		InvInvtry invtry = inventoryHolder.getInvtry();
		invtry = inventoryEJB.findById(invtry.getId());
		recomputeInventory(invtry);
		invtry.setInvtryStatus(InvInvtryStatus.CLOSED);
		invtry = inventoryEJB.update(invtry);
//		inventoryHolder.setInvtry(invtry);
		createClosedInventoryHistory(invtry);// Status closed
//		return inventoryHolder;
		return invtry;
	}	

	public InvInvtry postInventory(InvInvtry invtry){
//		inventoryHolder = updateInventory(inventoryHolder);
//		InvInvtry invtry = inventoryHolder.getInvtry();
		invtry = inventoryEJB.findById(invtry.getId());
		recomputeInventory(invtry);
		invtry.setInvtryStatus(InvInvtryStatus.POSTED);
		invtry = inventoryEJB.update(invtry);
//		inventoryHolder.setInvtry(invtry);
		createPostedInventoryHistory(invtry);// Status closed
//		return inventoryHolder;
		return invtry;
	}	

	@Inject
	private StkLotStockQtyLookup stockQtyLookup; 
	/**
	 * Add an inventory item. Conditions are:
	 * <ol>
	 * 	<li>The Inventory must not have been closed yet.</li>
	 * </ol>
	 * @param invtryItem
	 * @return
	 */
	public InvInvtryItem addItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getInvtryNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		
		String identifier = InvInvtryItem.toIdentifier(invtryItem.getInvtryNbr(), invtryItem.getLotPic(), invtryItem.getArtPic(), invtryItem.getSection());
		
		InvInvtryItem existing = invInvtryItemEJB.findByIdentif(identifier);
		if(existing!=null)
			return existing;

		StkArticleLot articleLot = articleLotLookup.findByIdentif(invtryItem.getLotPic());
		if(articleLot!=null){
			if(invtryItem.getExpirDt()==null){
				invtryItem.setExpirDt(articleLot.getExpirDt());
			}
			invtryItem.setMinSppuHT(articleLot.getMinSppuHT());
			invtryItem.setPppuCur(articleLot.getPppuCur());
			invtryItem.setPppuPT(articleLot.getPppuHT());
			invtryItem.setPurchRtrnDays(articleLot.getPurchRtrnDays());
			invtryItem.setPurchWrntyDys(articleLot.getPurchWrntyDys());
			invtryItem.setSalesRtrnDays(articleLot.getSalesRtrnDays());
			invtryItem.setSalesWrntyDys(articleLot.getSalesWrntyDys());
			invtryItem.setSppuCur(articleLot.getSppuCur());
			invtryItem.setSppuPT(articleLot.getSppuHT());
			invtryItem.setSupplier(articleLot.getSupplier());
			invtryItem.setSupplierPic(articleLot.getSupplierPic());
			invtryItem.setVatPurchPct(articleLot.getVatPurchPct());
			invtryItem.setVatSalesPct(articleLot.getVatSalesPct());
		}
		
		if(invtryItem.getAsseccedQty()!=null){
			if(invtryItem.getAcsngDt()==null){
				invtryItem.setAcsngDt(new Date());
			}
			invtryItem.setAcsngUser(securityUtil.getCurrentLoginName());
			StkLotStockQty stockQty = stockQtyLookup.findLatestQty(invtryItem.getArtPic(), invtryItem.getLotPic(), invtryItem.getSection());
			if(stockQty!=null)
				invtryItem.setExpectedQty(stockQty.getStockQty());

			invtryItem.evlte();
		}
		
		StkArticleLot2StrgSctn lot2Section = sctnLookup.findByStrgSectionAndLotPicAndArtPic(invtryItem.getSection(), invtryItem.getLotPic(), invtryItem.getArtPic());
		if(lot2Section!=null){
			invtryItem.setArtName(lot2Section.getArtName());
		}
		return invInvtryItemEJB.create(invtryItem);
	}
	
	/**
	 * Updates an inventory item. Conditions are:
	 * <ol>
	 * 	<li>The Inventory must not have been closed yet.</li>
	 * 	<li>The Inventory item has been created already. Is just being modified.</li>
	 * 	<li>The Inventory disabledDt must not have changed.</li>
	 * </ol>
	 * @param invtryItem
	 * @return
	 */
	public InvInvtryItem updateItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getInvtryNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemEJB.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");
		if(!CalendarUtil.isSameInstant(invtryItem.getDisabledDt(), existing.getDisabledDt()))
			throw new IllegalStateException("Use disableItem/enableItem to change the status of an inventory item.");

		String currentLoginName = securityUtil.getCurrentLoginName();

		boolean changed = false;
		if(!BigDecimalUtils.numericEquals(invtryItem.getAsseccedQty(), existing.getAsseccedQty())){
			existing.setAsseccedQty(invtryItem.getAsseccedQty());
			existing.setAcsngDt(invtryItem.getAcsngDt());
			existing.setAcsngUser(currentLoginName);
			existing.evlte();
			changed = true;
		}
		if(!CalendarUtil.isSameDay(invtryItem.getExpirDt(), existing.getExpirDt())){
			existing.setExpirDt(invtryItem.getExpirDt());
			changed = true;
		}
		if(changed) {
			existing = invInvtryItemEJB.update(existing);
			if(!StringUtils.equals(currentLoginName, invtryItem.getAcsngUser())){
				createQuantityModifiedItemHistory(invtry, existing);
			}
		}
		
		return existing;
	}

	public InvInvtryItem disableItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getInvtryNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemEJB.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");

		if(existing.getDisabledDt()!=null) return existing;

		Date disabledDt = invtryItem.getDisabledDt()!=null?invtryItem.getDisabledDt():new Date();
		existing.setDisabledDt(disabledDt);
		existing = invInvtryItemEJB.update(existing);
		
		// Create history.
		createDisabledInventoryItemHistory(invtry, existing);
		return existing;
		
	}

	public InvInvtryItem enableItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getInvtryNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemEJB.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");

		if(existing.getDisabledDt()==null) return existing;

		existing.setDisabledDt(null);
		existing = invInvtryItemEJB.update(existing);
		
		// Create history.
		createDisabledInventoryItemHistory(invtry, existing);
		return existing;
	}
	
	private boolean deleteHolders(InvInvtryHolder invtryHolder){
		List<InvInvtryItemHolder> invtryItemHolders = invtryHolder.getInvtryItemHolders();
		List<InvInvtryItemHolder> diToRemove = new ArrayList<InvInvtryItemHolder>();
		boolean modified = false;
		for (InvInvtryItemHolder itemHolder : invtryItemHolders) {
			if(itemHolder.isDeleted()){
				InvInvtryItem invtryItem = itemHolder.getInvtryItem();
				String id = StringUtils.isNotBlank(invtryItem.getId())?invtryItem.getId():invtryItem.getIdentif();
				if(StringUtils.isNotBlank(id)){
					invInvtryItemEJB.deleteById(invtryItem.getId());
					modified = true;					
				}
				diToRemove.add(itemHolder);
			}
		}
		invtryItemHolders.removeAll(diToRemove);
		return modified;
	}

	private InvInvtry createInventoryObject(InvInvtry inventory, String currentLoginName, Date now){
		if(StringUtils.isBlank(inventory.getId())){
			if(StringUtils.isBlank(inventory.getAcsngUser()))
				inventory.setAcsngUser(currentLoginName);
			if(inventory.getInvtryDt()==null)
				inventory.setInvtryDt(now);
			inventory.setInvtryStatus(InvInvtryStatus.ONGOING);
			if(StringUtils.isBlank(inventory.getSection()) && StringUtils.isBlank(inventory.getRangeStart()) && StringUtils.isBlank(inventory.getRangeEnd())){
				inventory.setPreparedDt(new Date());
			}
			inventory = inventoryEJB.create(inventory);
			createInitialInventoryHistory(inventory);
			
		}
		return inventory;
	}
	
	private void recomputeInventory(final InvInvtry invInvtry){
		// update delivery object.
		String invtryNbr = invInvtry.getInvtryNbr();
		Long count = invInvtryItemEJB.countByInvtryNbr(invtryNbr);
		int start = 0;
		int max = 100;
		invInvtry.clearAmts();
		while(start<=count){
			List<InvInvtryItem> list = invInvtryItemEJB.findByInvtryNbr(invtryNbr, start, max);
			start +=max;
			for (InvInvtryItem item : list) {
				invInvtry.addGapPurchAmtHT(item.getGapTotalPpPT());
				invInvtry.addGapSaleAmtHT(item.getGapTotalSpPT());
			}
		}
	}
	
	private void createClosedInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setComment(BaseHistoryTypeEnum.CLOSED.name());
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(BaseHistoryTypeEnum.CLOSED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(BaseProcStepEnum.CLOSING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	private void createPostedInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setComment(BaseHistoryTypeEnum.POSTED.name());
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(BaseHistoryTypeEnum.POSTED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(BaseProcStepEnum.POSTING.name());
		invtryHstryEJB.create(invtryHstry);
	}
	
	private void createInitialInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(BaseHistoryTypeEnum.INITIATED.name());
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(BaseHistoryTypeEnum.INITIATED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(BaseProcStepEnum.INITIATING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	private void createModifiedInventoryHistory(InvInvtry invtry){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(BaseHistoryTypeEnum.MODIFIED.name());
		invtryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		invtryHstry.setEntIdentif(invtry.getId());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(invtryHstry);
	}

	private void createDisabledInventoryItemHistory(InvInvtry invtry, InvInvtryItem invtryItem){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(BaseHistoryTypeEnum.ITEM_MODIFIED.name());
		invtryHstry.setAddtnlInfo(FormatedValidFrom.format(invtryItem.getDisabledDt()));
		invtryHstry.setEntIdentif(invtry.getIdentif());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(invtryHstry);
	}
	
	private void createQuantityModifiedItemHistory(InvInvtry invtry, InvInvtryItem invtryItem){
		TermWsUserPrincipal callerPrincipal = securityUtil.getCallerPrincipal();
		InvInvtryHstry invtryHstry = new InvInvtryHstry();
		invtryHstry.setComment(BaseHistoryTypeEnum.ITEM_MODIFIED.name());
		invtryHstry.setAddtnlInfo("" + invtryItem.getAsseccedQty());
		invtryHstry.setEntIdentif(invtry.getIdentif());
		invtryHstry.setEntStatus(invtry.getInvtryStatus().name());
		invtryHstry.setHstryDt(new Date());
		invtryHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());
		
		invtryHstry.setOrignLogin(callerPrincipal.getName());
		invtryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		invtryHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(invtryHstry);
	}
}
