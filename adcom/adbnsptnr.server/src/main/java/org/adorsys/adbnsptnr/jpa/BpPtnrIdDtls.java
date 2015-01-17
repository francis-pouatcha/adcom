package org.adorsys.adbnsptnr.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpPtnrIdDtls_description")
public class BpPtnrIdDtls extends AbstractTimedData {

	private static final long serialVersionUID = -1455712464712681064L;

	@Column
	@Description("BpPtnrIdDtls_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpPtnrIdDtls_ptnrIdType_description")
	@Enumerated(EnumType.ORDINAL)
	private BpPtnrIdType ptnrIdType;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpPtnrIdDtls_issuedDt_description")
	private Date issuedDt;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("BpPtnrIdDtls_expirdDt_description")
	private Date expirdDt;

	@Column
	@Description("BpPtnrIdDtls_issuedBy_description")
	private String issuedBy;

	@Column
	@Description("BpPtnrIdDtls_issuedIn_description")
	private String issuedIn;

	@Column
	@Description("BpPtnrIdDtls_issuingCtry_description")
	private String issuingCtry;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public BpPtnrIdType getPtnrIdType() {
		return this.ptnrIdType;
	}

	public void setPtnrIdType(final BpPtnrIdType ptnrIdType) {
		this.ptnrIdType = ptnrIdType;
	}

	public Date getIssuedDt() {
		return this.issuedDt;
	}

	public void setIssuedDt(final Date issuedDt) {
		this.issuedDt = issuedDt;
	}

	public Date getExpirdDt() {
		return this.expirdDt;
	}

	public void setExpirdDt(final Date expirdDt) {
		this.expirdDt = expirdDt;
	}

	public String getIssuedBy() {
		return this.issuedBy;
	}

	public void setIssuedBy(final String issuedBy) {
		this.issuedBy = issuedBy;
	}

	public String getIssuedIn() {
		return this.issuedIn;
	}

	public void setIssuedIn(final String issuedIn) {
		this.issuedIn = issuedIn;
	}

	public String getIssuingCtry() {
		return this.issuingCtry;
	}

	public void setIssuingCtry(final String issuingCtry) {
		this.issuingCtry = issuingCtry;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr;
	}
}