package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("StkArticleLot_description")
public abstract class StkAbstractArticleLot extends AbstractIdentifData {

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

	@Column
	@Description("StkArticleLot_purchRtrnDays_description")
	private BigDecimal purchRtrnDays;
	
	// Date of the closing of this article lot. This is generally done
	// after an inventory where we can state that there is none
	// of the articles of this lot remaining in stock.
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArticleLot_closedDt_description")
	private Date closedDt;
	
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
}