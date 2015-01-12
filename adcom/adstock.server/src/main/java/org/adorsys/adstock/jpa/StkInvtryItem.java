package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("StkInvtryItem_description")
public class StkInvtryItem extends AbstractIdentifData {

	private static final long serialVersionUID = 4787521802569514327L;

	@Column
	@Description("StkInvtryItem_invtryNbr_description")
	@NotNull
	private String invtryNbr;

	@Column
	@Description("StkInvtryItem_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkInvtryItem_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkInvtryItem_expectedQty_description")
	private BigDecimal expectedQty;

	@Column
	@Description("StkInvtryItem_asseccedQty_description")
	private BigDecimal asseccedQty;

	@Column
	@Description("StkInvtryItem_gap_description")
	private BigDecimal gap;

	@Column
	@Description("StkInvtryItem_sppuHT_description")
	private BigDecimal sppuHT;

	@Column
	@Description("StkInvtryItem_sppuCur_description")
	private String sppuCur;

	@Column
	@Description("StkInvtryItem_gapTotalSpHT_description")
	private BigDecimal gapTotalSpHT;

	@Column
	@Description("StkInvtryItem_pppuHT_description")
	private BigDecimal pppuHT;

	@Column
	@Description("StkInvtryItem_pppuCur_description")
	private String pppuCur;

	@Column
	@Description("StkInvtryItem_gapTotalPpHT_description")
	private BigDecimal gapTotalPpHT;

	@Column
	@Description("StkInvtryItem_acsngUser_description")
	@NotNull
	private String acsngUser;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkInvtryItem_acsngDt_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date acsngDt;

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

	public BigDecimal getSppuHT() {
		return this.sppuHT;
	}

	public void setSppuHT(final BigDecimal sppuHT) {
		this.sppuHT = sppuHT;
	}

	public String getSppuCur() {
		return this.sppuCur;
	}

	public void setSppuCur(final String sppuCur) {
		this.sppuCur = sppuCur;
	}

	public BigDecimal getGapTotalSpHT() {
		return this.gapTotalSpHT;
	}

	public void setGapTotalSpHT(final BigDecimal gapTotalSpHT) {
		this.gapTotalSpHT = gapTotalSpHT;
	}

	public BigDecimal getPppuHT() {
		return this.pppuHT;
	}

	public void setPppuHT(final BigDecimal pppuHT) {
		this.pppuHT = pppuHT;
	}

	public String getPppuCur() {
		return this.pppuCur;
	}

	public void setPppuCur(final String pppuCur) {
		this.pppuCur = pppuCur;
	}

	public BigDecimal getGapTotalPpHT() {
		return this.gapTotalPpHT;
	}

	public void setGapTotalPpHT(final BigDecimal gapTotalPpHT) {
		this.gapTotalPpHT = gapTotalPpHT;
	}

	public String getAcsngUser() {
		return this.acsngUser;
	}

	public void setAcsngUser(final String acsngUser) {
		this.acsngUser = acsngUser;
	}

	public Date getAcsngDt() {
		return this.acsngDt;
	}

	public void setAcsngDt(final Date acsngDt) {
		this.acsngDt = acsngDt;
	}

	@Override
	protected String makeIdentif() {
		return invtryNbr + "_" + artPic + "_" + lotPic;
	}
}