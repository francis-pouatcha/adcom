package org.adorsys.adprocmt.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.jpa.AmtOrPct;
import org.adorsys.adcore.jpa.CurrencyEnum;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.adcore.utils.FinancialOps;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("PrcmtDlvryItem_description")
public abstract class PrcmtAbstractDlvryItem extends AbstractIdentifData {

	private static final long serialVersionUID = 8446377222696584731L;

	@Column
	@Description("PrcmtDlvryItem_dlvryItemNbr_description")
	@NotNull
	private String dlvryItemNbr;
	
	@Column
	@Description("PrcmtDlvryItem_dlvryNbr_description")
	@NotNull
	private String dlvryNbr;

	/*
	 * The lot identification code. Many delivery items can have the 
	 * same lot identification code, but different storage locations.
	 */
	@Column
	@Description("PrcmtDlvryItem_lotPic_description")
	private String lotPic;

	/*
	 * The article identification code. This is the most important information in a delivery item.
	 * This must be present for each delivery item submitted. If not the delivery item is 
	 * rejected.
	 * 
	 * This is used to identify the article and if possible fill in missing informations like on
	 * sales pricing.
	 */
	@Column
	@Description("PrcmtDlvryItem_artPic_description")
	@NotNull
	private String artPic;
	
	/*
	 * this field is use to carry article name
	 */
	@Column
	@Description("PrcmtDlvryItem_artName_description")
	@Transient
	private String artName;

	/*
	 * The supplier of this item, if available.
	 */
	@Column
	@Description("PrcmtDlvryItem_supplier_description")
	private String supplier;

	/*
	 * The supplier specific identification code if available.
	 */
	@Column
	@Description("PrcmtDlvryItem_supplierPic_description")
	private String supplierPic;

	/*
	 * The expiration date of this lot. This is decisive for the splitting in lots.
	 * All articles in the same lot have the same expiration date.
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDlvryItem_expirDt_description")
	private Date expirDt;

	/*
	 * The quantity delivered. This can match the quantity ordered or not.
	 * In the normal case, qtyOrdered = qtyDlvrd - freeQty = qtyBilled
	 * 
	 * But in some cases the delivery might be erroneous.
	 * 
	 */
	@Column
	@Description("PrcmtDlvryItem_qtyDlvrd_description")
	@NotNull
	private BigDecimal qtyDlvrd;
	
	/*
	 * The free quantity. This might be a promotion of the supplier.
	 * 
	 * This information is generally not available in the procurement order.
	 */
	@Column
	@Description("PrcmtDlvryItem_freeQty_description")
	private BigDecimal freeQty;

	/*
	 * The quantity on the invoice. This might be less the the quantity delivered or
	 * the quantity expected to be delivered. In which case a claim process might 
	 * automatically be started by this application.
	 */
	@Column
	@Description("PrcmtDlvryItem_qtyBilled_description")
	//@NotNull
	private BigDecimal qtyBilled;
	
	@Column
	@Description("PrcmtDlvryItem_pppuPreTax_description")
	private BigDecimal pppuPreTax;

	@Column
	@Description("PrcmtDlvryItem_pppuCur_description")
	@NotNull
	private String pppuCur = CurrencyEnum.XAF.name();

	@Column
	@Description("PrcmtDlvryItem_grossPPPreTax_description")
	private BigDecimal grossPPPreTax;
	
	@Column
	@Description("PrcmtDlvryItem_rebateType_description")
	//@NotNull
	@Enumerated(EnumType.STRING)
	private AmtOrPct rebateType;
	
	@Column
	@Description("PrcmtDlvryItem_rebateAmt_description")
	private BigDecimal rebateAmt;

	@Column
	@Description("PrcmtDlvryItem_rebatePct_description")
	private BigDecimal rebatePct;

	@Column
	@Description("PrcmtDlvryItem_netPPPreTax_description")
	private BigDecimal netPPPreTax;

	@Column
	@Description("PrcmtDlvryItem_vatPct_description")
	private BigDecimal vatPct;

