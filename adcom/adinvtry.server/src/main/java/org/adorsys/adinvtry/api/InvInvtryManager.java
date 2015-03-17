package org.adorsys.adinvtry.api;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.enums.BaseProcStepEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryHstryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
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


	public InvInvtry prepareInventory(InvInvtry invtry) {
		// Create the delivery object if necessary
		return createInventoryObject(invtry, null, null);
	}
	
	/**
	 * Process an incoming inventory. The inventory holds :
	 * 	- a partial list of inventory
	 * 
	 * @param invtryHolder
	 * @return
	 */
	public InvInvtryHolder updateInventory(InvInvtryHolder invtryHolder){
		InvInvtry invtry = invtryHolder.getInvtry();
		String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		
		// Create the delivery object if necessary
		invtry = createInventoryObject(invtry, currentLoginName, now);
		invtryHolder.setInvtry(invtry);
		boolean modified = false;

		boolean itemModified = deleteHolders(invtryHolder);
		
		List<InvInvtryItemHolder> invInvtryItemHolders = invtryHolder.getInvtryItemHolders();
		if(invInvtryItemHolders==null) invInvtryItemHolders=new ArrayList<InvInvtryItemHolder>();
		
		for (InvInvtryItemHolder itemHolder : invInvtryItemHolders) {
			InvInvtryItem invInvtryItem = itemHolder.getInvtryItem();
			invInvtryItem.setAcsngUser(securityUtil.getCurrentLoginName());
			if(StringUtils.isBlank(invInvtryItem.getInvtryNbr()))
				invInvtryItem.setInvtryNbr(invtry.getInvtryNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(invInvtryItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(invInvtryItem.getId())){
				// todo check mdified
				InvInvtryItem persInvItem = invInvtryItemEJB.findById(invInvtryItem.getId());
				if(persInvItem==null) throw new IllegalStateException("Missing inventory item with id: " + invInvtryItem.getId());
				if(!invInvtryItem.contentEquals(persInvItem)){
					invInvtryItem.copyTo(persInvItem);
					invInvtryItem = invInvtryItemEJB.update(persInvItem);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(invInvtryItem.getId())) {
					InvInvtryItem persDi = invInvtryItemEJB.findById(invInvtryItem.getId());
					if(persDi!=null){
						if(!invInvtryItem.contentEquals(persDi)){
							invInvtryItem.copyTo(persDi);
							invInvtryItem = invInvtryItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						invInvtryItem.evlte();//evaluate different amount before save
						invInvtryItem = invInvtryItemEJB.create(invInvtryItem);
						itemModified = true;
					}
				} else {
					invInvtryItem.evlte();//evaluate different amount before save
					invInvtryItem = invInvtryItemEJB.create(invInvtryItem);
					itemModified = true;
				}
			}

			itemHolder.setInvtryItem(invInvtryItem);
			
		}

		if(itemModified){
			// Create or update the delivery.
			recomputeInventory(invtry);
			invtry.setInvtryStatus(InvInvtryStatus.ONGOING);
			invtry = inventoryEJB.update(invtry);
			invtryHolder.setInvtry(invtry);		
		}
		if(modified || itemModified){
			createModifiedInventoryHistory(invtry);
		}
		return invtryHolder;
	}
	
	/**
	 * Closing an existing invetory.
	 * 
	 * @param inventoryHolder
	 * @return
	 */
	public InvInvtryHolder closeInventory(InvInvtryHolder inventoryHolder){
		inventoryHolder = updateInventory(inventoryHolder);
		InvInvtry invtry = inventoryHolder.getInvtry();
		recomputeInventory(invtry);
		invtry.setInvtryStatus(InvInvtryStatus.CLOSED);
		invtry = inventoryEJB.update(invtry);
		inventoryHolder.setInvtry(invtry);
		createClosedInventoryHistory(invtry);// Status closed
		return inventoryHolder;
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
				inventory.setAcsngDt(now);
			inventory.setInvtryStatus(InvInvtryStatus.ONGOING);
			if(StringUtils.isBlank(inventory.getSection()) && StringUtils.isBlank(inventory.getRangeStart()) && StringUtils.isBlank(inventory.getRangeEnd())){
				inventory.setPreparedDt(new Date());
			}
			inventory = inventoryEJB.create(inventory);
			createInitialInventoryHistory(inventory);
			
		}
		return inventory;
	}
	
	@Schedule(second="*/45", minute = "*/3", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void prepareInventories() {
		List<InvInvtry> invtrys = inventoryEJB.findOpenInvtrys();
		for (InvInvtry inventory : invtrys) {
			boolean close = false;
			// Create object jobs.
			if(StringUtils.isNotBlank(inventory.getRangeStart()) || StringUtils.isNotBlank(inventory.getRangeEnd())){
				String rangeStart = inventory.getRangeStart();
				if(StringUtils.isBlank(rangeStart))rangeStart="a";
				String rangeEnd = inventory.getRangeEnd();
				if(StringUtils.isBlank(rangeEnd))rangeEnd="z";
				if(StringUtils.isNotBlank(inventory.getSection())){
					Long count = sctnLookup.countByStrgSectionAndArtNameRange(inventory.getSection(), rangeStart, rangeEnd);
					int max = 50;
					int first = 0;
					while(first<count){
						int firstResult = first;
						first+=count;
						List<StkArticleLot2StrgSctn> lot2Sections = sctnLookup.findByStrgSectionAndArtNameRange(inventory.getSection(), rangeStart, rangeEnd, firstResult, max);
						close |= createInvntryItems(lot2Sections, inventory);
					}
				} else {
					Long count = sctnLookup.countByArtNameRange(rangeStart, rangeEnd);
					int max = 50;
					int first = 0;
					while(first<count){
						int firstResult = first;
						first+=count;
						List<StkArticleLot2StrgSctn> lot2Sections = sctnLookup.findByArtNameRange(rangeStart, rangeEnd, firstResult, max);
						close |= createInvntryItems(lot2Sections, inventory);
					}
				}
			} else if (StringUtils.isNotBlank(inventory.getSection())){
				Long count = sctnLookup.countByStrgSection(inventory.getSection());
				int max = 50;
				int first = 0;
				while(first<count){
					int firstResult = first;
					first+=count;
					List<StkArticleLot2StrgSctn> lot2Sections = sctnLookup.findByStrgSectionSorted(inventory.getSection(), firstResult, max);
					close |= createInvntryItems(lot2Sections, inventory);
				}
			}
			if(close){
				InvInvtry found = inventoryEJB.findById(inventory.getId());
				found.setPreparedDt(new Date());
				inventoryEJB.update(found);
			}
		}
	}
	
	private boolean createInvntryItems(List<StkArticleLot2StrgSctn> lot2Sections, InvInvtry inventory){
		boolean modified = false;
		for (StkArticleLot2StrgSctn lot2StrgSctn : lot2Sections) {
			String identifier = InvInvtryItem.toIdentifier(inventory.getInvtryNbr(), lot2StrgSctn.getLotPic(), 
					lot2StrgSctn.getArtPic(), lot2StrgSctn.getStrgSection());
			InvInvtryItem invtryItem = invInvtryItemEJB.findByIdentif(identifier);
			if(invtryItem!=null) continue;
			invtryItem = new InvInvtryItem();
			invtryItem.setInvtryNbr(inventory.getInvtryNbr());
			invtryItem.setLotPic(lot2StrgSctn.getLotPic());
			invtryItem.setArtPic(lot2StrgSctn.getArtPic());
			invtryItem.setSection(lot2StrgSctn.getStrgSection());
			invtryItem.setArtName(lot2StrgSctn.getArtName());

			//evaluate different amount before save
			try {
				invtryItem = invInvtryItemEJB.create(invtryItem);
				modified = true;
			} catch (EntityExistsException e){
				// Noop
			}
		}
		return modified;
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
		InvInvtryHstry deliveryHstry = new InvInvtryHstry();
		deliveryHstry.setComment(BaseHistoryTypeEnum.MODIFIED.name());
		deliveryHstry.setAddtnlInfo(InventoryInfo.prinInfo(invtry));
		deliveryHstry.setEntIdentif(invtry.getId());
		deliveryHstry.setEntStatus(invtry.getInvtryStatus().name());
		deliveryHstry.setHstryDt(new Date());
		deliveryHstry.setHstryType(BaseHistoryTypeEnum.MODIFIED.name());
		
		deliveryHstry.setOrignLogin(callerPrincipal.getName());
		deliveryHstry.setOrignWrkspc(callerPrincipal.getWorkspaceId());
		deliveryHstry.setProcStep(BaseProcStepEnum.MODIFYING.name());
		invtryHstryEJB.create(deliveryHstry);
	}
}
