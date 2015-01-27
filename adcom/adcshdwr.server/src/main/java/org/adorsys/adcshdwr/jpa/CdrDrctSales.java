package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrDrctSales_description")
public class CdrDrctSales extends AbstractIdentifData {

	private static final long serialVersionUID = 1100532460425251752L;

	@Column
	@Description("CdrDrctSales_dsNbr_description")
	@NotNull
	private String dsNbr;

	@Column
	@Description("CdrDrctSales_dsCur_description")
	private String dsCur;

	@Column
	@Description("CdrDrctSales_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("CdrDrctSales_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("CdrDrctSales_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("CdrDrctSales_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("CdrDrctSales_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("CdrDrctSales_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("CdrDrctSales_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("CdrDrctSales_netSalesAmt_description")
	private BigDecimal netSalesAmt;

	@Column
	@Description("CdrDrctSales_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("CdrDrctSales_netAmtToPay_description")
	private BigDecimal netAmtToPay;

	@Column
	@Description("CdrDrctSales_cashier_description")
	@NotNull
	private String cashier;

	@Column
	@Description("CdrDrctSales_cdrNbr_description")
	private String cdrNbr;

	@Column
	@Description("CdrDrctSales_rcptNbr_description")
	@NotNull
	private String rcptNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrDrctSales_rcptPrntDt_description")
	private Date rcptPrntDt;

	public String getDsNbr() {
		return this.dsNbr;
	}

	public void setDsNbr(final String dsNbr) {
		this.dsNbr = dsNbr;
	}

	public String getDsCur() {
		return this.dsCur;
	}

	public void setDsCur(final String dsCur) {
		this.dsCur = dsCur;
	}

	public BigDecimal getGrossSPPreTax() {
		return this.grossSPPreTax;
	}

	public void setGrossSPPreTax(final BigDecimal grossSPPreTax) {
		this.grossSPPreTax = grossSPPreTax;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(final BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getNetSPPreTax() {
		return this.netSPPreTax;
	}

	public void setNetSPPreTax(final BigDecimal netSPPreTax) {
		this.netSPPreTax = netSPPreTax;
	}

	public BigDecimal getVatAmount() {
		return this.vatAmount;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetSPTaxIncl() {
		return this.netSPTaxIncl;
	}

	public void setNetSPTaxIncl(final BigDecimal netSPTaxIncl) {
		this.netSPTaxIncl = netSPTaxIncl;
	}

	public BigDecimal getPymtDscntPct() {
		return this.pymtDscntPct;
	}

	public void setPymtDscntPct(final BigDecimal pymtDscntPct) {
		this.pymtDscntPct = pymtDscntPct;
	}

	public BigDecimal getPymtDscntAmt() {
		return this.pymtDscntAmt;
	}

	public void setPymtDscntAmt(final BigDecimal pymtDscntAmt) {
		this.pymtDscntAmt = pymtDscntAmt;
	}

	public BigDecimal getNetSalesAmt() {
		return this.netSalesAmt;
	}

	public void setNetSalesAmt(final BigDecimal netSalesAmt) {
		this.netSalesAmt = netSalesAmt;
	}

	public BigDecimal getRdngDscntAmt() {
		return this.rdngDscntAmt;
	}

	public void setRdngDscntAmt(final BigDecimal rdngDscntAmt) {
		this.rdngDscntAmt = rdngDscntAmt;
	}

	public BigDecimal getNetAmtToPay() {
		return this.netAmtToPay;
	}

	public void setNetAmtToPay(final BigDecimal netAmtToPay) {
		this.netAmtToPay = netAmtToPay;
	}

	public String getCashier() {
		return this.cashier;
	}

	public void setCashier(final String cashier) {
		this.cashier = cashier;
	}

	public String getCdrNbr() {
		return this.cdrNbr;
	}

	public void setCdrNbr(final String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}

	public String getRcptNbr() {
		return this.rcptNbr;
	}

	public void setRcptNbr(final String rcptNbr) {
		this.rcptNbr = rcptNbr;
	}

	public Date getRcptPrntDt() {
		return this.rcptPrntDt;
	}

	public void setRcptPrntDt(final Date rcptPrntDt) {
		this.rcptPrntDt = rcptPrntDt;
	}

	@Override
	protected String makeIdentif() {
		return dsNbr;
	}
}