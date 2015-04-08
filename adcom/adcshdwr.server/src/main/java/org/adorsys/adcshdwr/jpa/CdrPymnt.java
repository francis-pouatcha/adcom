package org.adorsys.adcshdwr.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrPymnt_description")
public class CdrPymnt extends AbstractIdentifData {

	private static final long serialVersionUID = -8696070131718102911L;

	@Column
	@Description("CdrPymnt_pymntNbr_description")
	@NotNull
	private String pymntNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrPymnt_pymntDt_description")
	@NotNull
	private Date pymntDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrPymnt_valueDt_description")
	@NotNull
	private Date valueDt;

	@Column
	@Description("CdrPymnt_amt_description")
	private BigDecimal amt;

	@Column
	@Description("CdrPymnt_cashier_description")
	@NotNull
	private String cashier;

	@Column
	@Description("CdrPymnt_cdrNbr_description")
	private String cdrNbr;

	@Column
	@Description("CdrPymnt_paidBy_description")
	@NotNull
	private String paidBy;

	@Column
	@Description("CdrPymnt_rcptNbr_description")
	@NotNull
	private String rcptNbr;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("CdrPymnt_rcptPrntDt_description")
	private Date rcptPrntDt;

	public String getPymntNbr() {
		return this.pymntNbr;
	}

	public void setPymntNbr(final String pymntNbr) {
		this.pymntNbr = pymntNbr;
	}

	public Date getPymntDt() {
		return this.pymntDt;
	}

	public void setPymntDt(final Date pymntDt) {
		this.pymntDt = pymntDt;
	}

	public Date getValueDt() {
		return this.valueDt;
	}

	public void setValueDt(final Date valueDt) {
		this.valueDt = valueDt;
	}

	public BigDecimal getAmt() {
		return this.amt;
	}

	public void setAmt(final BigDecimal amt) {
		this.amt = amt;
	}

	public String getCashier() {
		return this.cashier;
	}

	public void setCashier(final String cashier) {
		this.cashier = cashier;
	}

	public String getCdrNbr() {
		return this.cdrNbr;
	}

	public void setCdrNbr(final String cdrNbr) {
		this.cdrNbr = cdrNbr;
	}

	public String getPaidBy() {
		return this.paidBy;
	}

	public void setPaidBy(final String paidBy) {
		this.paidBy = paidBy;
	}

	public String getRcptNbr() {
		return this.rcptNbr;
	}

	public void setRcptNbr(final String rcptNbr) {
		this.rcptNbr = rcptNbr;
	}

	public Date getRcptPrntDt() {
		return this.rcptPrntDt;
	}

	public void setRcptPrntDt(final Date rcptPrntDt) {
		this.rcptPrntDt = rcptPrntDt;
	}

	@Override
	protected String makeIdentif() {
		return pymntNbr;
	}

	/**
	 * addAmnt.
	 *
	 * @param amt2
	 */
	public void addAmnt(BigDecimal amt2) {
		if(amt2 == null) amt2 = BigDecimal.ZERO;
		if(amt == null) amt = BigDecimal.ZERO;
		this.amt = amt.add(amt2);
	}

	/**
	 * clearAmts.
	 *
	 */
	public void clearAmts() {
		if(amt == null) amt = BigDecimal.ZERO;
	}
}