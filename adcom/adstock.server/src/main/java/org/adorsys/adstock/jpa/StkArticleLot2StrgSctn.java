package org.adorsys.adstock.jpa;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("StkArticleLot2StrgSctn_description")
public class StkArticleLot2StrgSctn extends AbstractIdentifData {

	private static final long serialVersionUID = -8793130123326839851L;

	@Column
	@Description("StkArticleLot2StrgSctn_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkArticleLot2StrgSctn_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkArticleLot2StrgSctn_strgSection_description")
	@NotNull
	private String strgSection;

	@Column
	@Description("StkArticleLot2StrgSctn_qty_description")
	@NotNull
	private BigDecimal qty;
	
	@Transient
	private StkSection stkSection;

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

	public BigDecimal getQty() {
		return qty;
	}

	public void setQty(BigDecimal qty) {
		this.qty = qty;
	}
	
	public StkSection getStkSection() {
		return stkSection;
	}

	public void setStkSection(StkSection stkSection) {
		this.stkSection = stkSection;
	}

	public void copyTo(StkArticleLot2StrgSctn target){
		target.artPic = artPic;
		target.lotPic = lotPic;
		target.strgSection=strgSection;
		target.qty = qty;
	}
	
	public boolean contentEquals(StkArticleLot2StrgSctn target){
		if(!BigDecimalUtils.numericEquals(target.qty,qty)) return false;
		if(!StringUtils.equals(target.strgSection,strgSection)) return false;
		if(!StringUtils.equals(target.lotPic, lotPic)) return false;
		if(!StringUtils.equals(target.artPic, artPic)) return false;
		return true;
	}
	
}