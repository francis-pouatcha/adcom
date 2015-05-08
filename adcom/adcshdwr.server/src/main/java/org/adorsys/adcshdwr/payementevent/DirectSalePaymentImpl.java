package org.adorsys.adcshdwr.payementevent;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcshdwr.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrDsPymntItem;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrDsPymntItemEJB;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class DirectSalePaymentImpl {
	
	@Inject
	CdrDsPymntItemEJB cdrDsPymntItemEJB;
	@Inject
	CdrCshDrawerEJB cdrCshDrawerEJB;
	
	public void pay(PaymentEvent pymtEvt) throws AdException{
		
		if(pymtEvt == null) throw new AdException("No Payment");
		if(StringUtils.isBlank(pymtEvt.getSaleNbr())) throw new AdException("No Direct Sale Number");
		if(pymtEvt.getAmt() == null) throw new AdException("No Amount to pay");
		if(pymtEvt.getRcvdAmt() == null) throw new AdException("No Recieved amount");
		if(BigDecimal.ZERO.compareTo(pymtEvt.getAmt()) == 1) throw new AdException("Amount Net to pay less than 0");
		if(BigDecimal.ZERO.compareTo(pymtEvt.getRcvdAmt()) == 1) throw new AdException("Received Amount less than 0");	
		if(pymtEvt.getAmt().compareTo(pymtEvt.getRcvdAmt()) == 1) throw new AdException("Received Amount less than Amount Net to Pay");
		
		List<CdrDsPymntItem> dsPymntItems = cdrDsPymntItemEJB.findByDsNbr(pymtEvt.getSaleNbr());
		if(!dsPymntItems.isEmpty()) throw new AdException("Existing payment for this sale");
			
		CdrDsPymntItem dsPymntItem = new CdrDsPymntItem();
		dsPymntItem.setAmt(pymtEvt.getAmt());
		dsPymntItem.setRcvdAmt(pymtEvt.getRcvdAmt());
		dsPymntItem.setDsNbr(pymtEvt.getSaleNbr());
		dsPymntItem.setPymntMode(pymtEvt.getPymntMode());
		dsPymntItem.setPymntDocType("Direct_Sale_Payment");
		dsPymntItem.soustractDiffAmt();
		dsPymntItem = cdrDsPymntItemEJB.create(dsPymntItem);		
		//update cshDrawer
		CdrCshDrawer activeCshDrawer = cdrCshDrawerEJB.getActiveCshDrawer();
		activeCshDrawer.AddTtlCash(dsPymntItem.getAmt());
		activeCshDrawer.evlte();
		cdrCshDrawerEJB.update(activeCshDrawer);
		
	}

}
