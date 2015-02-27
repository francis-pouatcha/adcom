package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@MappedSuperclass
@Description("InvInvtry_description")
public abstract class InvAbstractInvtry extends AbstractIdentifData {

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
	@Enumerated(EnumType.STRING)
	@NotNull
	private InvInvtryStatus invtryStatus;
	
	@Column
	@Description("InvInvtry_invInvtryType_description")
	@Enumerated(EnumType.STRING)
	@NotNull
	private InvInvtryType invInvtryType;

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

	public InvInvtryType getInvInvtryType() {
		return invInvtryType;
	}

	public void setInvInvtryType(InvInvtryType invInvtryType) {
		this.invInvtryType = invInvtryType;
	}

	@Override
	protected String makeIdentif() {
		return invtryNbr;
	}
	
	public void copyTo(InvAbstractInvtry target) {
		target.invtryNbr = invtryNbr;
		target.acsngUser = acsngUser;
		target.acsngDt = acsngDt;
		target.gapSaleAmtHT = gapSaleAmtHT;
		target.gapPurchAmtHT = gapPurchAmtHT;
		target.invtryStatus = invtryStatus;
		target.invInvtryType = invInvtryType;
		target.descptn = descptn;
		target.invtryDt = invtryDt;
	}

	public void clearAmts() {
		this.gapSaleAmtHT=BigDecimal.ZERO;
		this.gapPurchAmtHT = BigDecimal.ZERO;
	}

	public void addGapSaleAmtHT(BigDecimal gapSaleAmtHT) {
		if(this.gapSaleAmtHT==null)this.gapSaleAmtHT=BigDecimal.ZERO;
		this.gapSaleAmtHT = this.gapSaleAmtHT.add(gapSaleAmtHT);
	}

	public void addGapPurchAmtHT(BigDecimal gapPurchAmtHT) {
		if(this.gapPurchAmtHT==null)this.gapPurchAmtHT=BigDecimal.ZERO;
		this.gapPurchAmtHT = this.gapPurchAmtHT.add(gapPurchAmtHT);
	}
}
