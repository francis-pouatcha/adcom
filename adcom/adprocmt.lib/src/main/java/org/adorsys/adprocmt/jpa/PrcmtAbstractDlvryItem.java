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

@MappedSuperclass
@Description("PrcmtDlvryItem_description")
public class PrcmtAbstractDlvryItem extends AbstractMvmtData {

	private static final long serialVersionUID = 8446377222696584731L;

	@Column
	@Description("PrcmtDlvryItem_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

	@Column
	@Description("PrcmtDlvryItem_poNbr_description")
	private String poNbr;

	@Column
	@Description("PrcmtDlvryItem_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("PrcmtDlvryItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("PrcmtDlvryItem_supplier_description")
	private String supplier;

	@Column
	@Description("PrcmtDlvryItem_supplierPic_description")
	private String supplierPic;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDlvryItem_expirDt_description")
	private Date expirDt;

	@Column
	@Description("PrcmtDlvryItem_qtyOrdered_description")
	private BigDecimal qtyOrdered;

	@Column
	@Description("PrcmtDlvryItem_qtyDlvrd_description")
	@NotNull
	private BigDecimal qtyDlvrd;

	@Column
	@Description("PrcmtDlvryItem_freeQty_description")
	private BigDecimal freeQty;

	@Column
	@Description("PrcmtDlvryItem_stkQtyPreDlvry_description")
	private BigDecimal stkQtyPreDlvry;

	@Column
	@Description("PrcmtDlvryItem_pppuPreTax_description")
	private BigDecimal pppuPreTax;

	@Column
	@Description("PrcmtDlvryItem_pppuCur_description")
	private String pppuCur;

	@Column
	@Description("PrcmtDlvryItem_grossPPPreTax_description")
	private BigDecimal grossPPPreTax;

	@Column
	@Description("PrcmtDlvryItem_rebate_description")
	private BigDecimal rebate;

	@Column
	@Description("PrcmtDlvryItem_netPPPreTax_description")
	private BigDecimal netPPPreTax;

	@Column
	@Description("PrcmtDlvryItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("PrcmtDlvryItem_vatAmount_description")
	private BigDecimal vatAmount;

	@Column
	@Description("PrcmtDlvryItem_netPPTaxIncl_description")
	private BigDecimal netPPTaxIncl;

	@Column
	@Description("PrcmtDlvryItem_sppuPreTax_description")
	private BigDecimal sppuPreTax;

	@Column
	@Description("PrcmtDlvryItem_purchWrntyDys_description")
	private BigDecimal purchWrntyDys;

	@Column
	@Description("PrcmtDlvryItem_purchRtrnDays_description")
	private BigDecimal purchRtrnDays;

	@Column
	@Description("PrcmtDlvryItem_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("PrcmtDlvryItem_minSppuHT_description")
	private BigDecimal minSppuHT;

	@Column
	@Description("PrcmtDlvryItem_vatSalesPct_description")
	private BigDecimal vatSalesPct;

	@Column
	@Description("PrcmtDlvryItem_salesWrntyDys_description")
	private BigDecimal salesWrntyDys;

	@Column
	@Description("PrcmtDlvryItem_salesRtrnDays_description")
	private BigDecimal salesRtrnDays;

	@Column
	@Description("PrcmtDlvryItem_creatingUsr_description")
	@NotNull
	private String creatingUsr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDlvryItem_creationDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date creationDt;

	public String getDlvryNbr() {
		return this.dlvryNbr;
	}

	public void setDlvryNbr(final String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

	public String getPoNbr() {
		return this.poNbr;
	}

	public void setPoNbr(final String poNbr) {
		this.poNbr = poNbr;
	}

	public String getLotPic() {
		return this.lotPic;
	}

	public void setLotPic(final String lotPic) {
		this.lotPic = lotPic;
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

	public String getSupplierPic() {
		return this.supplierPic;
	}

	public void setSupplierPic(final String supplierPic) {
		this.supplierPic = supplierPic;
	}

	public Date getExpirDt() {
		return this.expirDt;
	}

	public void setExpirDt(final Date expirDt) {
		this.expirDt = expirDt;
	}

	public BigDecimal getQtyOrdered() {
		return this.qtyOrdered;
	}

	public void setQtyOrdered(final BigDecimal qtyOrdered) {
		this.qtyOrdered = qtyOrdered;
	}

	public BigDecimal getQtyDlvrd() {
		return this.qtyDlvrd;
	}

	public void setQtyDlvrd(final BigDecimal qtyDlvrd) {
		this.qtyDlvrd = qtyDlvrd;
	}

	public BigDecimal getFreeQty() {
		return this.freeQty;
	}

	public void setFreeQty(final BigDecimal freeQty) {
		this.freeQty = freeQty;
	}

	public BigDecimal getStkQtyPreDlvry() {
		return this.stkQtyPreDlvry;
	}

	public void setStkQtyPreDlvry(final BigDecimal stkQtyPreDlvry) {
		this.stkQtyPreDlvry = stkQtyPreDlvry;
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

	public BigDecimal getSppuPreTax() {
		return this.sppuPreTax;
	}

	public void setSppuPreTax(final BigDecimal sppuPreTax) {
		this.sppuPreTax = sppuPreTax;
	}

	public BigDecimal getPurchWrntyDys() {
		return this.purchWrntyDys;
	}

	public void setPurchWrntyDys(final BigDecimal purchWrntyDys) {
		this.purchWrntyDys = purchWrntyDys;
	}

	public BigDecimal getPurchRtrnDays() {
		return this.purchRtrnDays;
	}

	public void setPurchRtrnDays(final BigDecimal purchRtrnDays) {
		this.purchRtrnDays = purchRtrnDays;
	}

	public String getSppuCur() {
		return this.sppuCur;
	}

	public void setSppuCur(final String sppuCur) {
		this.sppuCur = sppuCur;
	}

	public BigDecimal getMinSppuHT() {
		return this.minSppuHT;
	}

	public void setMinSppuHT(final BigDecimal minSppuHT) {
		this.minSppuHT = minSppuHT;
	}

	public BigDecimal getVatSalesPct() {
		return this.vatSalesPct;
	}

	public void setVatSalesPct(final BigDecimal vatSalesPct) {
		this.vatSalesPct = vatSalesPct;
	}

	public BigDecimal getSalesWrntyDys() {
		return this.salesWrntyDys;
	}

	public void setSalesWrntyDys(final BigDecimal salesWrntyDys) {
		this.salesWrntyDys = salesWrntyDys;
	}

	public BigDecimal getSalesRtrnDays() {
		return this.salesRtrnDays;
	}

	public void setSalesRtrnDays(final BigDecimal salesRtrnDays) {
		this.salesRtrnDays = salesRtrnDays;
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
	
	public void copyTo(PrcmtAbstractDlvryItem target){
		target.dlvryNbr=dlvryNbr;
		target.poNbr=poNbr;
		target.lotPic=lotPic;
		target.artPic=artPic;
		target.supplier=supplier;
		target.supplierPic=supplierPic;
		target.expirDt=expirDt;
		target.qtyOrdered=qtyOrdered;
		target.qtyDlvrd=qtyDlvrd;
		target.freeQty=freeQty;
		target.stkQtyPreDlvry=stkQtyPreDlvry;
		target.pppuPreTax=pppuPreTax;
		target.pppuCur=pppuCur;
		target.grossPPPreTax=grossPPPreTax;
		target.rebate=rebate;
		target.netPPPreTax=netPPPreTax;
		target.vatPct=vatPct;
		target.vatAmount=vatAmount;
		target.netPPTaxIncl=netPPPreTax;
		target.sppuPreTax=sppuPreTax;
		target.purchWrntyDys=purchWrntyDys;
		target.purchRtrnDays=purchRtrnDays;
		target.sppuCur=sppuCur;
		target.minSppuHT=minSppuHT;
		target.vatSalesPct=vatSalesPct;
		target.salesWrntyDys=salesWrntyDys;
		target.salesRtrnDays=salesRtrnDays;
		target.creatingUsr=creatingUsr;
		target.creationDt=creationDt;	
	}
		
}