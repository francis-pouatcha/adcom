package org.adorsys.adcatal.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

/**
 * Product Details Config
 * 
 * @author francis
 *
 */
@Entity
@Description("CatalArtDetailConfig_description")
public class CatalArtDetailConfig extends AbstractTimedData {

	private static final long serialVersionUID = -2136313145008332965L;

	@Column
	@Description("CatalArtDetailConfig_artEquivCode_description")
	@NotNull
	private String artDetCode;

	@Column
	@Description("CatalArtDetailConfig_sourceIdentif_description")
	@NotNull
	private String sourceIdentif;

	@Column
	@Description("CatalArtDetailConfig_targetIdentif_description")
	@NotNull
	private String targetIdentif;

	@Column
	@Description("CatalArtDetailConfig_targetQty_description")
	@NotNull
	private BigDecimal targetQty;

	@Column
	@Description("CatalArtDetailConfig_salesPrice_description")
	@NotNull
	private BigDecimal salesPrice;

	@Column
	@Description("CatalArtDetailConfig_active_description")
	private Boolean active;

	public String getArtDetCode() {
		return artDetCode;
	}

	public void setArtDetCode(String artDetCode) {
		this.artDetCode = artDetCode;
	}

	public String getSourceIdentif() {
		return this.sourceIdentif;
	}

	public void setSourceIdentif(final String sourceIdentif) {
		this.sourceIdentif = sourceIdentif;
	}

	public String getTargetIdentif() {
		return this.targetIdentif;
	}

	public void setTargetIdentif(final String targetIdentif) {
		this.targetIdentif = targetIdentif;
	}

	public BigDecimal getTargetQty() {
		return this.targetQty;
	}

	public void setTargetQty(final BigDecimal targetQty) {
		this.targetQty = targetQty;
	}

	public BigDecimal getSalesPrice() {
		return this.salesPrice;
	}

	public void setSalesPrice(final BigDecimal salesPrice) {
		this.salesPrice = salesPrice;
	}

	public Boolean getActive() {
		return this.active;
	}

	public void setActive(final Boolean active) {
		this.active = active;
	}

	@Override
	protected String makeIdentif() {
		return artDetCode;
	}

}