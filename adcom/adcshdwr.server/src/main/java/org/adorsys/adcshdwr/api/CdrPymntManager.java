/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.payementevent.IndirectSale;
import org.adorsys.adcshdwr.payementevent.PaymentEvent;
import org.adorsys.adcshdwr.rest.CdrPymntItemEJB;

/**
 * @author guymoyo
 *
 */
@Stateless
public class CdrPymntManager {

	@Inject
	private CdrPymntItemEJB pymntItemEJB;
		
	@Inject
    @IndirectSale
    Event<PaymentEvent> indirectSaleEvent;
	
	/*public CdrPymntHolder saveAndClovePymt(CdrPymntHolder cdrPymntHolder) throws AdException {
		CdrPymnt cdrPymnt = cdrPymntHolder.getCdrPymnt();
		CdrCshDrawer activeCshDrawer = cshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new AdException("No opened cash drawer found for this session, please open one");
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
				throw new AdException("Missing doc number identification code.");

			if(StringUtils.isNotBlank(pymtItem.getId())){
				// todo check mdified
				CdrPymntItem persPi = pymntItemEJB.findById(pymtItem.getId());
				if(persPi==null) throw new AdException("Missing delivery item with id: " + pymtItem.getId());
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
	}*/

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

	public CdrPymntHolder savePymt(CdrPymntHolder cdrPymntHolder) {
		if(cdrPymntHolder.getRcvdAmt() == null || BigDecimal.ZERO.compareTo(cdrPymntHolder.getRcvdAmt()) == 1)
			cdrPymntHolder.setRcvdAmt(cdrPymntHolder.getAmt());
		
		PaymentEvent paymentEvent = new PaymentEvent(cdrPymntHolder.getPymntMode(), cdrPymntHolder.getAmt(), cdrPymntHolder.getRcvdAmt(),
				new Date(), cdrPymntHolder.getInvceNbr(), cdrPymntHolder.getVchrNbr(), cdrPymntHolder.getPymntNbr());	
		indirectSaleEvent.fire(paymentEvent);
		
		//search pymtHolder and return
		return cdrPymntHolder;
	}
}
