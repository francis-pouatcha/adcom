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

import org.adorsys.adcshdwr.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.payementevent.IndirectSale;
import org.adorsys.adcshdwr.payementevent.PaymentEvent;
import org.adorsys.adcshdwr.rest.CdrPymntEJB;
import org.adorsys.adcshdwr.rest.CdrPymntItemEJB;

/**
 * @author guymoyo
 *
 */
@Stateless
public class CdrPymntManager {

	
	@Inject
    @IndirectSale
    private Event<PaymentEvent> indirectSaleEvent;
	@Inject
	private CdrPymntEJB cdrPymntEJB;
	@Inject
	private CdrPymntItemEJB pymntItemEJB;
	

	public CdrPymntHolder savePymt(CdrPymntHolder cdrPymntHolder) throws AdException {
		if(cdrPymntHolder.getRcvdAmt() == null || BigDecimal.ZERO.compareTo(cdrPymntHolder.getRcvdAmt()) == 1)
			cdrPymntHolder.setRcvdAmt(cdrPymntHolder.getAmt());
		
		PaymentEvent paymentEvent = new PaymentEvent(cdrPymntHolder.getPymntMode(), cdrPymntHolder.getAmt(), cdrPymntHolder.getRcvdAmt(),
				new Date(), cdrPymntHolder.getInvceNbr(), cdrPymntHolder.getVchrNbr(), cdrPymntHolder.getPymntNbr());	
		
		try {
			indirectSaleEvent.fire(paymentEvent);
		} catch (Exception e) {
			throw new AdException(e.getMessage());
		}	
		//search pymtHolder and return
		List<CdrPymnt> listCdrPymnt = cdrPymntEJB.findByInvNbr(paymentEvent.getSaleNbr());
		String pymntNbr = listCdrPymnt.get(0).getPymntNbr();
		cdrPymntHolder.setPymntNbr(pymntNbr);
		List<CdrPymntItem> lsitItem = pymntItemEJB.findByPymntNbr(pymntNbr);
		cdrPymntHolder.getCdrPymntItem().clear();
		cdrPymntHolder.getCdrPymntItem().addAll(lsitItem);
		return cdrPymntHolder;
	}


	public CdrPymntHolder invPymt(String invNbr) {
		CdrPymntHolder cdrPymntHolder = new CdrPymntHolder();
		List<CdrPymnt> listCdrPymnt = cdrPymntEJB.findByInvNbr(invNbr);
		if(listCdrPymnt.isEmpty()) return cdrPymntHolder;
		
		String pymntNbr = listCdrPymnt.get(0).getPymntNbr();
		cdrPymntHolder.setPymntNbr(pymntNbr);
		List<CdrPymntItem> lsitItem = pymntItemEJB.findByPymntNbr(pymntNbr);
		cdrPymntHolder.getCdrPymntItem().clear();
		cdrPymntHolder.getCdrPymntItem().addAll(lsitItem);
		return cdrPymntHolder;
	}
}
