package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalArtEquivalence_description")
public class CatalArtEquivalence extends AbstractTimedData {

	private static final long serialVersionUID = 7808069147519207704L;

	@Column
	@Description("CatalArtEquivalence_artEquivCode_description")
	@NotNull
	private String artEquivCode;

	@Column
	@Description("CatalArtEquivalence_mainArtIdentif_description")
	@NotNull
	private String mainArtIdentif;

	@Column
	@Description("CatalArtEquivalence_equivArtIdentif_description")
	@NotNull
	private String equivArtIdentif;
	
	@Transient
	@Description("CatalArtEquivalence_mainArtName_description")
	private String mainArtName;
	
	@Transient
	@Description("CatalArtEquivalence_mainArtEquiv_description")
	private String equivArtName;
	
    public String getMainArtName() {
		return mainArtName;
	}
    
    public String getEquivArtName() {
		return equivArtName;
	}
    
    public void setMainArtName(String mainArtName) {
		this.mainArtName = mainArtName;
	}
    
    public void setEquivArtName(String equivArtName) {
		this.equivArtName = equivArtName;
	}

	public String getArtEquivCode() {
		return this.artEquivCode;
	}

	public void setArtEquivCode(final String artEquivCode) {
		this.artEquivCode = artEquivCode;
	}

	public String getMainArtIdentif() {
		return this.mainArtIdentif;
	}

	public void setMainArtIdentif(final String mainArtIdentif) {
		this.mainArtIdentif = mainArtIdentif;
	}

	public String getEquivArtIdentif() {
		return this.equivArtIdentif;
	}

	public void setEquivArtIdentif(final String equivArtIdentif) {
		this.equivArtIdentif = equivArtIdentif;
	}

	@Override
	protected String makeIdentif() {
		return artEquivCode;
	}
}