package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@MappedSuperclass
@Description("PrcmtProcOrder_description")
public abstract class PrcmtAbstractProcOrder extends AbstractMvmtData {

	private static final long serialVersionUID = 2603268059857640820L;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtProcOrder_orderDt_description")
	@NotNull
	private Date orderDt;

	@Column
	@Description("PrcmtProcOrder_poNbr_description")
	@NotNull
	private String poNbr;

	@Column
	@Description("PrcmtProcOrder_supplier_description")
	private String supplier;

	@Column
	@Description("PrcmtProcOrder_poCur_description")
	private String poCur;

	@Column
	@Description("PrcmtProcOrder_grossPPPreTax_description")
	private BigDecimal grossPPPreTax;

	@Column
	@Description("PrcmtProcOrder_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("PrcmtProcOrder_netPPPreTax_description")
	private BigDecimal netPPPreTax;

	@Column
	@Description("PrcmtProcOrder_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("PrcmtProcOrder_netPPTaxIncl_description")
	private BigDecimal netPPTaxIncl;

	@Column
	@Description("PrcmtProcOrder_pymtDscntPct_description")
	private BigDecimal pymtDscntPct;

	@Column
	@Description("PrcmtProcOrder_pymtDscntAmt_description")
	private BigDecimal pymtDscntAmt;

	@Column
	@Description("PrcmtProcOrder_netPurchAmt_description")
	private BigDecimal netPurchAmt;

	@Column
	@Description("PrcmtProcOrder_rdngDscntAmt_description")
	private BigDecimal rdngDscntAmt;

	@Column
	@Description("PrcmtProcOrder_netAmtToPay_description")
	private BigDecimal netAmtToPay;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtProcOrder_submitedDt_description")
	private Date submitedDt;

	@Column
	@Description("PrcmtProcOrder_ordrngOrgUnit_description")
	@NotNull
	private String ordrngOrgUnit;

	@Column
	@Description("PrcmtProcOrder_creatingUsr_description")
	@NotNull
	private String creatingUsr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtProcOrder_createdDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date createdDt;

	@Column
	@Description("PrcmtProcOrder_poTriggerMode_description")
	@NotNull
	private String poTriggerMode;

	@Column
	@Description("PrcmtProcOrder_poType_description")
	@NotNull
	private String poType;

	@Column
	@Description("PrcmtProcOrder_poStatus_description")
	@NotNull
	private String poStatus;

	@PrePersist
	public void prePersist() {
		if (getId() == null)
			setId(poNbr);
	}
	
	public void copyTo(PrcmtAbstractProcOrder target){
		target.creatingUsr = creatingUsr;
		target.createdDt = createdDt;
		target.poCur = poCur;
		target.poNbr = poNbr;
		target.poStatus = poStatus;
		target.grossPPPreTax = grossPPPreTax;
		target.netAmtToPay = netAmtToPay;
		target.netPPPreTax = netPPPreTax;
		target.netPPTaxIncl = netPPTaxIncl;
		target.netPurchAmt = netPurchAmt;
		target.orderDt = orderDt;
		target.pymtDscntAmt = pymtDscntAmt;
		target.pymtDscntPct = pymtDscntPct;
		target.poType = poType;
		target.poTriggerMode = poTriggerMode;
		target.rdngDscntAmt = rdngDscntAmt;
		target.rebate = rebate;
		target.supplier = supplier;
		target.vatAmount = vatAmount;
		target.submitedDt = submitedDt;
		target.ordrngOrgUnit = ordrngOrgUnit;
	}
	
	public Date getOrderDt() {
		return this.orderDt;
	}

	public void setOrderDt(final Date orderDt) {
		this.orderDt = orderDt;
	}

	public String getPoNbr() {
		return this.poNbr;
	}

	public void setPoNbr(final String poNbr) {
		this.poNbr = poNbr;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(final String supplier) {
		this.supplier = supplier;
	}

	public String getPoCur() {
		return this.poCur;
	}

	public void setPoCur(final String poCur) {
		this.poCur = poCur;
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

	public Date getSubmitedDt() {
		return this.submitedDt;
	}

	public void setSubmitedDt(final Date submitedDt) {
		this.submitedDt = submitedDt;
	}

	public String getOrdrngOrgUnit() {
		return this.ordrngOrgUnit;
	}

	public void setOrdrngOrgUnit(final String ordrngOrgUnit) {
		this.ordrngOrgUnit = ordrngOrgUnit;
	}

	public String getCreatingUsr() {
		return this.creatingUsr;
	}

	public void setCreatingUsr(final String creatingUsr) {
		this.creatingUsr = creatingUsr;
	}

	public Date getCreatedDt() {
		return this.createdDt;
	}

	public void setCreatedDt(final Date createdDt) {
		this.createdDt = createdDt;
	}

	public String getPoStatus() {
		return poStatus;
	}

	public void setPoStatus(String poStatus) {
		this.poStatus = poStatus;
	}

	public String getPoTriggerMode() {
		return poTriggerMode;
	}

	public void setPoTriggerMode(String poTriggerMode) {
		this.poTriggerMode = poTriggerMode;
	}

	public String getPoType() {
		return poType;
	}

	public void setPoType(String poType) {
		this.poType = poType;
	}

}