	@Column
	@Description("PrcmtDlvryItem_vatAmt_description")
	private BigDecimal vatAmt;

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
	//@NotNull
	private String creatingUsr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("PrcmtDlvryItem_creationDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date creationDt;

	@PrePersist
	public void prePersist() {
		if(StringUtils.isBlank(dlvryItemNbr)){
			dlvryItemNbr = SequenceGenerator.getSequence(dlvryNbr);
		}
		setId(dlvryItemNbr);
	}

	public String getDlvryNbr() {
		return this.dlvryNbr;
	}

	public void setDlvryNbr(final String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
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

	public BigDecimal getQtyDlvrd() {
		return this.qtyDlvrd;
	}

	public void setQtyDlvrd(final BigDecimal qtyDlvrd) {
		this.qtyDlvrd = qtyDlvrd;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public BigDecimal getFreeQty() {
		return this.freeQty;
	}

	public void setFreeQty(final BigDecimal freeQty) {
		this.freeQty = freeQty;
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
	
	public BigDecimal getRebateAmt() {
		return rebateAmt;
	}

	public void setRebateAmt(BigDecimal rebateAmt) {
		this.rebateAmt = rebateAmt;
	}

	public BigDecimal getRebatePct() {
		return rebatePct;
	}

	public void setRebatePct(BigDecimal rebatePct) {
		this.rebatePct = rebatePct;
	}

	public BigDecimal getVatAmt() {
		return vatAmt;
	}

	public void setVatAmt(BigDecimal vatAmt) {
		this.vatAmt = vatAmt;
	}

	public AmtOrPct getRebateType() {
		return rebateType;
	}

	public void setRebateType(AmtOrPct rebateType) {
		this.rebateType = rebateType;
	}

	public String getDlvryItemNbr() {
		return dlvryItemNbr;
	}

	public void setDlvryItemNbr(String dlvryItemNbr) {
		this.dlvryItemNbr = dlvryItemNbr;
	}

	public BigDecimal getQtyBilled() {
		return qtyBilled;
	}

	public void setQtyBilled(BigDecimal qtyBilled) {
		this.qtyBilled = qtyBilled;
	}

	public void copyTo(PrcmtAbstractDlvryItem target){
		target.dlvryItemNbr=dlvryItemNbr;
		target.dlvryNbr=dlvryNbr;
		target.lotPic=lotPic;
		target.artPic=artPic;
		target.supplier=supplier;
		target.supplierPic=supplierPic;
		target.expirDt=expirDt;
		target.qtyDlvrd=qtyDlvrd;
		target.freeQty=freeQty;
		target.qtyBilled=qtyBilled;
		target.pppuPreTax=pppuPreTax;
		target.pppuCur=pppuCur;
		target.grossPPPreTax=grossPPPreTax;
		target.rebateType = rebateType;
		target.rebatePct=rebatePct;
		target.rebateAmt=rebateAmt;
		target.netPPPreTax=netPPPreTax;
		target.vatPct=vatPct;
		target.vatAmt=vatAmt;
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


	public boolean contentEquals(PrcmtAbstractDlvryItem target){
		if(!CalendarUtil.isSameDay(target.expirDt,expirDt)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyDlvrd,qtyDlvrd)) return false;
		if(!BigDecimalUtils.numericEquals(target.freeQty,freeQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.qtyBilled,qtyBilled)) return false;
		if(!BigDecimalUtils.numericEquals(target.pppuPreTax,pppuPreTax)) return false;
		if(!StringUtils.equals(target.pppuCur,pppuCur)) return false;
		if(!BigDecimalUtils.numericEquals(target.grossPPPreTax,grossPPPreTax)) return false;
		if(!ObjectUtils.equals(target.rebateType , rebateType)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebatePct,rebatePct)) return false;
		if(!BigDecimalUtils.numericEquals(target.rebateAmt,rebateAmt)) return false;
		if(!BigDecimalUtils.numericEquals(target.netPPPreTax,netPPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatPct,vatPct)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatAmt,vatAmt)) return false;
		if(!BigDecimalUtils.numericEquals(target.netPPTaxIncl,netPPPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.sppuPreTax,sppuPreTax)) return false;
		if(!BigDecimalUtils.numericEquals(target.purchWrntyDys,purchWrntyDys)) return false;
		if(!BigDecimalUtils.numericEquals(target.purchRtrnDays,purchRtrnDays)) return false;
		if(!StringUtils.equals(target.sppuCur,sppuCur)) return false;
		if(!BigDecimalUtils.numericEquals(target.minSppuHT,minSppuHT)) return false;
		if(!BigDecimalUtils.numericEquals(target.vatSalesPct,vatSalesPct)) return false;
		if(!BigDecimalUtils.numericEquals(target.salesWrntyDys,salesWrntyDys)) return false;
		if(!BigDecimalUtils.numericEquals(target.salesRtrnDays,salesRtrnDays)) return false;
		if(!StringUtils.equals(target.creatingUsr,creatingUsr)) return false;
		if(!CalendarUtil.isSameInstant(target.creationDt,creationDt)) return false;

		if(!StringUtils.equals(target.dlvryItemNbr,dlvryItemNbr)) return false;
		if(!StringUtils.equals(target.dlvryNbr,dlvryNbr)) return false;
		if(!StringUtils.equals(target.lotPic,lotPic)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;
		if(!StringUtils.equals(target.supplier,supplier)) return false;
		if(!StringUtils.equals(target.supplierPic,supplierPic)) return false;
		return true;
	}
	
	public void addQtyDlvrd(BigDecimal qtyDlvrd) {
		if(this.qtyDlvrd==null) this.qtyDlvrd=BigDecimal.ZERO;
		if(qtyDlvrd==null) qtyDlvrd=BigDecimal.ZERO;
		this.qtyDlvrd = this.qtyDlvrd.add(qtyDlvrd);
	}

	public void addFreeQty(BigDecimal freeQty) {
		if(this.freeQty==null) this.freeQty=BigDecimal.ZERO;
		if(freeQty==null) freeQty=BigDecimal.ZERO;
		this.freeQty = this.freeQty.add(freeQty);
	}

	public void addQtyBilled(BigDecimal qtyBilled) {
		if(this.qtyBilled==null) this.qtyBilled=BigDecimal.ZERO;
		if(qtyBilled==null) qtyBilled=BigDecimal.ZERO;
		this.qtyBilled = this.qtyBilled.add(qtyBilled);
	}

	public void addRebateAmt(BigDecimal rebateAmt) {
		if(this.rebateAmt==null) this.rebateAmt=BigDecimal.ZERO;
		if(rebateAmt==null) rebateAmt=BigDecimal.ZERO;
		this.rebateAmt = this.rebateAmt.add(rebateAmt);
	}
	
	public void evlte() {
		if(this.qtyDlvrd==null) this.qtyDlvrd=BigDecimal.ZERO;
		if(this.freeQty==null) this.freeQty=BigDecimal.ZERO;
		BigDecimal qtyPurchased = this.qtyBilled==null?this.qtyDlvrd.subtract(this.freeQty):this.qtyBilled;
		if(this.pppuPreTax==null) this.pppuPreTax=BigDecimal.ZERO;
		this.grossPPPreTax = qtyPurchased.multiply(this.pppuPreTax);
		if(this.rebatePct==null)this.rebatePct=BigDecimal.ZERO;
		if(this.rebateAmt==null)this.rebateAmt=BigDecimal.ZERO;
		if(this.grossPPPreTax.compareTo(BigDecimal.ZERO)<=0){
			this.rebateAmt = BigDecimal.ZERO;
			this.rebatePct = BigDecimal.ZERO;
		} else {
			if(AmtOrPct.PERCENT.equals(this.rebateType)){
				this.rebateAmt = FinancialOps.amtFromPrct(this.grossPPPreTax, this.rebatePct, this.pppuCur);
			} else {
				this.rebatePct = FinancialOps.prctFromAmt(this.grossPPPreTax, this.rebateAmt, this.pppuCur);
			}
		}
		this.netPPPreTax = FinancialOps.substract(this.grossPPPreTax, this.rebateAmt, this.pppuCur);

		if(this.vatPct==null)this.vatPct=BigDecimal.ZERO;
		if(this.vatAmt==null)this.vatAmt=BigDecimal.ZERO;
		if(this.netPPPreTax.compareTo(BigDecimal.ZERO)<=0){
			this.vatPct = BigDecimal.ZERO;
			this.vatAmt = BigDecimal.ZERO;
		} else {
			this.vatAmt = FinancialOps.amtFromPrct(this.netPPPreTax, this.vatPct, this.pppuCur);
		}
		this.netPPTaxIncl = FinancialOps.add(this.netPPPreTax, this.vatAmt, this.pppuCur);
	}

	@Override
	protected String makeIdentif() {
		return dlvryItemNbr;
	}
	
}