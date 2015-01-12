package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("StkInvtry_description")
public class StkInvtry extends AbstractIdentifData {

	private static final long serialVersionUID = -7874651724074117754L;

	@Column
	@Description("StkInvtry_invtryNbr_description")
	@NotNull
	private String invtryNbr;

	@Column
	@Description("StkInvtry_acsngUser_description")
	@NotNull
	private String acsngUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkInvtry_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date acsngDt;

	@Column
	@Description("StkInvtry_gapSaleAmtHT_description")
	private BigDecimal gapSaleAmtHT;

	@Column
	@Description("StkInvtry_gapPurchAmtHT_description")
	private BigDecimal gapPurchAmtHT;

	@Column
	@Description("StkInvtry_invtryStatus_description")
	@Enumerated
	@NotNull
	private StkInvtryStatus invtryStatus;

	@Column
	@Description("StkInvtry_descptn_description")
	@Size(max = 256, message = "StkInvtry_descptn_Size_validation")
	private String descptn;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkInvtry_invtryDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date invtryDt;

	public String getInvtryNbr() {
		return this.invtryNbr;
	}

	public void setInvtryNbr(final String invtryNbr) {
		this.invtryNbr = invtryNbr;
	}

	public String getAcsngUser() {
		return this.acsngUser;
	}

	public void setAcsngUser(final String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public Date getAcsngDt() {
		return this.acsngDt;
	}

	public void setAcsngDt(final Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	public BigDecimal getGapSaleAmtHT() {
		return this.gapSaleAmtHT;
	}

	public void setGapSaleAmtHT(final BigDecimal gapSaleAmtHT) {
		this.gapSaleAmtHT = gapSaleAmtHT;
	}

	public BigDecimal getGapPurchAmtHT() {
		return this.gapPurchAmtHT;
	}

	public void setGapPurchAmtHT(final BigDecimal gapPurchAmtHT) {
		this.gapPurchAmtHT = gapPurchAmtHT;
	}

	public StkInvtryStatus getInvtryStatus() {
		return this.invtryStatus;
	}

	public void setInvtryStatus(final StkInvtryStatus invtryStatus) {
		this.invtryStatus = invtryStatus;
	}

	public String getDescptn() {
		return this.descptn;
	}

	public void setDescptn(final String descptn) {
		this.descptn = descptn;
	}

	public Date getInvtryDt() {
		return this.invtryDt;
	}

	public void setInvtryDt(final Date invtryDt) {
		this.invtryDt = invtryDt;
	}

	@Override
	protected String makeIdentif() {
		return invtryNbr;
	}
}