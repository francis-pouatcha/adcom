package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("CatalProductFamily_description")
public class CatalProductFamily extends AbstractIdentifData {

	private static final long serialVersionUID = -3125003867184463811L;

	@Column
	@Description("CatalProductFamily_famCode_description")
	@NotNull
	private String famCode;

	@Column
	@Description("CatalProductFamily_parentIdentif_description")
	private String parentIdentif;
	
	/*
	 * This is the mapping of the path from the root to the immediate parent of
	 * this product. We will use slash as a path separator.
	 */
	@Column
	@Description("CatalProductFamily_famPath_description")
	private String famPath;
	
	@Transient
	private CatalFamilyFeatMaping features = new CatalFamilyFeatMaping();

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(famCode))
			famCode = SequenceGenerator.getSequence(SequenceGenerator.PRODUCT_FAMILY_SEQUENCE_PREFIXE);
		
		setIdentif(famCode);
		setId(famCode);
	}
	
	public String getParentIdentif() {
		return this.parentIdentif;
	}

	public void setParentIdentif(final String parentIdentif) {
		this.parentIdentif = parentIdentif;
	}

	public String getFamCode() {
		return famCode;
	}

	public void setFamCode(String famCode) {
		this.famCode = famCode;
	}

	@Override
	protected String makeIdentif() {
		if(StringUtils.isBlank(famCode)) famCode=SequenceGenerator.getSequence(SequenceGenerator.PRODUCT_FAMILY_SEQUENCE_PREFIXE);
		return famCode;
	}

	public CatalFamilyFeatMaping getFeatures() {
		return features;
	}

	public void setFeatures(CatalFamilyFeatMaping features) {
		this.features = features;
	}

	public String getFamPath() {
		return famPath;
	}

	public void setFamPath(String famPath) {
		this.famPath = famPath;
	}

	public void copyTo(CatalProductFamily target) {
		target.famCode=famCode;
		target.famPath=famPath;
		target.features=features;
		target.identif=identif;
		target.parentIdentif=parentIdentif;
	}
}