package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public class StkAbstractLot2Section extends AbstractIdentifData {

	private static final long serialVersionUID = -8174913096678489715L;

	@Column
	@Description("StkAbstractLot2Section_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkAbstractLot2Section_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkAbstractLot2Section_strgSection_description")
	@NotNull
	private String strgSection;

	@Transient
	private StkAbstractSection stkSection;
	
	@Transient
	private StkAbstractArticleLot sectionArticleLot;
	
	public String getStrgSection() {
		return strgSection;
	}

	public void setStrgSection(String strgSection) {
		this.strgSection = strgSection;
	}

	public static String toId(String artPic, String lotPic, String strgSection){
		return artPic + "_" +lotPic +"_"+ strgSection;
	}
	@Override
	protected String makeIdentif() {
		return toId(artPic, lotPic, strgSection);
	}
	
	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return artPic;
	}

	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}

	public void copyTo(StkAbstractLot2Section target){
		target.artPic = artPic;
		target.lotPic = lotPic;
		target.strgSection=strgSection;
		target.stkSection = stkSection;
		target.sectionArticleLot =sectionArticleLot;
	}
	
	public boolean contentEquals(StkAbstractLot2Section target){
		if(!StringUtils.equals(target.strgSection,strgSection)) return false;
		if(!StringUtils.equals(target.lotPic, lotPic)) return false;
		if(!StringUtils.equals(target.artPic, artPic)) return false;
		return true;
	}
	
	public StkAbstractSection getStkSection() {
		return stkSection;
	}

	public void setStkSection(StkAbstractSection stkSection) {
		this.stkSection = stkSection;
	}

	public StkAbstractArticleLot getSectionArticleLot() {
		return sectionArticleLot;
	}

	public void setSectionArticleLot(StkAbstractArticleLot sectionArticleLot) {
		this.sectionArticleLot = sectionArticleLot;
	}
	
}