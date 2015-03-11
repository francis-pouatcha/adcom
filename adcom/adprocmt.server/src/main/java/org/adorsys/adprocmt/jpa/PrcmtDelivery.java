package org.adorsys.adprocmt.jpa;

import java.util.Date;

import javax.persistence.Entity;

import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtDelivery_description")
public class PrcmtDelivery extends PrcmtAbstractDelivery {
	private static final long serialVersionUID = -644543225855706107L;

	public void fillDataFromOrder(PrcmtProcOrder procOrder) {
	
		this.setDlvryCur(procOrder.getPoCur());
		this.setDlvryDt(new Date());
		this.setDlvrySlipNbr(procOrder.getPoNbr());
		this.setGrossPPPreTax(procOrder.getGrossPPPreTax());
		this.setNetAmtToPay(procOrder.getNetAmtToPay());
		this.setNetPPPreTax(procOrder.getNetPPPreTax());
		this.setNetPPTaxIncl(procOrder.getNetPPTaxIncl());
		this.setNetPurchAmt(procOrder.getNetPurchAmt());
		this.setOrderDt(procOrder.getOrderDt());
		this.setPymtDscntAmt(procOrder.getPymtDscntAmt());
		this.setPymtDscntPct(procOrder.getPymtDscntPct());
		this.setRcvngOrgUnit(procOrder.getOrdrngOrgUnit());
		this.setRdngDscntAmt(procOrder.getRdngDscntAmt());
		this.setRebate(procOrder.getRebate());
		this.setVatAmount(procOrder.getVatAmount());
		this.setSupplier(procOrder.getSupplier());
	}

	
}