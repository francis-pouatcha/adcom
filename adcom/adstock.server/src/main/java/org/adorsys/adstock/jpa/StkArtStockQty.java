package org.adorsys.adstock.jpa;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("StkArtStockQty_description")
public class StkArtStockQty extends AbstractMvmtData {

	private static final long serialVersionUID = 1173421118771666655L;

	@Column
	@Description("StkArtStockQty_artPic_description")
	@NotNull
	private String artPic;

	@Column
	@Description("StkArtStockQty_stockQty_description")
	@NotNull
	private BigDecimal stockQty;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("StkArtStockQty_qtyDt_description")
	@NotNull
	private Date qtyDt;

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
}