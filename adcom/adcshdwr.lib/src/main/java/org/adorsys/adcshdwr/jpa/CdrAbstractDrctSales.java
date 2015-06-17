/**
 * 
 */
package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.adcore.utils.RoundAmount;
import org.adorsys.javaext.description.Description;


@MappedSuperclass
@Description("CdrDrctSales_description")
public class CdrAbstractDrctSales extends AbstractIdentifData {

	private static final long serialVersionUID = 1100532460425251752L;

	@Column
	@Description("CdrDrctSales_dsNbr_description")
	@NotNull
	private String dsNbr;
	
	/*
	 * This is the original dsNbr, that can be used to track
	 * the original invoice of a returned item.
	 * 
	 * When this is not null, we reuse the rebates defined 
	 * there to process the return.
	 */
	@Column
	@Description("CdrDsArtItem_origDsNbr_description")
	private String origDsNbr;

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

	public String getOrigDsNbr() {
		return origDsNbr;
	}

	public void setOrigDsNbr(String origDsNbr) {
		this.origDsNbr = origDsNbr;
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

	/**
	 * clearAmts.
	 *
	 */
	public void clearAmts() {

		this.grossSPPreTax=BigDecimal.ZERO;
		this.rebate = BigDecimal.ZERO;
		this.netSPPreTax=BigDecimal.ZERO;
		this.vatAmount=BigDecimal.ZERO;
		this.netSPTaxIncl=BigDecimal.ZERO;
		this.netSalesAmt=BigDecimal.ZERO;
		this.netAmtToPay=BigDecimal.ZERO;
	}

	public void copyTo(CdrAbstractDrctSales target){
		target.dsNbr=dsNbr;
		target.dsCur=dsCur;
		target.grossSPPreTax=grossSPPreTax;
		target.rebate=rebate;
		target.netSPPreTax=netSPPreTax;
		target.vatAmount=vatAmount;
		target.netSPTaxIncl=netSPTaxIncl;
		target.pymtDscntPct=pymtDscntPct;
		target.pymtDscntAmt=pymtDscntAmt;
		target.netSalesAmt=netSalesAmt;
		target.rdngDscntAmt=rdngDscntAmt;
		target.netAmtToPay=netAmtToPay;
		target.cashier=cashier;
		target.cdrNbr=cdrNbr;
		target.rcptNbr=rcptNbr;
		target.rcptPrntDt=rcptPrntDt;
	}
	
	public void addGrossSPPreTax(BigDecimal grossSPPreTax) {
		if(this.grossSPPreTax==null)this.grossSPPreTax=BigDecimal.ZERO;
		this.grossSPPreTax = this.grossSPPreTax.add(grossSPPreTax);
	}

	public void addRebate(BigDecimal rebate) {
		if(this.rebate==null) this.rebate = BigDecimal.ZERO;
		this.rebate=this.rebate.add(rebate);
	}

	public void addNetSPPreTax(BigDecimal netSPPreTax) {
		if(this.netSPPreTax==null) this.netSPPreTax=BigDecimal.ZERO;
		this.netSPPreTax = this.netSPPreTax.add(netSPPreTax);
	}

	public void addVatAmount(BigDecimal vatAmt) {
		if(this.vatAmount==null)this.vatAmount=BigDecimal.ZERO;
		this.vatAmount = this.vatAmount.add(vatAmt);
	}

	public void addNetSPTaxIncl(BigDecimal netSPTaxIncl) {
		if(this.netSPTaxIncl==null)this.netSPTaxIncl=BigDecimal.ZERO;
		this.netSPTaxIncl=this.netSPTaxIncl.add(netSPTaxIncl);
	}

	public void evlte() {
		

		if(this.pymtDscntPct!=null)
			this.pymtDscntAmt = FinancialOps.amtFromPrct(this.netSPPreTax, this.pymtDscntPct, this.dsCur);
		
	if(this.pymtDscntAmt==null) this.pymtDscntAmt=BigDecimal.ZERO;
	
	this.netSalesAmt = FinancialOps.substract(this.netSPTaxIncl, this.pymtDscntAmt, this.dsCur);
	
	RoundAmount roundAmount = new RoundAmount();
	roundAmount.roundIt(this.netSalesAmt);
	this.netAmtToPay = roundAmount.getAmount();
	this.rdngDscntAmt = roundAmount.getRoundDiscount();
	
	/*if(this.rdngDscntAmt==null) this.rdngDscntAmt=BigDecimal.ZERO;
	this.netAmtToPay = FinancialOps.add(this.netSalesAmt, this.rdngDscntAmt, this.dsCur);*/
	}
}
