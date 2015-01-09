package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalPkgMode_description")
public class CatalPkgMode extends AbstractTimedData {

	private static final long serialVersionUID = -1845587048039828959L;

	@Column
	@Description("CatalPkgMode_pkgCode_description")
	@NotNull(message = "CatalPkgMode_pkgCode_NotNull_validation")
	private String pkgCode;

	@Column
	@Description("CatalPkgMode_name_description")
	@NotNull(message = "CatalPkgMode_name_NotNull_validation")
	private String name;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPkgCode() {
		return pkgCode;
	}

	public void setPkgCode(String pkgCode) {
		this.pkgCode = pkgCode;
	}

	@Override
	protected String makeIdentif() {
		return pkgCode;
	}

}