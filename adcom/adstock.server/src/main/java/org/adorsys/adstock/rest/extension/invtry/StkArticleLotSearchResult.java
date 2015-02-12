/**
 * 
 */
package org.adorsys.adstock.rest.extension.invtry;

import java.util.List;

import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;

/**
 * @author boriswaguia
 *
 */
public class StkArticleLotSearchResult {
	/**
	 * The articleLotSearchInput field.
	 */
	private StkArticleLotSearchInput articleLotSearchInput;
	/**
	 * The articleLots field.
	 */
	private List<StkArticleLot> articleLots;
	/**
	 * The articleLot2Ous field.
	 */
	private List<StkArticleLot2Ou> articleLot2Ous;
	/**
	 * The articleLot2StrgSctns field.
	 */
	private List<StkArticleLot2StrgSctn> articleLot2StrgSctns;
	public StkArticleLotSearchInput getArticleLotSearchInput() {
		return articleLotSearchInput;
	}
	public void setArticleLotSearchInput(
			StkArticleLotSearchInput articleLotSearchInput) {
		this.articleLotSearchInput = articleLotSearchInput;
	}
	public List<StkArticleLot> getArticleLots() {
		return articleLots;
	}
	public void setArticleLots(List<StkArticleLot> articleLots) {
		this.articleLots = articleLots;
	}
	public List<StkArticleLot2Ou> getArticleLot2Ous() {
		return articleLot2Ous;
	}
	public void setArticleLot2Ous(List<StkArticleLot2Ou> articleLot2Ous) {
		this.articleLot2Ous = articleLot2Ous;
	}
	public List<StkArticleLot2StrgSctn> getArticleLot2StrgSctns() {
		return articleLot2StrgSctns;
	}
	public void setArticleLot2StrgSctns(
			List<StkArticleLot2StrgSctn> articleLot2StrgSctns) {
		this.articleLot2StrgSctns = articleLot2StrgSctns;
	}
	@Override
	public String toString() {
		return "StkArticleLotSearchResult [articleLotSearchInput="
				+ articleLotSearchInput + ", articleLots=" + articleLots
				+ ", articleLot2Ous=" + articleLot2Ous
				+ ", articleLot2StrgSctns=" + articleLot2StrgSctns + "]";
	}
		
}
