package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.javaext.description.Description;

@Entity
@Description("StkArticleLot_description")
public class StkArticleLot extends StkAbstractArticleLot {
	private static final long serialVersionUID = -4912132065652417884L;

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