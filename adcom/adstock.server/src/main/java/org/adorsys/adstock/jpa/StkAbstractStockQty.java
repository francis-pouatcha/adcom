package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
public abstract class StkAbstractStockQty extends AbstractMvmtData {

	private static final long serialVersionUID = 5437976674412946410L;

	@Column
	@Description("StkArtStockQty_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkArtStockQty_stockQty_description")
	@NotNull
	private BigDecimal stockQty;

	@Column
	@Description("StkArtStockQty_rsrvdQty_description")
	@NotNull
	private BigDecimal rsrvdQty;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArtStockQty_qtyDt_description")
	@NotNull
	private Date qtyDt;
	
	// The originating process. Sales, Inventory, Procurement
	@Column
	@Description("StkArtStockQty_origProcs_description")
	private String origProcs;

	// The identifier of the origin process.
	@Column
	@Description("StkArtStockQty_origProcsNbr_description")
	private String origProcsNbr;
	
	// The number is incremented every time a new stock quantity
	// is computed from a parent. The new seqNbr is the sequence
	// number of the parent increased by one.
	@NotNull
	@Description("StkArtStockQty_seqNbr_description")
	private Integer seqNbr;
	
	// For any seqNbr > 0, the id of the parent rec must be documented here.
	// Unless it is a consolidated record.
	@Description("StkArtStockQty_parentRcrd_description")
	private String parentRcrd;
	
	// Set to true if the record is consolidated. In that case there
	// will be no prntRecord.
	@Description("StkArtStockQty_cnsldtd_description")
	private Boolean cnsldtd;

	public String getArtPic() {
		return this.artPic;
	}

	public void setArtPic(final String artPic) {
		this.artPic = artPic;
	}

	public BigDecimal getStockQty() {
		return this.stockQty;
	}

	public void setStockQty(final BigDecimal stockQty) {
		this.stockQty = stockQty;
	}

	public Date getQtyDt() {
		return this.qtyDt;
	}

	public void setQtyDt(final Date qtyDt) {
		this.qtyDt = qtyDt;
	}

	public Integer getSeqNbr() {
		return seqNbr;
	}

	public void setSeqNbr(Integer seqNbr) {
		this.seqNbr = seqNbr;
	}

}