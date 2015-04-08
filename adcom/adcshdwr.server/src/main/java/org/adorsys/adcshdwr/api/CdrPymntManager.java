/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntObject;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrPymntEJB;
import org.adorsys.adcshdwr.rest.CdrPymntItemEJB;
import org.adorsys.adcshdwr.rest.CdrPymntObjectEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class CdrPymntManager {

	@Inject
	private CdrPymntEJB pymntEJB;

	@Inject
	private CdrPymntItemEJB pymntItemEJB;
	
	@Inject
	private CdrPymntObjectEJB pymntObjectEJB;
	
	@Inject
	private CdrCshDrawerEJB cshDrawerEJB;
	
	public CdrPymntHolder saveAndClovePymt(CdrPymntHolder cdrPymntHolder) {
		CdrPymnt cdrPymnt = cdrPymntHolder.getCdrPymnt();
		CdrCshDrawer activeCshDrawer = cshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new IllegalStateException("No opened cash drawer found for this session, please open one");
		cdrPymnt.setCdrNbr(activeCshDrawer.getCdrNbr());
		if(StringUtils.isBlank(cdrPymnt.getId())) {
			cdrPymnt = pymntEJB.create(cdrPymnt);
		}
		boolean modified = false;
		boolean itemModified = deleteHolders(cdrPymntHolder);

		List<CdrPymntItemHolder> pymtItems = cdrPymntHolder.getPymtItems();
		if(pymtItems == null) pymtItems = new ArrayList<CdrPymntItemHolder>();

		for (CdrPymntItemHolder itemHolder : pymtItems) {
			CdrPymntItem pymtItem = itemHolder.getPymtItem();

			if(StringUtils.isBlank(pymtItem.getPymntNbr()))
				pymtItem.setPymntNbr(cdrPymnt.getPymntNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(pymtItem.getPymntDocNbr()))
				throw new IllegalStateException("Missing doc number identification code.");

			if(StringUtils.isNotBlank(pymtItem.getId())){
				// todo check mdified
				CdrPymntItem persPi = pymntItemEJB.findById(pymtItem.getId());
				if(persPi==null) throw new IllegalStateException("Missing delivery item with id: " + pymtItem.getId());
				if(!pymtItem.contentEquals(persPi)){
					pymtItem.copyTo(persPi);
					pymtItem.evlte();
					pymtItem = pymntItemEJB.update(persPi);
					itemModified = true;
					CdrPymntObject persPo = pymntObjectEJB.findByOrigItemNbr(persPi.getIdentif());
					if(persPo != null) {
						CdrPymntObject pymntObject = pymtItem.toPymntObject();
						pymntObject.copyTo(persPo);
						persPo = pymntObjectEJB.update(persPo);
					}
				}
				
			} else {
				if (StringUtils.isNotBlank(pymtItem.getPymntNbr())) {
					CdrPymntItem persDi = pymntItemEJB.findById(pymtItem.getPymntNbr());
					if(persDi!=null){
						if(!pymtItem.contentEquals(persDi)){
							pymtItem.copyTo(persDi);
							pymtItem.evlte();
							pymtItem = pymntItemEJB.update(persDi);
							itemModified = true;
							//update the pymnt object
							CdrPymntObject persPo = pymntObjectEJB.findByOrigItemNbr(pymtItem.getIdentif());
							if(persPo != null) {
								CdrPymntObject pymntObject = pymtItem.toPymntObject();
								pymntObject.copyTo(persPo);
								persPo = pymntObjectEJB.update(persPo);
							}
						}
					} else {
						pymtItem.evlte();
						pymtItem.setPymntNbr(cdrPymnt.getPymntNbr());
						pymtItem = pymntItemEJB.create(pymtItem);
						itemModified = true;						
						CdrPymntObject pymntObject = pymtItem.toPymntObject();
						pymntObject = pymntObjectEJB.create(pymntObject);
					}
				} else {
					pymtItem.evlte();
					pymtItem = pymntItemEJB.create(pymtItem);
					itemModified = true;
					CdrPymntObject pymntObject = pymtItem.toPymntObject();
					pymntObject = pymntObjectEJB.create(pymntObject);
				}
			}

			itemHolder.setPymtItem(pymtItem);
		}

		if(itemModified){
			recomputePymt(cdrPymnt);
			//			cdrPymnt.setPoStatus(BaseProcessStatusEnum.ONGOING.name());
			cdrPymnt = pymntEJB.update(cdrPymnt);
			cdrPymntHolder.setCdrPymnt(cdrPymnt);
		}
		if(modified || itemModified){
			//			createModifiedOrderHistory(cdrPymnt);
		}
		return cdrPymntHolder;
	}

	/**
	 * recomputeOrder.
	 *
	 * @param cdrPymnt
	 */
	private void recomputePymt(CdrPymnt cdrPymnt) {
		// update CdrPmnt object.
		String pymntNbr = cdrPymnt.getPymntNbr();
		Long count = pymntItemEJB.countByPymntNbr(pymntNbr);
		int start = 0;
		int max = 100;
		cdrPymnt.clearAmts();
		while(start<=count){
			List<CdrPymntItem> list = pymntItemEJB.findByPymntNbr(pymntNbr, start, max);
			start +=max;
			for (CdrPymntItem pymntItem : list) {
				cdrPymnt.addAmnt(pymntItem.getAmt());
			}
		}
	}

	/**
	 * deleteHolders.
	 *
	 * @param cdrPymntHolder
	 * @return
	 */
	private boolean deleteHolders(CdrPymntHolder cdrPymntHolder) {
		List<CdrPymntItemHolder> pymtItems = cdrPymntHolder.getPymtItems();
		
		List<CdrPymntItemHolder> piToRemove = new ArrayList<CdrPymntItemHolder>();
		boolean modified = false;
		for (CdrPymntItemHolder itemHolder : pymtItems) {
			if(itemHolder.isDeleted()){
				CdrPymntItem pymtItem = itemHolder.getPymtItem();
				String id = StringUtils.isNotBlank(pymtItem.getId())?pymtItem.getId():"";
				if(StringUtils.isNotBlank(id)){
					pymntItemEJB.deleteById(id);
					modified = true;
					CdrPymntObject persPo = pymntObjectEJB.findByOrigItemNbr(pymtItem.getIdentif());
					if(persPo != null) {
						pymntObjectEJB.deleteById(persPo.getId());
					}
				}
				piToRemove.add(itemHolder);
			}
		}
		pymtItems.removeAll(piToRemove);
		return modified;
	}
}
