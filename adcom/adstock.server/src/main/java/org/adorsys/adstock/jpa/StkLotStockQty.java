package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.description.Description;

@Entity
@Description("StkLotStockQty_description")
public class StkLotStockQty extends StkAbstractStockQty {

	private static final long serialVersionUID = -6004607524717707290L;

	@Column
	@Description("StkLotStockQty_lotPic_description")
	@NotNull
	private String lotPic;

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}
	
	public String artAndLotPic(){
		return getArtPic() + "_" + getLotPic();
	}
}