package org.adorsys.adprocmt.jpa;

import java.util.Date;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.CurrencyEnum;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("PrcmtDelivery_description")
public class PrcmtDelivery extends PrcmtAbstractDelivery {
	private static final long serialVersionUID = -644543225855706107L;

	public void fillDataFromOrder(PrcmtProcOrder procOrder) {
	
		this.setDlvryCur(StringUtils.isNotBlank(procOrder.getPoCur())?procOrder.getPoCur():CurrencyEnum.XAF.name());
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