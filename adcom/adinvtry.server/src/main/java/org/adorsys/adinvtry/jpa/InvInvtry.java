package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("InvInvtry_description")
public class InvInvtry extends AbstractIdentifData {

	private static final long serialVersionUID = -7418836442555482998L;

	@Column(unique = true)
	@Description("InvInvtry_invtryNbr_description")
	@NotNull
	private String invtryNbr;

	@Column
	@Description("InvInvtry_acsngUser_description")
	@NotNull
	private String acsngUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	@NotNull
	private Date acsngDt;

	@Column
	@Description("InvInvtry_gapSaleAmtHT_description")
	private BigDecimal gapSaleAmtHT;

	@Column
	@Description("InvInvtry_gapPurchAmtHT_description")
	private BigDecimal gapPurchAmtHT;

	@Column
	@Description("InvInvtry_invtryStatus_description")
	@Enumerated(EnumType.ORDINAL)
	@NotNull
	private InvInvtryStatus invtryStatus;

	@Column
	@Description("InvInvtry_descptn_description")
	@Size(max = 256, message = "InvInvtry_descptn_Size_validation")
	private String descptn;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtry_invtryDt_description")
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

	public InvInvtryStatus getInvtryStatus() {
		return invtryStatus;
	}

	public void setInvtryStatus(InvInvtryStatus invtryStatus) {
		this.invtryStatus = invtryStatus;
	}

	@Override
	protected String makeIdentif() {
		return invtryNbr;
	}

}