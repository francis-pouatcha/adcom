package org.adorsys.adsales.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("SlsSalesOrder_description")
public class SlsSalesOrder extends AbstractIdentifData {

	private static final long serialVersionUID = 8702438704782113053L;

	@Column
	@Description("SlsSalesOrder_soNbr_description")
	@NotNull
	private String soNbr;
	
	@Column
	@Description("SlsSalesOrder_acsngUser_description")
	@NotNull
	private String acsngUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsSalesOrder_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date acsngDt;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("SlsSalesOrder_soDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date soDt;

	@Column
	@Description("SlsSalesOrder_soStatus_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private BaseProcessStatusEnum soStatus;

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
	@Description("SlsSalesOrder_rebatePct_description")
	private BigDecimal rebatePct;

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


	public BaseProcessStatusEnum getSoStatus() {
		return soStatus;
	}

	public void setSoStatus(BaseProcessStatusEnum soStatus) {
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

	public BigDecimal getRebatePct() {
		return rebatePct;
	}

	public void setRebatePct(BigDecimal rebatePct) {
		this.rebatePct = rebatePct;
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
	
	

	public String getAcsngUser() {
		return acsngUser;
	}

	public void setAcsngUser(String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public Date getAcsngDt() {
		return acsngDt;
	}

	public void setAcsngDt(Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	public Date getSoDt() {
		return soDt;
	}

	public void setSoDt(Date soDt) {
		this.soDt = soDt;
	}

	@Override
	protected String makeIdentif() {
		return soNbr;
	}
}