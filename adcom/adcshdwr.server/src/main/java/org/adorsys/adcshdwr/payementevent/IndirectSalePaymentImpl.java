package org.adorsys.adcshdwr.payementevent;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcshdwr.exceptions.AdException;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.jpa.CdrPymntItem;
import org.adorsys.adcshdwr.jpa.CdrPymntMode;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrCstmrVchrEJB;
import org.adorsys.adcshdwr.rest.CdrPymntEJB;
import org.adorsys.adcshdwr.rest.CdrPymntItemEJB;
import org.apache.commons.lang3.StringUtils;

/**
* 
* @author guymoyo
*
*/

@Stateless
public class IndirectSalePaymentImpl {

	@Inject
	private CdrPymntItemEJB cdrPymntItemEJB;
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	@Inject
	private CdrPymntEJB cdrPymntEJB;
	@Inject
	private CdrCstmrVchrEJB cdrCstmrVchrEJB;
	
	public void pay(PaymentEvent pymtEvt) throws AdException{
		
		if(pymtEvt == null) throw new AdException("No Payment");
		if(StringUtils.isBlank(pymtEvt.getSaleNbr())) throw new AdException("No Sale Number");
		if(pymtEvt.getAmt() == null) throw new AdException("No Amount to pay");
		if(pymtEvt.getRcvdAmt() == null) throw new AdException("No Recieved amount");
		if(BigDecimal.ZERO.compareTo(pymtEvt.getAmt()) == 1) throw new AdException("Amount Net to pay less than 0");
		if(BigDecimal.ZERO.compareTo(pymtEvt.getRcvdAmt()) == 1) throw new AdException("Received Amount less than 0");
		if(pymtEvt.getAmt().compareTo(pymtEvt.getRcvdAmt()) == 1) throw new AdException("Received Amount less than Amount Net to Pay");
		
		
		CdrCshDrawer activeCshDrawer = cdrCshDrawerEJB.getActiveCshDrawer();
		if(activeCshDrawer == null) throw new AdException("No open cash drawer");
		
		CdrPymnt cdrPymnt = new CdrPymnt();
		if(StringUtils.isBlank(pymtEvt.getPymntNbr())) {	
			cdrPymnt.setCdrNbr(activeCshDrawer.getCdrNbr());
			cdrPymnt.setInvNbr(pymtEvt.getSaleNbr());
		    cdrPymnt = cdrPymntEJB.create(cdrPymnt);
		}else{
			List<CdrPymnt> list = cdrPymntEJB.findByPymntNbr(pymtEvt.getPymntNbr());
			if(list.isEmpty()) throw new AdException("Incorrect Payment Number");
			cdrPymnt = list.get(0);
		}
		
		if(pymtEvt.getPymntMode()==null) pymtEvt.setPymntMode(CdrPymntMode.CASH);
		if(pymtEvt.getPymntMode().equals(CdrPymntMode.VOUCHER)){
			if(StringUtils.isBlank(pymtEvt.getVchrNbr())) throw new AdException("No Voucher number");
			List<CdrCstmrVchr> listVcher = cdrCstmrVchrEJB.findByVchrNbr(pymtEvt.getVchrNbr());
			if(listVcher.isEmpty()) throw new AdException("No Voucher found with this number");
			CdrCstmrVchr cdrCstmrVchr = listVcher.get(0);
			if(pymtEvt.getAmt().compareTo(cdrCstmrVchr.getRestAmt()) == 1) throw new AdException("Voucher amount less than amount to pay");
			cdrCstmrVchr.AddAmtUsed(pymtEvt.getAmt());
			cdrCstmrVchr.evlte();
			cdrCstmrVchrEJB.update(cdrCstmrVchr);
		}
		
		CdrPymntItem pymntItem = new CdrPymntItem();
		pymntItem.setAmt(pymtEvt.getAmt());
		pymntItem.setRcvdAmt(pymtEvt.getRcvdAmt());
		pymntItem.setPymntNbr(cdrPymnt.getPymntNbr());
		pymntItem.setPymntMode(pymtEvt.getPymntMode());
		pymntItem.setPymntDocType("InDirect_Sale_Payment");
		pymntItem.evlte();
		pymntItem = cdrPymntItemEJB.create(pymntItem);	
		
		//update payment
		cdrPymnt.addAmnt(pymntItem.getAmt());
		cdrPymnt = cdrPymntEJB.update(cdrPymnt);	
		//update cshDrawer
		switch (pymtEvt.getPymntMode()) {
		case VOUCHER: activeCshDrawer.AddTtlVchrIn(pymntItem.getAmt());break;
		case CASH: activeCshDrawer.AddTtlCash(pymntItem.getAmt());break;
		case CHECK: activeCshDrawer.AddTtlCheck(pymntItem.getAmt());break;
		case CREDIT_CARD: activeCshDrawer.AddTtlCredCard(pymntItem.getAmt());break;
		}
		activeCshDrawer.evlte();
		cdrCshDrawerEJB.update(activeCshDrawer);
		
	}

}
