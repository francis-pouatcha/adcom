/**
 * 
 */
package org.adorsys.adstock.rest.extension.invtry;

import java.util.List;

import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.jpa.StkLotStockQty;


/**
 * @author boriswaguia
 *
 */
public class StkArticleLotDTO {
	
	/**
	 * The artPic field.
	 */
	private String artPic;
	/**
	 * The articleLot2Ou field.
	 */
	private StkArticleLot2Ou articleLot2Ou;
	/**
	 * The stkArticleLot field.
	 */
	private StkArticleLot stkArticleLot;
	/**
	 * The artStokQtys field.
	 */
	private List<StkArtStockQty> artStokQtys;
	/**
	 * The stkLotStkQtys field.
	 */
	private List<StkLotStockQty> stkLotStkQtys;
	
	public String getArtPic() {
		return artPic;
	}
	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}
	public StkArticleLot2Ou getArticleLot2Ou() {
		return articleLot2Ou;
	}
	public void setArticleLot2Ou(StkArticleLot2Ou articleLot2Ou) {
		this.articleLot2Ou = articleLot2Ou;
	}
	public StkArticleLot getStkArticleLot() {
		return stkArticleLot;
	}
	public void setStkArticleLot(StkArticleLot stkArticleLot) {
		this.stkArticleLot = stkArticleLot;
	}
	public List<StkArtStockQty> getArtStokQtys() {
		return artStokQtys;
	}
	public void setArtStokQtys(List<StkArtStockQty> artStokQtys) {
		this.artStokQtys = artStokQtys;
	}
	public List<StkLotStockQty> getStkLotStkQtys() {
		return stkLotStkQtys;
	}
	public void setStkLotStkQtys(List<StkLotStockQty> stkLotStkQtys) {
		this.stkLotStkQtys = stkLotStkQtys;
	}
	
}