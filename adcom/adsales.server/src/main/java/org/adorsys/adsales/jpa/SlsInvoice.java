package org.adorsys.adsales.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsInvoice_description")
public class SlsInvoice extends AbstractIdentifData {

	private static final long serialVersionUID = -5368159204977758066L;

	@Column
	@Description("SlsInvoice_invceType_description")
	@NotNull
	private String invceType;

	@Column
	@Description("SlsInvoice_invceNbr_description")
	@NotNull
	private String invceNbr;

	@Column
	@Description("SlsInvoice_soNbr_description")
	@NotNull
	private String soNbr;

	@Column
	@Description("SlsInvoice_invceStatus_description")
	@NotNull
	private String invceStatus;

	@Column
	@Description("SlsInvoice_invceCur_description")
	private String invceCur;

	@Column
	@Description("SlsInvoice_grossSPPreTax_description")
	private BigDecimal grossSPPreTax;

	@Column
	@Description("SlsInvoice_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("SlsInvoice_netSPPreTax_description")
	private BigDecimal netSPPreTax;

	@Column
	@Description("SlsInvoice_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("SlsInvoice_netSPTaxIncl_description")
	private BigDecimal netSPTaxIncl;

	@Column
	@Description("SlsInvoice_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("SlsInvoice_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("SlsInvoice_netSalesAmt_description")
	private BigDecimal netSalesAmt;

	@Column
	@Description("SlsInvoice_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("SlsInvoice_netAmtToPay_description")
	private BigDecimal netAmtToPay;

	public String getInvceType() {
		return this.invceType;
	}

	public void setInvceType(final String invceType) {
		this.invceType = invceType;
	}

	public String getInvceNbr() {
		return this.invceNbr;
	}

	public void setInvceNbr(final String invceNbr) {
		this.invceNbr = invceNbr;
	}

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}

	public String getInvceStatus() {
		return this.invceStatus;
	}

	public void setInvceStatus(final String invceStatus) {
		this.invceStatus = invceStatus;
	}

	public String getInvceCur() {
		return this.invceCur;
	}

	public void setInvceCur(final String invceCur) {
		this.invceCur = invceCur;
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
		return invceNbr;
	}
}