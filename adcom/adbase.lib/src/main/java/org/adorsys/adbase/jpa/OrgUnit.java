package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("OrgUnit_description")
public class OrgUnit extends AbstractTimedData {

	private static final long serialVersionUID = -4042784869338352973L;

	@Column
	@Description("OrgUnit_ctryIso3_description")
	@NotNull
	private String ctryIso3;

	@Column
	@Description("OrgUnit_typeIdentif_description")
	@NotNull
	private String typeIdentif;

	@Column
	@Description("OrgUnit_fullName_description")
	@NotNull
	private String fullName;

	@Column
	@Description("OrgUnit_shortName_description")
	@NotNull
	private String shortName;

	public String getCtryIso3() {
		return this.ctryIso3;
	}

	public void setCtryIso3(final String ctryIso3) {
		this.ctryIso3 = ctryIso3;
	}

	public String getTypeIdentif() {
		return this.typeIdentif;
	}

	public void setTypeIdentif(final String typeIdentif) {
		this.typeIdentif = typeIdentif;
	}

	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(final String shortName) {
		this.shortName = shortName;
	}

	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(getIdentif()))
			throw new IllegalStateException("Identifier must be set explicitely.");
		return getIdentif();
	}
}