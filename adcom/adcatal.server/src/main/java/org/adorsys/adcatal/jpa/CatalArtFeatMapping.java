package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalArtFeatMapping_description")
public class CatalArtFeatMapping extends AbstractTimedData {

	private static final long serialVersionUID = -35779531489146424L;

	@Column
	@Description("CatalArtFeatMapping_artIdentif_description")
	@NotNull
	private String artIdentif;

	@Column
	@Description("CatalArtFeatMapping_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Description("CatalArtFeatMapping_artName_description")
	private String artName;

	@Column
	@Description("CatalArtFeatMapping_substances_description")
	private String substances;

	@Column
	@Description("CatalArtFeatMapping_usage_description")
	private String usage;

	public String getArtIdentif() {
		return this.artIdentif;
	}

	public void setArtIdentif(final String artIdentif) {
		this.artIdentif = artIdentif;
	}

	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

	public String getArtName() {
		return this.artName;
	}

	public void setArtName(final String artName) {
		this.artName = artName;
	}

	public String getSubstances() {
		return this.substances;
	}

	public void setSubstances(final String substances) {
		this.substances = substances;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(final String usage) {
		this.usage = usage;
	}

	@Override
	protected String makeIdentif() {
		return artIdentif + "_" + langIso2;
	}
}