package org.adorsys.adcatal.jpa;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalArticle_description")
public class CatalArticle extends CatalAbstractArticle {

	private static final long serialVersionUID = -1863080422505047660L;
	
	@Transient
	private CatalArt2ProductFamily familyFeatures = new CatalArt2ProductFamily();

	@Transient
	private CatalArtFeatMapping features = new CatalArtFeatMapping();
	
	@Transient
	private CatalPicMapping picMapping = new CatalPicMapping();

	public CatalArtFeatMapping getFeatures() {
		return features;
	}

	public void setFeatures(CatalArtFeatMapping features) {
		this.features = features;
	}

	public CatalArt2ProductFamily getFamilyFeatures() {
		return familyFeatures;
	}

	public void setFamilyFeatures(CatalArt2ProductFamily familyFeatures) {
		this.familyFeatures = familyFeatures;
	}

	public CatalPicMapping getPicMapping() {
		return picMapping;
	}

	public void setPicMapping(CatalPicMapping picMapping) {
		this.picMapping = picMapping;
	}

	public void copyTo(CatalArticle target) {
		super.copyTo(target);
		target.features = features;
		target.familyFeatures=familyFeatures;
		target.picMapping=picMapping;
	}
}