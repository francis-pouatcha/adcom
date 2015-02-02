package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("CatalArtFeatMapping_description")
public class CatalArtFeatMapping extends CatalAbstractFeatMapping {

	private static final long serialVersionUID = -6409127372502988751L;

	@Column
	@Description("CatalArtFeatMapping_artIdentif_description")
	@NotNull
	private String artIdentif;

	@Column
	@Description("CatalArtFeatMapping_artName_description")
	private String artName;

	public String getArtIdentif() {
		return this.artIdentif;
	}

	public void setArtIdentif(final String artIdentif) {
		this.artIdentif = artIdentif;
	}

	public String getArtName() {
		return this.artName;
	}

	public void setArtName(final String artName) {
		this.artName = artName;
	}

	@Override
	protected String makeIdentif() {
		return toIdentif(artIdentif, getLangIso2());
	}
	
	public static String toIdentif(String artIdentif, String langIso2){
		return artIdentif + "_" + langIso2;
	}
	
	public static boolean hasData(CatalArtFeatMapping obj){
		return 	CatalAbstractFeatMapping.hasData(obj) &&
				StringUtils.isBlank(obj.artName) && 
				StringUtils.isBlank(obj.artIdentif);
	}
}