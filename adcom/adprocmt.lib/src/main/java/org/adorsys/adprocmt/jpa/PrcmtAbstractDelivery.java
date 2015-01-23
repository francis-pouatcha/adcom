package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.adorsys.javaext.format.NumberFormatType;
import org.adorsys.javaext.format.NumberType;

@MappedSuperclass
@Description("PrcmtDelivery_description")
public class PrcmtAbstractDelivery extends AbstractMvmtData {

	private static final long serialVersionUID = -7543043082815267044L;

	@Column
	@Description("PrcmtDelivery_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

	@Column
	@Description("PrcmtDelivery_dlvrySlipNbr_description")
	@NotNull(message = "PrcmtDelivery_dlvrySlipNbr_NotNull_validation")
	private String dlvrySlipNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDelivery_dtOnDlvrySlip_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy")
	private Date dtOnDlvrySlip;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDelivery_orderDt_description")
	private Date orderDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDelivery_dlvryDt_description")
	@NotNull
	private Date dlvryDt;

	@Column
	@Description("PrcmtDelivery_supplier_description")
	private String supplier;

	@Column
	@Description("PrcmtDelivery_dlvryCur_description")
	private String dlvryCur;

	@Column
	@Description("PrcmtDelivery_grossPPPreTax_description")
	private BigDecimal grossPPPreTax;

	@Column
	@Description("PrcmtDelivery_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("PrcmtDelivery_netPPPreTax_description")
	private BigDecimal netPPPreTax;

	@Column
	@Description("PrcmtDelivery_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("PrcmtDelivery_netPPTaxIncl_description")
	private BigDecimal netPPTaxIncl;

	@Column
	@Description("PrcmtDelivery_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("PrcmtDelivery_pymtDscntAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("PrcmtDelivery_netPurchAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal netPurchAmt;

	@Column
	@Description("PrcmtDelivery_rdngDscntAmt_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("PrcmtDelivery_netAmtToPay_description")
	@NumberFormatType(NumberType.CURRENCY)
	private BigDecimal netAmtToPay;

	@Column
	@Description("PrcmtDelivery_dlvryStatus_description")
	@NotNull
	private String dlvryStatus;

	@Column
	@Description("PrcmtDelivery_rcvngOrgUnit_description")
	private String rcvngOrgUnit;

	@Column
	@Description("PrcmtDelivery_creatingUsr_description")
	@NotNull
	private String creatingUsr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDelivery_creationDt_description")
	@NotNull
	private Date creationDt;

	public String getDlvryNbr() {
		return this.dlvryNbr;
	}

	public void setDlvryNbr(final String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

	public String getDlvrySlipNbr() {
		return this.dlvrySlipNbr;
	}

	public void setDlvrySlipNbr(final String dlvrySlipNbr) {
		this.dlvrySlipNbr = dlvrySlipNbr;
	}

	public Date getDtOnDlvrySlip() {
		return this.dtOnDlvrySlip;
	}

	public void setDtOnDlvrySlip(final Date dtOnDlvrySlip) {
		this.dtOnDlvrySlip = dtOnDlvrySlip;
	}

	public Date getOrderDt() {
		return this.orderDt;
	}

	public void setOrderDt(final Date orderDt) {
		this.orderDt = orderDt;
	}

	public Date getDlvryDt() {
		return this.dlvryDt;
	}

	public void setDlvryDt(final Date dlvryDt) {
		this.dlvryDt = dlvryDt;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(final String supplier) {
		this.supplier = supplier;
	}

	public String getDlvryCur() {
		return this.dlvryCur;
	}

	public void setDlvryCur(final String dlvryCur) {
		this.dlvryCur = dlvryCur;
	}

	public BigDecimal getGrossPPPreTax() {
		return this.grossPPPreTax;
	}

	public void setGrossPPPreTax(final BigDecimal grossPPPreTax) {
		this.grossPPPreTax = grossPPPreTax;
	}

	public BigDecimal getRebate() {
		return this.rebate;
	}

	public void setRebate(final BigDecimal rebate) {
		this.rebate = rebate;
	}

	public BigDecimal getNetPPPreTax() {
		return this.netPPPreTax;
	}

	public void setNetPPPreTax(final BigDecimal netPPPreTax) {
		this.netPPPreTax = netPPPreTax;
	}

	public BigDecimal getVatAmount() {
		return this.vatAmount;
	}

	public void setVatAmount(final BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}

	public BigDecimal getNetPPTaxIncl() {
		return this.netPPTaxIncl;
	}

	public void setNetPPTaxIncl(final BigDecimal netPPTaxIncl) {
		this.netPPTaxIncl = netPPTaxIncl;
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

	public BigDecimal getNetPurchAmt() {
		return this.netPurchAmt;
	}

	public void setNetPurchAmt(final BigDecimal netPurchAmt) {
		this.netPurchAmt = netPurchAmt;
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

	public String getRcvngOrgUnit() {
		return this.rcvngOrgUnit;
	}

	public void setRcvngOrgUnit(final String rcvngOrgUnit) {
		this.rcvngOrgUnit = rcvngOrgUnit;
	}

	public String getCreatingUsr() {
		return this.creatingUsr;
	}

	public void setCreatingUsr(final String creatingUsr) {
		this.creatingUsr = creatingUsr;
	}

	public Date getCreationDt() {
		return this.creationDt;
	}

	public void setCreationDt(final Date creationDt) {
		this.creationDt = creationDt;
	}

	public String getDlvryStatus() {
		return dlvryStatus;
	}

	public void setDlvryStatus(String dlvryStatus) {
		this.dlvryStatus = dlvryStatus;
	}
}