package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("StkMvnt_description")
public class StkMvnt extends AbstractMvmtData {

	private static final long serialVersionUID = 8507933454388023908L;

	@Column
	@Description("StkMvnt_lotPic_description")
	@NotNull
	private String lotPic;

	@Column
	@Description("StkMvnt_artPic_description")
	@NotNull
	private String artPic;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkMvnt_mvntDt_description")
	@NotNull
	private Date mvntDt;

	@Column
	@Description("StkMvnt_mvgUser_description")
	@NotNull
	private String mvgUser;

	@Column
	@Description("StkMvnt_movedQty_description")
	@NotNull
	private BigDecimal movedQty;

	@Column
	@Description("StkMvnt_mvntType_description")
	@Enumerated
	@NotNull
	private StkMvtType mvntType;

	@Column
	@Description("StkMvnt_mvntOrigin_description")
	@Enumerated
	@NotNull
	private StkMvtTerminal mvntOrigin;

	@Column
	@Description("StkMvnt_mvntDest_description")
	@Enumerated
	@NotNull
	private StkMvtTerminal mvntDest;

	@Column
	@Description("StkMvnt_origDocNbrs_description")
	private String origDocNbrs;

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

	public Date getMvntDt() {
		return this.mvntDt;
	}

	public void setMvntDt(final Date mvntDt) {
		this.mvntDt = mvntDt;
	}

	public String getMvgUser() {
		return this.mvgUser;
	}

	public void setMvgUser(final String mvgUser) {
		this.mvgUser = mvgUser;
	}

	public BigDecimal getMovedQty() {
		return this.movedQty;
	}

	public void setMovedQty(final BigDecimal movedQty) {
		this.movedQty = movedQty;
	}

	public StkMvtType getMvntType() {
		return this.mvntType;
	}

	public void setMvntType(final StkMvtType mvntType) {
		this.mvntType = mvntType;
	}

	public StkMvtTerminal getMvntOrigin() {
		return this.mvntOrigin;
	}

	public void setMvntOrigin(final StkMvtTerminal mvntOrigin) {
		this.mvntOrigin = mvntOrigin;
	}

	public StkMvtTerminal getMvntDest() {
		return this.mvntDest;
	}

	public void setMvntDest(final StkMvtTerminal mvntDest) {
		this.mvntDest = mvntDest;
	}

	public String getOrigDocNbrs() {
		return this.origDocNbrs;
	}

	public void setOrigDocNbrs(final String origDocNbrs) {
		this.origDocNbrs = origDocNbrs;
	}
}