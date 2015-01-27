package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpCtgryDscnt_description")
public class BpCtgryDscnt extends AbstractTimedData {

	private static final long serialVersionUID = -1745434871112146796L;

	@Column
	@Description("BpCtgryDscnt_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpCtgryDscnt_dscntAuthrzd_description")
	private Boolean dscntAuthrzd;

	@Column
	@Description("BpCtgryDscnt_maxDscntRatePct_description")
	private BigDecimal maxDscntRatePct;

	public String getCtgryCode() {
		return this.ctgryCode;
	}

	public void setCtgryCode(final String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}

	public Boolean getDscntAuthrzd() {
		return this.dscntAuthrzd;
	}

	public void setDscntAuthrzd(final Boolean dscntAuthrzd) {
		this.dscntAuthrzd = dscntAuthrzd;
	}

	public BigDecimal getMaxDscntRatePct() {
		return this.maxDscntRatePct;
	}

	public void setMaxDscntRatePct(final BigDecimal maxDscntRatePct) {
		this.maxDscntRatePct = maxDscntRatePct;
	}

	@Override
	protected String makeIdentif() {
		return ctgryCode;
	}
}