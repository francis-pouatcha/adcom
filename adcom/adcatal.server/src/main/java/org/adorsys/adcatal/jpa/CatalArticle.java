package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalArticle_description")
public class CatalArticle extends AbstractTimedData {

	private static final long serialVersionUID = -1863080422505047660L;

	@Column
	@Description("CatalArticle_articleName_description")
	@NotNull
	private String articleName;

	@Column
	@Description("CatalArticle_pic_description")
	private String pic;

	@Column
	@Description("CatalArticle_active_description")
	private Boolean active;

	@Column
	@Description("CatalArticle_authorizedSale_description")
	private Boolean authorizedSale;

	@Column
	@Description("CatalArticle_familiesIdentif_description")
	private String familiesIdentif;

	@Column
	@Description("CatalArticle_sppu_description")
	private BigDecimal sppu;

	@Column
	@Description("CatalArticle_sppuCurrIso3_description")
	private String sppuCurrIso3;
	
	@Column
	@Description("CatalArticle_maxDisctRate_description")
	private BigDecimal maxDisctRate;

	@Column
	@Description("CatalArticle_maxStockQty_description")
	private BigDecimal maxStockQty;

	@Column
	@Description("CatalArticle_minStockQty_description")
	private BigDecimal minStockQty;
	
	@Column
	@Description("CatalArticle_vatRate_description")
	private BigDecimal vatRate;

	public String getArticleName() {
		return this.articleName;
	}

	public void setArticleName(final String articleName) {
		this.articleName = articleName;
	}

	public String getPic() {
		return this.pic;
	}

	public void setPic(final String pic) {
		this.pic = pic;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	public Boolean getAuthorizedSale() {
		return this.authorizedSale;
	}

	public void setAuthorizedSale(final Boolean authorizedSale) {
		this.authorizedSale = authorizedSale;
	}

	public String getFamiliesIdentif() {
		return this.familiesIdentif;
	}

	public void setFamiliesIdentif(final String familiesIdentif) {
		this.familiesIdentif = familiesIdentif;
	}

	public BigDecimal getSppu() {
		return this.sppu;
	}

	public void setSppu(final BigDecimal sppu) {
		this.sppu = sppu;
	}

	public String getSppuCurrIso3() {
		return this.sppuCurrIso3;
	}

	public void setSppuCurrIso3(final String sppuCurrIso3) {
		this.sppuCurrIso3 = sppuCurrIso3;
	}

	public BigDecimal getVatRate() {
		return this.vatRate;
	}

	public void setVatRate(final BigDecimal vatRate) {
		this.vatRate = vatRate;
	}

	@Override
	protected String makeIdentif() {
		return pic;
	}
}