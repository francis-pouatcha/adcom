package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalFamilyFeatMaping_description")
public class CatalFamilyFeatMaping extends AbstractIdentifData {

	private static final long serialVersionUID = -2759458392635728552L;

	@Column
	@Description("CatalFamilyFeatMaping_pfIdentif_description")
	@NotNull
	private String pfIdentif;

	@Column
	@Description("CatalFamilyFeatMaping_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Description("CatalFamilyFeatMaping_familyName_description")
	private String familyName;

	@Column
	@Description("CatalFamilyFeatMaping_substances_description")
	private String substances;

	@Column
	@Description("CatalFamilyFeatMaping_warnings_description")
	private String warnings;

	@Column
	@Description("CatalFamilyFeatMaping_usage_description")
	private String usage;

	public String getPfIdentif() {
		return this.pfIdentif;
	}

	public void setPfIdentif(final String pfIdentif) {
		this.pfIdentif = pfIdentif;
	}

	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(final String familyName) {
		this.familyName = familyName;
	}

	public String getSubstances() {
		return this.substances;
	}

	public void setSubstances(final String substances) {
		this.substances = substances;
	}

	public String getWarnings() {
		return this.warnings;
	}

	public void setWarnings(final String warnings) {
		this.warnings = warnings;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(final String usage) {
		this.usage = usage;
	}

	@Override
	protected String makeIdentif() {
		return toIdentif(pfIdentif, langIso2);
	}

	public static String toIdentif(String pfIdentif, String langIso2) {
		return pfIdentif + "_" + langIso2;
	}
}