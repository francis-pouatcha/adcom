package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrPymntItem_description")
public class CdrPymntItem extends AbstractIdentifData {

	private static final long serialVersionUID = -7025227533952645459L;

	@Column
	@Description("CdrPymntItem_pymntNbr_description")
	@NotNull
	private String pymntNbr;

	@Column
	@Description("CdrPymntItem_pymntMode_description")
	@Enumerated
	@NotNull
	private CdrPymntMode pymntMode;

	@Column
	@Description("CdrPymntItem_pymntDocType_description")
	private String pymntDocType;

	@Column
	@Description("CdrPymntItem_pymntDocNbr_description")
	private String pymntDocNbr;

	@Column
	@Description("CdrPymntItem_amt_description")
	@NotNull
	private BigDecimal amt;

	@Column
	@Description("CdrPymntItem_rcvdAmt_description")
	private BigDecimal rcvdAmt;

	@Column
	@Description("CdrPymntItem_diffAmt_description")
	private BigDecimal diffAmt;

	public String getPymntNbr() {
		return this.pymntNbr;
	}

	public void setPymntNbr(final String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}

	public CdrPymntMode getPymntMode() {
		return this.pymntMode;
	}

	public void setPymntMode(final CdrPymntMode pymntMode) {
		this.pymntMode = pymntMode;
	}

	public String getPymntDocType() {
		return this.pymntDocType;
	}

	public void setPymntDocType(final String pymntDocType) {
		this.pymntDocType = pymntDocType;
	}

	public String getPymntDocNbr() {
		return this.pymntDocNbr;
	}

	public void setPymntDocNbr(final String pymntDocNbr) {
		this.pymntDocNbr = pymntDocNbr;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public BigDecimal getRcvdAmt() {
		return this.rcvdAmt;
	}

	public void setRcvdAmt(final BigDecimal rcvdAmt) {
		this.rcvdAmt = rcvdAmt;
	}

	public BigDecimal getDiffAmt() {
		return this.diffAmt;
	}

	public void setDiffAmt(final BigDecimal diffAmt) {
		this.diffAmt = diffAmt;
	}

	@Override
	protected String makeIdentif() {
		return pymntNbr + "_ " + pymntMode.name() + "_" + pymntDocType + "_" + pymntDocNbr;
	}
}