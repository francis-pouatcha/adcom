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
public class ArticleLotSearchResult {
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
	
	private Long count;
	private ArtLotSearchInput searchInput;
	
	public ArticleLotSearchResult(Long count, List<StkArticleLot> resultList,  List<StkArticleLot2Ou> articleLot2Ous,
			ArtLotSearchInput searchInput)
	{
		super();
		this.count = count;
		this.articleLots = resultList;
		this.articleLot2Ous = articleLot2Ous;
		this.searchInput = searchInput;
	}
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
	
	public Long getCount() {
		return count;
	}
	public void setCount(Long count) {
		this.count = count;
	}
	public ArtLotSearchInput getSearchInput() {
		return searchInput;
	}
	public void setSearchInput(ArtLotSearchInput searchInput) {
		this.searchInput = searchInput;
	}
	@Override
	public String toString() {
		return "StkArticleLotSearchResult [articleLotSearchInput="
				+ articleLotSearchInput + ", articleLots=" + articleLots
				+ ", articleLot2Ous=" + articleLot2Ous
				+ ", articleLot2StrgSctns=" + articleLot2StrgSctns + "]";
	}

}
