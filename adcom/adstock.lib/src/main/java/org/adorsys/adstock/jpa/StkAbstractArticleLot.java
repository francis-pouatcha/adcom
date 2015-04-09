package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.time.DateUtils;

@MappedSuperclass
@Description("StkArticleLot_description")
public class StkAbstractArticleLot extends AbstractIdentifData {

	private static final long serialVersionUID = 6790628013825127916L;

	@Column
	@Description("StkArticleLot_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkArticleLot_artPic_description")
	@NotNull
	private String artPic;
	
	@Column
	@Description("StkArticleLot_supplierPic_description")
	private String supplierPic;

	@Column
	@Description("StkArticleLot_supplier_description")
	private String supplier;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_stkgDt_description")
	@NotNull
	private Date stkgDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_expirDt_description")
	private Date expirDt;

	@Column
	@Description("StkArticleLot_sppuHT_description")
	private BigDecimal sppuHT;

	@Column
	@Description("StkArticleLot_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("StkArticleLot_minSppuHT_description")
	private BigDecimal minSppuHT;

	@Column
	@Description("StkArticleLot_vatSalesPct_description")
	private BigDecimal vatSalesPct;

	@Column
	@Description("StkArticleLot_salesWrntyDys_description")
	private BigDecimal salesWrntyDys;

	@Column
	@Description("StkArticleLot_salesRtrnDays_description")
	private BigDecimal salesRtrnDays;

	@Column
	@Description("StkArticleLot_pppuHT_description")
	private BigDecimal pppuHT;

	@Column
	@Description("StkArticleLot_pppuCur_description")
	private String pppuCur;

	@Column
	@Description("StkArticleLot_vatPurchPct_description")
	private BigDecimal vatPurchPct;

	@Column
	@Description("StkArticleLot_purchWrntyDys_description")
	private BigDecimal purchWrntyDys;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_purchWrntyDt_description")
	private Date purchWrntyDt;

	@Column
	@Description("StkArticleLot_purchRtrnDays_description")
	private BigDecimal purchRtrnDays;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_purchRtrnDt_description")
	private Date purchRtrnDt;
	
	// Date of the closing of this article lot. This is generally done
	// after an inventory where we can state that there is none
	// of the articles of this lot remaining in stock.
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_closedDt_description")
	private Date closedDt;
	

	@Transient
	private List<StkArticleLot2StrgSctn> strgSctns = new ArrayList<StkArticleLot2StrgSctn>();
		
	@Transient
	private BigDecimal sppuTaxIncl;

	@Transient
	private BigDecimal salesVatAmt;
	
	/*
	 * The quantities in each lot of this article.
	 */
	@Transient
	private List<StkLotStockQty> artQties = new ArrayList<StkLotStockQty>();
	
	@Transient
	private BigDecimal lotQty;
	
	@Transient
	private Date lotQtyDt;
	
	/*
	 * The name of this article in the language of the user.
	 */
	@Transient
	private CatalArtFeatMapping artFeatures;
	
	
	@PrePersist
	public void prePersist() {
		super.prePersist();
		if(stkgDt!=null && purchWrntyDys!=null)
			purchWrntyDt = DateUtils.addDays(stkgDt, purchRtrnDays.intValue());

		if(stkgDt!=null && purchRtrnDays!=null)
			purchRtrnDt = DateUtils.addDays(stkgDt, purchRtrnDays.intValue());
	}

	@PreUpdate
	public void preUpdate() {
		if(stkgDt!=null && purchWrntyDys!=null)
			purchWrntyDt = DateUtils.addDays(stkgDt, purchRtrnDays.intValue());

		if(stkgDt!=null && purchRtrnDays!=null)
			purchRtrnDt = DateUtils.addDays(stkgDt, purchRtrnDays.intValue());
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

	public String getSupplierPic() {
		return this.supplierPic;
	}

	public void setSupplierPic(final String supplierPic) {
		this.supplierPic = supplierPic;
	}

	public String getSupplier() {
		return this.supplier;
	}

	public void setSupplier(final String supplier) {
		this.supplier = supplier;
	}
	
	public Date getStkgDt() {
		return this.stkgDt;
	}

	public void setStkgDt(final Date stkgDt) {
		this.stkgDt = stkgDt;
	}

	public Date getExpirDt() {
		return this.expirDt;
	}

	public void setExpirDt(final Date expirDt) {
		this.expirDt = expirDt;
	}

	public BigDecimal getSppuHT() {
		return this.sppuHT;
	}

	public void setSppuHT(final BigDecimal sppuHT) {
		this.sppuHT = sppuHT;
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

	public BigDecimal getPppuHT() {
		return this.pppuHT;
	}

	public void setPppuHT(final BigDecimal pppuHT) {
		this.pppuHT = pppuHT;
	}

	public String getPppuCur() {
		return this.pppuCur;
	}

	public void setPppuCur(final String pppuCur) {
		this.pppuCur = pppuCur;
	}

	public BigDecimal getVatPurchPct() {
		return this.vatPurchPct;
	}

	public void setVatPurchPct(final BigDecimal vatPurchPct) {
		this.vatPurchPct = vatPurchPct;
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

	public Date getPurchWrntyDt() {
		return purchWrntyDt;
	}

	public void setPurchWrntyDt(Date purchWrntyDt) {
		this.purchWrntyDt = purchWrntyDt;
	}

	public Date getPurchRtrnDt() {
		return purchRtrnDt;
	}

	public void setPurchRtrnDt(Date purchRtrnDt) {
		this.purchRtrnDt = purchRtrnDt;
	}

	@Override
	protected String makeIdentif() {
		return toId(lotPic);
	}
	
	public static String toId(String lotPic){
		return lotPic;
	}

	public Date getClosedDt() {
		return closedDt;
	}

	public void setClosedDt(Date closedDt) {
		this.closedDt = closedDt;
	}

	public List<StkArticleLot2StrgSctn> getStrgSctns() {
		return strgSctns;
	}

	public void setStrgSctns(List<StkArticleLot2StrgSctn> strgSctns) {
		this.strgSctns = strgSctns;
	}

	public BigDecimal getSppuTaxIncl() {
		return sppuTaxIncl;
	}

	public void setSppuTaxIncl(BigDecimal sppuTaxIncl) {
		this.sppuTaxIncl = sppuTaxIncl;
	}

	public List<StkLotStockQty> getArtQties() {
		return artQties;
	}

	public void setArtQties(List<StkLotStockQty> artQties) {
		this.artQties = artQties;
	}

	public BigDecimal getSalesVatAmt() {
		return salesVatAmt;
	}

	public void setSalesVatAmt(BigDecimal salesVatAmt) {
		this.salesVatAmt = salesVatAmt;
	}

	public CatalArtFeatMapping getArtFeatures() {
		return artFeatures;
	}

	public void setArtFeatures(CatalArtFeatMapping artFeatures) {
		this.artFeatures = artFeatures;
	}

	public BigDecimal getLotQty() {
		return lotQty;
	}

	public void setLotQty(BigDecimal lotQty) {
		this.lotQty = lotQty;
	}

	public Date getLotQtyDt() {
		return lotQtyDt;
	}

	public void setLotQtyDt(Date lotQtyDt) {
		this.lotQtyDt = lotQtyDt;
	}

}