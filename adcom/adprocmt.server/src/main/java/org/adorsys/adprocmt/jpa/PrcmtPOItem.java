package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("PrcmtPOItem_description")
public class PrcmtPOItem extends AbstractMvmtData {

	private static final long serialVersionUID = -8274314551216824192L;

	@Column
	@Description("PrcmtPOItem_poNbr_description")
	@NotNull
	private String poNbr;

	@Column
	@Description("PrcmtPOItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("PrcmtPOItem_supplier_description")
	private String supplier;

	@Column
	@Description("PrcmtPOItem_qtyOrdered_description")
	@NotNull
	private BigDecimal qtyOrdered;

	@Column
	@Description("PrcmtPOItem_stkQtyPreOrder_description")
	@NotNull
	private BigDecimal stkQtyPreOrder;

	@Column
	@Description("PrcmtPOItem_pppuPreTax_description")
	private BigDecimal pppuPreTax;

	@Column
	@Description("PrcmtPOItem_pppuCur_description")
	private String pppuCur;

	@Column
	@Description("PrcmtPOItem_grossPPPreTax_description")
	private BigDecimal grossPPPreTax;

	@Column
	@Description("PrcmtPOItem_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("PrcmtPOItem_netPPPreTax_description")
	private BigDecimal netPPPreTax;

	@Column
	@Description("PrcmtPOItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("PrcmtPOItem_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("PrcmtPOItem_netPPTaxIncl_description")
	private BigDecimal netPPTaxIncl;

	@Column
	@Description("PrcmtPOItem_creatingUsr_description")
	@NotNull
	private String creatingUsr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtPOItem_createdDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date createdDt;

	public String getPoNbr() {
		return this.poNbr;
	}

	public void setPoNbr(final String poNbr) {
		this.poNbr = poNbr;
	}

	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(final String supplier) {
		this.supplier = supplier;
	}

	public BigDecimal getQtyOrdered() {
		return this.qtyOrdered;
	}

	public void setQtyOrdered(final BigDecimal qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public BigDecimal getStkQtyPreOrder() {
		return this.stkQtyPreOrder;
	}

	public void setStkQtyPreOrder(final BigDecimal stkQtyPreOrder) {
		this.stkQtyPreOrder = stkQtyPreOrder;
	}

	public BigDecimal getPppuPreTax() {
		return this.pppuPreTax;
	}

	public void setPppuPreTax(final BigDecimal pppuPreTax) {
		this.pppuPreTax = pppuPreTax;
	}

	public String getPppuCur() {
		return this.pppuCur;
	}

	public void setPppuCur(final String pppuCur) {
		this.pppuCur = pppuCur;
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

	public BigDecimal getVatPct() {
		return this.vatPct;
	}

	public void setVatPct(final BigDecimal vatPct) {
		this.vatPct = vatPct;
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
}