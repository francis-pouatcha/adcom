/**
 * 
 */
package org.adorsys.adinvtry.rest;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.Contract;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.util.AbstractMapper;
import org.adorsys.adinvtry.rest.util.IdentifToInvInvtryItemMapper;
import org.adorsys.adinvtry.rest.util.ListToMapConverter;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class InvtryManagerEJB {
	
	@Inject
	private InvInvtryEJB invtryEJB;
	
	@Inject
	private InvInvtryItemEJB invtryItemEJB;

	
	/**
	 * saveInvtry.
	 *
	 * @param invtryHolder
	 * @return {@link InvInvtry}
	 */
	public InvInvtry saveInvtry(InvInvtryHolder invtryHolder) {
		if(invtryHolder == null) throw new IllegalArgumentException("Null argument are not accepted here. Invalid invtry");
		InvInvtry invtry = invtryHolder.getInvtry();
		List<InvInvtryItem> invtryItems = invtryHolder.getInvtryItems();
		generateInvNbr(invtry);
		List<InvInvtryItem> savedItems = new ArrayList<InvInvtryItem>(invtryItems.size());
		for (InvInvtryItem invInvtryItem : invtryItems) {
			invInvtryItem = addInvIntryItem(invtry,invInvtryItem);
			savedItems.add(invInvtryItem);
		}

		computeInvtryGap(invtry,savedItems);
		invtry.setInvtryStatus(InvInvtryStatus.ONGOING);
		saveInvtry(invtry);
		
		List<InvInvtryItem> existingInvtryItems = invtryItemEJB.findByInvtryNbr(invtry.getInvtryNbr());
		List<InvInvtryItem> removedInvItems = retrieveRemovedInvItems(savedItems,existingInvtryItems);
		deleteRemoved(removedInvItems);
		return invtry;
	}


	
	/**
	 * closeInvtry.
	 *
	 * @param invtryHolder
	 */
	public void closeInvtry(InvInvtryHolder invtryHolder) {
		if(invtryHolder == null) return;
		InvInvtry invtry = saveInvtry(invtryHolder);
		//TODO : trigger worker.
		invtry.setInvtryStatus(InvInvtryStatus.CLOSED);
		invtry.setInvtryDt(new Date());
	}

	
	/**
	 * deleteRemoved.
	 *
	 * @param removedInvItems
	 */
	private void deleteRemoved(List<InvInvtryItem> removedInvItems) {
		for (InvInvtryItem invInvtryItem : removedInvItems) {
			if(StringUtils.isNotBlank(invInvtryItem.getId())) {
				invtryItemEJB.deleteById(invInvtryItem.getId());
			}
		}
	}


	/**
	 * retrieveRemoveInvItems.
	 *
	 * @param savedItems
	 * @param existingInvtryItems
	 * @return {@link List}<InvInvtryItems>
	 */
	private List<InvInvtryItem> retrieveRemovedInvItems(List<InvInvtryItem> savedItems,
			List<InvInvtryItem> existingInvtryItems) {
		IdentifToInvInvtryItemMapper defaultMapper = new IdentifToInvInvtryItemMapper();
		Map<String, InvInvtryItem> savedItemsMap = transform(savedItems,defaultMapper);
		Map<String, InvInvtryItem> existingInvtryItemsMap = transform(savedItems,defaultMapper);
		List<InvInvtryItem> removed = new ArrayList<InvInvtryItem>();
		
		Set<String> savedItemsKeys = savedItemsMap.keySet();
		for (String savedItemsKey : savedItemsKeys) {
			if(!existingInvtryItemsMap.containsKey(savedItemsKey)) {
				removed.add(existingInvtryItemsMap.get(savedItemsKey));
			}
		}
		return removed;
	}
	


	/**
	 * transform.
	 *
	 * @param savedItems
	 * @return
	 */
	private Map<String, InvInvtryItem> transform(List<InvInvtryItem> savedItems,AbstractMapper<InvInvtryItem, String, InvInvtryItem> mapper) {		
		ListToMapConverter<InvInvtryItem, String, InvInvtryItem> converter = new ListToMapConverter<InvInvtryItem, String, InvInvtryItem>();
		Map<String, InvInvtryItem> map = converter.conver(savedItems, mapper);
		return map;			
	}


	/**
	 * generateInvNbr.
	 *
	 * @param invtry
	 * @return {@link InvInvtry}
	 */
	private InvInvtry generateInvNbr(InvInvtry invtry) {
		String invtryNbr = invtry.getInvtryNbr();
		if(StringUtils.isBlank(invtryNbr)) {
			invtryNbr = SequenceGenerator.getSequence(SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE);
		}
		invtry.setInvtryNbr(invtryNbr);
		return invtry;
	}
	

	/**
	 * saveInvtry.
	 *
	 * @param invtry
	 * @return {@link InvInvtry}
	 */
	private InvInvtry saveInvtry(InvInvtry invtry) {
		String identif = invtry.getIdentif();
		if(StringUtils.isBlank(identif)) {
			invtryEJB.create(invtry);
		}else {
			invtryEJB.update(invtry);
		}
		return invtry;
	}


	/**
	 * computeInvtryGap.
	 *
	 * @param invtry
	 * @param savedItems
	 * @return {@link InvInvtry}
	 */
	private InvInvtry computeInvtryGap(InvInvtry invtry,
			List<InvInvtryItem> savedItems) {
		
		Contract.checkIllegalArgumentException(invtry);
		BigDecimal gapPurchAmtHT = BigDecimal.ZERO;
		BigDecimal gapSaleAmtHT = BigDecimal.ZERO;
		
		for (InvInvtryItem invInvtryItem : savedItems) {
			BigDecimal gapTotalPpPT = invInvtryItem.getGapTotalPpPT();
			BigDecimal gapTotalSpPT = invInvtryItem.getGapTotalSpPT();
			gapPurchAmtHT.add(gapTotalPpPT);//TODO :what do we do if it doesn't exist
			gapSaleAmtHT.add(gapTotalSpPT);//TODO : what do we do if it doesn't exist
		}
		invtry.setGapPurchAmtHT(gapPurchAmtHT);
		invtry.setGapSaleAmtHT(gapSaleAmtHT);
		
		return invtry;
	}


	/**
	 * addInvIntryItem.
	 *
	 * @param invInvtryItem
	 * @return {@link InvInvtryItem}
	 */
	private InvInvtryItem addInvIntryItem(InvInvtry invtry,InvInvtryItem invInvtryItem) {
		if(invInvtryItem == null) throw new IllegalArgumentException("null inventryitem");
		String invtryNbr = invtry.getInvtryNbr();
		if(StringUtils.isBlank(invtryNbr)) throw new IllegalStateException("Invalid invtry number");
		invInvtryItem.setInvtryNbr(invtryNbr);
		computeGaps(invInvtryItem);
		if(StringUtils.isBlank(invInvtryItem.getIdentif())) {
			invtryItemEJB.create(invInvtryItem);
		}else {
			InvInvtryItem existing = invtryItemEJB.findById(invInvtryItem.getId());
			invInvtryItem.copyTo(existing);
			invtryItemEJB.update(existing);
		}
		return invInvtryItem;
	}




	/**
	 * computeGaps.
	 *
	 * @param invInvtryItem
	 * @return InvInvtryItem
	 */
	private InvInvtryItem computeGaps(InvInvtryItem invInvtryItem) {
		Contract.checkIllegalArgumentException(invInvtryItem);
		BigDecimal pppuPT = invInvtryItem.getPppuPT();
		BigDecimal sppuPT = invInvtryItem.getSppuPT();
		Contract.checkIllegalStateException(pppuPT);
		Contract.checkIllegalStateException(sppuPT);
		
		BigDecimal expectedQty = invInvtryItem.getExpectedQty();
		BigDecimal asseccedQty = invInvtryItem.getAsseccedQty();
		BigDecimal gap = BigDecimalUtils.subs(asseccedQty, expectedQty);
		invInvtryItem.setGap(gap);
		invInvtryItem.setGapTotalPpPT(pppuPT.multiply(gap));
		invInvtryItem.setGapTotalSpPT(sppuPT.multiply(gap));
		return invInvtryItem;
	}
	
	
}