package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpPtnrCtgry_description")
public class BpPtnrCtgry extends AbstractTimedData {

	private static final long serialVersionUID = -5239368389118117504L;

	@Column
	@Description("BpPtnrCtgry_ctgryCode_description")
	@NotNull
	private String ctgryCode;

	@Column
	@Description("BpPtnrCtgry_parentCode_description")
	private String parentCode;

	@Column
	@Description("BpPtnrCtgry_ptnrRole_description")
	@Enumerated
	private BpPtnrRole ptnrRole;

	public String getCtgryCode() {
		return this.ctgryCode;
	}

	public void setCtgryCode(final String ctgryCode) {
		this.ctgryCode = ctgryCode;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(final String parentCode) {
		this.parentCode = parentCode;
	}

	public BpPtnrRole getPtnrRole() {
		return this.ptnrRole;
	}

	public void setPtnrRole(final BpPtnrRole ptnrRole) {
		this.ptnrRole = ptnrRole;
	}

	@Override
	protected String makeIdentif() {
		return ctgryCode;
	}
}