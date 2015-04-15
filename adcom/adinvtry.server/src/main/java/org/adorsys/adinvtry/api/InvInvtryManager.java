package org.adorsys.adinvtry.api;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.adorsys.adcore.vo.StringListHolder;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemList;
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
		invtry.setInvtryStatus(InvInvtryStatus.INITIALIZING);
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
		if(invtry.getInvtryStatus()==InvInvtryStatus.INITIALIZING || invtry.getInvtryStatus()==InvInvtryStatus.POSTED)
			return invtry;// Not updatable.
		return inventoryEJB.update(invtry);
	}
	
	public InvInvtry closeInventory(InvInvtry invtry){
		
		if(invtry.getInvtryStatus()==InvInvtryStatus.CLOSED || invtry.getInvtryStatus()==InvInvtryStatus.POSTED)
			return invtry;// Not closable.
		
		invtry = inventoryEJB.findById(invtry.getId());
		Date conflictDt = invtry.getConflictDt();
		invtry = invInvtryItemEJB.validateInventory(invtry);
		// Conflict detected. Save and return
		if(conflictDt==null && invtry.getConflictDt()!=null) return inventoryEJB.update(invtry);
		
		// conflict still there. Just return
		if(invtry.getConflictDt()!=null)return invtry;
		
		invtry = invInvtryItemEJB.recomputeInventory(invtry);

		invtry.setInvtryStatus(InvInvtryStatus.CLOSED);
		invtry.setClosedDate(new Date());
		invtry = inventoryEJB.update(invtry);
		createClosedInventoryHistory(invtry);// Status closed
		return invtry;
	}	

	public InvInvtry validateInventory(InvInvtry invtry){
		
		if(invtry.getInvtryStatus()==InvInvtryStatus.CLOSED || invtry.getInvtryStatus()==InvInvtryStatus.POSTED)
			return invtry;// Not closable.
		
		invtry = inventoryEJB.findById(invtry.getId());
		Date conflictDt = invtry.getConflictDt();
		invtry = invInvtryItemEJB.validateInventory(invtry);

		if(conflictDt!=invtry.getConflictDt()) invtry = inventoryEJB.update(invtry);
		
		return inventoryEJB.findById(invtry.getId());
	}	
	
	public InvInvtry postInventory(InvInvtry invtry){
		if(invtry.getInvtryStatus()!=InvInvtryStatus.CLOSED)
			return invtry;// Not closable.

		Date conflictDt = invtry.getConflictDt();
		invtry = invInvtryItemEJB.validateInventory(invtry);
		// Conflict detected. Save and return
		if(conflictDt==null && invtry.getConflictDt()!=null) return inventoryEJB.update(invtry);
		
		// conflict still there. Just return
		if(invtry.getConflictDt()!=null)return invtry;
		
		invtry = invInvtryItemEJB.recomputeInventory(invtry);

		invtry.setInvtryStatus(InvInvtryStatus.POSTED);
		invtry.setPostedDate(new Date());
		invtry = inventoryEJB.update(invtry);
		createPostedInventoryHistory(invtry);// Status closed
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
		
		invtryItem.setAcsngUser(securityUtil.getCurrentLoginName());
		String identifier = InvInvtryItem.toIdentifier(invtryItem.getInvtryNbr(), invtryItem.getAcsngUser(), invtryItem.getLotPic(), invtryItem.getArtPic(), invtryItem.getSection());
		
		InvInvtryItem existing = invInvtryItemEJB.findByIdentif(identifier);
		if(existing!=null){
			invtryItem.setId(existing.getId());
			return updateItem(invtryItem);
		}

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
			StkLotStockQty stockQty = stockQtyLookup.findLatestQty(invtryItem.getArtPic(), invtryItem.getLotPic(), invtryItem.getSection());
			if(stockQty!=null)
				invtryItem.setExpectedQty(stockQty.getStockQty());

			invtryItem.evlte();
		}
		
		StkArticleLot2StrgSctn lot2Section = sctnLookup.findByStrgSectionAndLotPicAndArtPic(invtryItem.getSection(), invtryItem.getLotPic(), invtryItem.getArtPic());
		if(lot2Section!=null){
			invtryItem.setArtName(lot2Section.getArtName());
		}
		InvInvtryItem created = invInvtryItemEJB.create(invtryItem);
		setConflicting(invtry, created);
		return created;
	}
	
	private void setConflicting(InvInvtry invtry, InvInvtryItem created){
		if(created.getConflictDt()!=null && invtry.getConflictDt()==null){
			invtry = inventoryEJB.findById(invtry.getId());
			if(invtry.getConflictDt()==null){
				invtry.setConflictDt(new Date());
				inventoryEJB.update(invtry);
			}
		}
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
		if(!BigDecimalUtils.strictEquals(invtryItem.getAsseccedQty(), existing.getAsseccedQty())){

			if(invtryItem.getAcsngDt()==null){
				invtryItem.setAcsngDt(new Date());
			}
			StkLotStockQty stockQty = stockQtyLookup.findLatestQty(existing.getArtPic(), existing.getLotPic(), existing.getSection());
			if(stockQty!=null)
				existing.setExpectedQty(stockQty.getStockQty());
			
			existing.setAsseccedQty(invtryItem.getAsseccedQty());
			existing.setAcsngDt(invtryItem.getAcsngDt());
			existing.setAcsngUser(currentLoginName);
			existing.evlte();
			changed = true;
		}
		if(!CalendarUtil.isSameDay(invtryItem.getExpirDt(), existing.getExpirDt()) && invtryItem.getExpirDt()!=null){
			existing.setExpirDt(invtryItem.getExpirDt());
			changed = true;
		}
		if(changed) {
			existing = invInvtryItemEJB.update(existing);
			if(!StringUtils.equals(currentLoginName, invtryItem.getAcsngUser())){
				createQuantityModifiedItemHistory(invtry, existing);
			}
		}

		setConflicting(invtry, existing);
		
		return existing;
	}

	public InvInvtryItemList disableItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getInvtryNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemEJB.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");
		
		if(existing.getDisabledDt()==null){
			Date disabledDt = invtryItem.getDisabledDt()!=null?invtryItem.getDisabledDt():new Date();
			existing.setDisabledDt(disabledDt);
			existing = invInvtryItemEJB.update(existing);
			
			// Create history.
			createDisabledInventoryItemHistory(invtry, existing);

			setConflicting(invtry, existing);
		}

		List<String> invtryNbrs = Arrays.asList(existing.getInvtryNbr());
		List<InvInvtryItem> invtryItems = invInvtryItemEJB.findBySalIndexForInvtrys(existing.getSalIndex(), invtryNbrs);
		return new InvInvtryItemList(existing.getSalIndex(), invtryNbrs, invtryItems);
		
	}

	public InvInvtryItemList enableItem(InvInvtryItem invtryItem) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryItem.getInvtryNbr());
		if(invtry==null) 
			throw new IllegalArgumentException("Missing inventory object");
		if(invtryHstryEJB.isClosed(invtry.getIdentif()))
			throw new IllegalStateException("Inventory object closed");
		InvInvtryItem existing = invInvtryItemEJB.findById(invtryItem.getId());
		if(existing==null)
			throw new IllegalStateException("Inventory Item inexistant");

		if(existing.getDisabledDt()!=null) {
			existing.setDisabledDt(null);
			existing = invInvtryItemEJB.update(existing);
			
			// Create history.
			createDisabledInventoryItemHistory(invtry, existing);

			setConflicting(invtry, existing);
		}

		List<String> invtryNbrs = Arrays.asList(existing.getInvtryNbr());
		List<InvInvtryItem> invtryItems = invInvtryItemEJB.findBySalIndexForInvtrys(existing.getSalIndex(), invtryNbrs);
		return new InvInvtryItemList(existing.getSalIndex(), invtryNbrs, invtryItems);
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
			if(inventory.getInvtryStatus()==null)
				inventory.setInvtryStatus(InvInvtryStatus.ONGOING);
			if(StringUtils.isBlank(inventory.getSection()) && StringUtils.isBlank(inventory.getRangeStart()) && StringUtils.isBlank(inventory.getRangeEnd())){
				inventory.setPreparedDt(new Date());
			}
			inventory = inventoryEJB.create(inventory);
			createInitialInventoryHistory(inventory);
			
		}
		return inventory;
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

	/**
	 * Merge all listed inventories into a single inventory.
	 * 
	 * @param searchInput
	 * @return
	 */
	public StringListHolder merge(StringListHolder invtryNbrs) {
		List<String> list = invtryNbrs.getList();
		if(list.isEmpty() || list.size()==1) return invtryNbrs;
		String containerInvtryNbr = list.get(0);
		// Validate Inventry for merging.
		InvInvtry containerInvtry = inventoryEJB.findByIdentif(containerInvtryNbr);
		checkCandidateContainer(containerInvtry);
		for (String invtryNbr : list) {
			if(StringUtils.equals(invtryNbr,containerInvtryNbr)) continue;
			InvInvtry invtry = inventoryEJB.findByIdentif(invtryNbr);
			checkCandidateMerge(containerInvtry, invtry);
			if(invtry.getContainerId()==null){
				invtry.setContainerId(containerInvtry.getInvtryNbr());
				invtry.setInvtryStatus(InvInvtryStatus.MERGED);
				inventoryEJB.update(invtry);
			}
		}
		containerInvtry.setContainerId(containerInvtry.getContainerId());
		inventoryEJB.update(containerInvtry);
		return invtryNbrs;
	}
	
	private void checkCandidateContainer(InvInvtry containerInvtry){
		if(containerInvtry==null)
			throw new IllegalArgumentException("InvInvtry_candidateContainerNotFound_error");
		// COnditions
		if(containerInvtry.getContainerId()!=null || containerInvtry.getMergedDate()!=null)
			throw new IllegalArgumentException("InvInvtry_mergedCanNotContain_error");
		if(containerInvtry.getPostedDate()!=null || containerInvtry.getMergedDate()!=null)
			throw new IllegalArgumentException("InvInvtry_postedCanNotContain_error");
	}

	private void checkCandidateMerge(InvInvtry invtry, InvInvtry containerInvtry){
		if(invtry==null)
			throw new IllegalArgumentException("InvInvtry_candidateMergeNotFound_error");
		// Conditions
		if(invtry.getPostedDate()!=null)
			throw new IllegalArgumentException("InvInvtry_postedCanNotBeMerged_error");
		if(invtry.getContainerId()!=null && !StringUtils.equals(invtry.getContainerId(), containerInvtry.getInvtryNbr()))
			throw new IllegalArgumentException("InvInvtry_mergeCandAssignedToAnotherContainer_error");
		if(invtry.getMergedDate()!=null && !StringUtils.equals(invtry.getContainerId(), containerInvtry.getInvtryNbr()))
			throw new IllegalArgumentException("InvInvtry_mergeCandMergedIntoAnotherContainer_error");
	}
}
