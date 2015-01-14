package org.adorsys.adbnsptnr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("Insurrance_description")
public class BpInsurrance extends AbstractTimedData {

	private static final long serialVersionUID = 3928927183324719436L;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Insurrance_beginDate_description")
	@NotNull(message = "Insurrance_beginDate_NotNull_validation")
	private Date beginDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("Insurrance_endDate_description")
	private Date endDate;

	@Column
	@Description("Insurrance_cstmrNbr_description")
	@NotNull
	private String cstmrNbr;

	@Column
	@Description("Insurrance_insurerNbr_description")
	@NotNull
	private String insurerNbr;

	@Column
	@Description("Insurrance_coverRatePct_description")
	@NotNull
	private BigDecimal coverRatePct;

	@Column
	@Description("Insurrance_memberNbr_description")
	private String memberNbr;

	public Date getBeginDate() {
		return this.beginDate;
	}

	public void setBeginDate(final Date beginDate) {
		this.beginDate = beginDate;
	}

	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public String getCstmrNbr() {
		return this.cstmrNbr;
	}

	public void setCstmrNbr(final String cstmrNbr) {
		this.cstmrNbr = cstmrNbr;
	}

	public String getInsurerNbr() {
		return this.insurerNbr;
	}

	public void setInsurerNbr(final String insurerNbr) {
		this.insurerNbr = insurerNbr;
	}

	public BigDecimal getCoverRatePct() {
		return this.coverRatePct;
	}

	public void setCoverRatePct(final BigDecimal coverRatePct) {
		this.coverRatePct = coverRatePct;
	}

	public String getMemberNbr() {
		return this.memberNbr;
	}

	public void setMemberNbr(final String memberNbr) {
		this.memberNbr = memberNbr;
	}

	@Override
	protected String makeIdentif() {
		return cstmrNbr + "_" + insurerNbr;
	}
}