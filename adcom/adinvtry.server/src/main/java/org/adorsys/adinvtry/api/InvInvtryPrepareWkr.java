package org.adorsys.adinvtry.api;

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

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLot2StrgSctnLookup;
import org.adorsys.adstock.rest.StkArticleLotLookup;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class InvInvtryPrepareWkr {
	
	@Inject
	private InvInvtryEJB inventoryEJB;
	
	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 

	@Inject
	private StkArticleLot2StrgSctnLookup sctnLookup;
	
	@Inject
	private StkArticleLotLookup articleLotLookup;

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

			StkArticleLot articleLot = articleLotLookup.findByIdentif(invtryItem.getLotPic());
			invtryItem.setExpirDt(articleLot.getExpirDt());
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
}
