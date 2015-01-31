package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("CatalArtFeatMapping_description")
public class CatalArtFeatMapping extends AbstractIdentifData {

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
		return toIdentif(artIdentif, langIso2);
	}
	
	public static String toIdentif(String artIdentif, String langIso2){
		return artIdentif + "_" + langIso2;
	}
	
	public static boolean hasData(CatalArtFeatMapping obj){
		return 
				StringUtils.isBlank(obj.artName) && 
				StringUtils.isBlank(obj.artIdentif) && 
				StringUtils.isBlank(obj.langIso2) && 
				StringUtils.isBlank(obj.substances) && 
				StringUtils.isBlank(obj.identif) && 
				StringUtils.isBlank(obj.usage);
	}
}