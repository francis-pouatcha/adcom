package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.adcore.utils.BigDecimalUtils;
import org.adorsys.adcore.utils.CalendarUtil;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("InvInvtryItem_description")
public abstract class InvAbstractInvtryItem extends AbstractIdentifData {

	private static final long serialVersionUID = 4195806327796087791L;

	@Column
	@Description("InvInvtryItem_invtryNbr_description")
	@NotNull
	private String invtryNbr;

	@Column
	@Description("InvInvtryItem_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("InvInvtryItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("InvInvtryItem_artName_description")
	@NotNull
	private String artName;
	
	@Column
	@Description("InvInvtryItem_section_description")
	private String section;

	@Column
	@Description("InvInvtryItem_orgUnit_description")
	private String orgUnit;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("InvInvtryItem_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date acsngDt;

	@Column
	@Description("InvInvtryItem_expectedQty_description")
	private BigDecimal expectedQty;

	@Column
	@Description("InvInvtryItem_asseccedQty_description")
	private BigDecimal asseccedQty;

	@Column
	@Description("InvInvtryItem_gap_description")
	private BigDecimal gap;

	@Column
	@Description("InvInvtryItem_sppuPT_description")
	private BigDecimal sppuPT;

	@Column
	@Description("InvInvtryItem_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("InvInvtryItem_gapTotalSpPT_description")
	private BigDecimal gapTotalSpPT;

	@Column
	@Description("InvInvtryItem_pppuPT_description")
	private BigDecimal pppuPT;

	@Column
	@Description("InvInvtryItem_pppuCur_description")
	private String pppuCur;

	@Column
	@Description("InvInvtryItem_gapTotalPpPT_description")
	private BigDecimal gapTotalPpPT;

	@Column
	@Description("InvInvtryItem_acsngUser_description")
	@NotNull
	private String acsngUser;

	public String getInvtryNbr() {
		return this.invtryNbr;
	}

	public void setInvtryNbr(final String invtryNbr) {
		this.invtryNbr = invtryNbr;
	}

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

	public String getSection() {
		return this.section;
	}

	public void setSection(final String section) {
		this.section = section;
	}

	public String getOrgUnit() {
		return this.orgUnit;
	}

	public void setOrgUnit(final String orgUnit) {
		this.orgUnit = orgUnit;
	}

	public Date getAcsngDt() {
		return this.acsngDt;
	}

	public void setAcsngDt(final Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	public BigDecimal getExpectedQty() {
		return this.expectedQty;
	}

	public void setExpectedQty(final BigDecimal expectedQty) {
		this.expectedQty = expectedQty;
	}

	public BigDecimal getAsseccedQty() {
		return this.asseccedQty;
	}

	public void setAsseccedQty(final BigDecimal asseccedQty) {
		this.asseccedQty = asseccedQty;
	}

	public BigDecimal getGap() {
		return this.gap;
	}

	public void setGap(final BigDecimal gap) {
		this.gap = gap;
	}

	public BigDecimal getSppuPT() {
		return this.sppuPT;
	}

	public void setSppuPT(final BigDecimal sppuPT) {
		this.sppuPT = sppuPT;
	}

	public String getSppuCur() {
		return this.sppuCur;
	}

	public void setSppuCur(final String sppuCur) {
		this.sppuCur = sppuCur;
	}

	public BigDecimal getGapTotalSpPT() {
		return this.gapTotalSpPT;
	}

	public void setGapTotalSpPT(final BigDecimal gapTotalSpPT) {
		this.gapTotalSpPT = gapTotalSpPT;
	}

	public BigDecimal getPppuPT() {
		return this.pppuPT;
	}

	public void setPppuPT(final BigDecimal pppuPT) {
		this.pppuPT = pppuPT;
	}

	public String getPppuCur() {
		return this.pppuCur;
	}

	public void setPppuCur(final String pppuCur) {
		this.pppuCur = pppuCur;
	}

	public BigDecimal getGapTotalPpPT() {
		return this.gapTotalPpPT;
	}

	public void setGapTotalPpPT(final BigDecimal gapTotalPpPT) {
		this.gapTotalPpPT = gapTotalPpPT;
	}

	public String getAcsngUser() {
		return this.acsngUser;
	}

	public void setAcsngUser(final String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	@Override
	protected String makeIdentif() {
		return invtryNbr + "_" + lotPic + "_" + artPic + "_" + section;
	}
	
	public void copyTo(InvAbstractInvtryItem target) {
		target.setAcsngDt(acsngDt);
		target.setAcsngUser(acsngUser);
		target.setArtPic(artPic);
		target.setAsseccedQty(asseccedQty);
		target.setExpectedQty(expectedQty);
		target.setGap(gap);
		target.setGapTotalPpPT(gapTotalPpPT);
		target.setGapTotalSpPT(gapTotalSpPT);
		target.setInvtryNbr(invtryNbr);
		target.setLotPic(lotPic);
		target.setOrgUnit(orgUnit);
		target.setPppuCur(pppuCur);
		target.setPppuPT(pppuPT);
		target.setSection(section);
		target.setSppuCur(sppuCur);
		target.setSppuPT(sppuPT);
		target.setArtName(artName);
	}
	
	public boolean contentEquals(InvAbstractInvtryItem target) {
		if(!CalendarUtil.isSameDay(target.acsngDt,acsngDt)) return false;
		if(!StringUtils.equals(target.acsngUser,acsngUser)) return false;
		if(!StringUtils.equals(target.artPic,artPic)) return false;
		if(!StringUtils.equals(target.artName,artName)) return false;
		if(!BigDecimalUtils.numericEquals(target.asseccedQty,asseccedQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.expectedQty,expectedQty)) return false;
		if(!BigDecimalUtils.numericEquals(target.gap,gap)) return false;
		if(!BigDecimalUtils.numericEquals(target.gapTotalPpPT,gapTotalPpPT)) return false;
		if(!BigDecimalUtils.numericEquals(target.gapTotalSpPT,gapTotalSpPT)) return false;
		if(!StringUtils.equals(target.invtryNbr,invtryNbr)) return false;
		if(!StringUtils.equals(target.lotPic,lotPic)) return false;
		if(!StringUtils.equals(target.orgUnit,orgUnit)) return false;
		if(!StringUtils.equals(target.pppuCur,pppuCur)) return false;
		if(!BigDecimalUtils.numericEquals(target.pppuPT,pppuPT)) return false;
		if(!StringUtils.equals(target.section,section)) return false;
		if(!StringUtils.equals(target.sppuCur,sppuCur)) return false;
		if(!BigDecimalUtils.numericEquals(target.sppuPT,sppuPT)) return false;
		return true;
	}

	public void evlte() {
		if(this.asseccedQty==null) this.asseccedQty=BigDecimal.ZERO;
		if(this.expectedQty==null) this.expectedQty=BigDecimal.ZERO;
		this.gap = this.expectedQty.subtract(this.asseccedQty);
		if(this.pppuPT==null) this.pppuPT=BigDecimal.ZERO;
		this.gapTotalPpPT = gap.multiply(this.pppuPT);
		if(this.sppuPT==null) this.sppuPT=BigDecimal.ZERO;
		this.gapTotalSpPT = gap.multiply(this.sppuPT);
	}	
}