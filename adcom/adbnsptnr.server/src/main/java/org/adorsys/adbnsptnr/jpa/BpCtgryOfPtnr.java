package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpCtgryOfPtnr_description")
public class BpCtgryOfPtnr extends AbstractTimedData {

	private static final long serialVersionUID = -2320937065216321253L;

	@Column
	@Description("BpCtgryOfPtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpCtgryOfPtnr_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpCtgryOfPtnr_whenInRole_description")
	@Enumerated(EnumType.ORDINAL)
	private BpPtnrRole whenInRole;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getCtgryCode() {
		return this.ctgryCode;
	}

	public void setCtgryCode(final String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}

	public BpPtnrRole getWhenInRole() {
		return this.whenInRole;
	}

	public void setWhenInRole(final BpPtnrRole whenInRole) {
		this.whenInRole = whenInRole;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr +"_"+ctgryCode;
	}
}