package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalFamilyFeatMaping_description")
public class CatalFamilyFeatMaping extends CatalAbstractFeatMapping {

	private static final long serialVersionUID = -2759458392635728552L;

	@Column
	@Description("CatalFamilyFeatMaping_pfIdentif_description")
	@NotNull
	private String pfIdentif;

	@Column
	@Description("CatalFamilyFeatMaping_familyName_description")
	private String familyName;
	@Column
	@Description("CatalFamilyFeatMaping_famPath_description")
	private String famPath;

	public String getPfIdentif() {
		return this.pfIdentif;
	}

	public void setPfIdentif(final String pfIdentif) {
		this.pfIdentif = pfIdentif;
	}

	public String getFamilyName() {
		return this.familyName;
	}

	public void setFamilyName(final String familyName) {
		this.familyName = familyName;
	}

	public String getFamPath() {
		return famPath;
	}

	public void setFamPath(String famPath) {
		this.famPath = famPath;
	}

	@Override
	protected String makeIdentif() {
		return toIdentif(pfIdentif, getLangIso2());
	}

	public static String toIdentif(String pfIdentif, String langIso2) {
		return pfIdentif + "_" + langIso2;
	}
}