package org.adorsys.adsales.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsSalesOrder_description")
public class SlsSalesOrder extends AbstractIdentifData {

	private static final long serialVersionUID = 8702438704782113053L;

	@Column
	@Description("SlsSalesOrder_soNbr_description")
	@NotNull
	private String soNbr;

	@Column
	@Description("SlsSalesOrder_soStatus_description")
	private String soStatus;

	@Column
	@Description("SlsSalesOrder_soCur_description")
	private String soCur;

	@Column
	@Description("SlsSalesOrder_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsSalesOrder_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("SlsSalesOrder_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsSalesOrder_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsSalesOrder_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsSalesOrder_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("SlsSalesOrder_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("SlsSalesOrder_netSalesAmt_description")
	private BigDecimal netSalesAmt;

	@Column
	@Description("SlsSalesOrder_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("SlsSalesOrder_netAmtToPay_description")
	private BigDecimal netAmtToPay;

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}

	public String getSoStatus() {
		return this.soStatus;
	}

	public void setSoStatus(final String soStatus) {
		this.soStatus = soStatus;
	}

	public String getSoCur() {
		return this.soCur;
	}

	public void setSoCur(final String soCur) {
		this.soCur = soCur;
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

	@Override
	protected String makeIdentif() {
		return soNbr;
	}
}