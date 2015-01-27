package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("StkArtLotSection_description")
public class StkArtLotSection extends AbstractTimedData {

	private static final long serialVersionUID = 5065852875520951827L;

	@Column
	@Description("StkArtLotSection_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkArtLotSection_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkArtLotSection_sectionCode_description")
	@NotNull
	private String sectionCode;

	public String getLotPic() {
		return this.lotPic;
	}

	public void setLotPic(final String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(final String sectionCode) {
		this.sectionCode = sectionCode;
	}

	@Override
	protected String makeIdentif() {
		return sectionCode+"_"+artPic + "_" + lotPic;
	}
}