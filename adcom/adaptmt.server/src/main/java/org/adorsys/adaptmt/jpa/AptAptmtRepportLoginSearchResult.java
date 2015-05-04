package org.adorsys.adaptmt.jpa;

import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class AptAptmtRepportLoginSearchResult {

	/*
	 * The number of entities matching this search.
	 */
	private Long count;

	/*
	 * The result list.
	 */
	private List<AptAptmtRepportLogin> resultList;

	/*
	 * The original search input object. For stateless clients.
	 */
	private AptAptmtRepportLoginSearchInput searchInput;

	public AptAptmtRepportLoginSearchResult() {
		super();
	}

	public AptAptmtRepportLoginSearchResult(Long count,
			List<AptAptmtRepportLogin> resultList,
			AptAptmtRepportLoginSearchInput searchInput) {
		super();
		this.count = count;
		this.resultList = resultList;
		this.searchInput = searchInput;
	}

	public Long getCount() {
		return count;
	}

	public List<AptAptmtRepportLogin> getResultList() {
		return resultList;
	}

	public AptAptmtRepportLoginSearchInput getSearchInput() {
		return searchInput;
	}

	public void setCount(Long count) {
		this.count = count;
	}

	public void setResultList(List<AptAptmtRepportLogin> resultList) {
		this.resultList = resultList;
	}

	public void setSearchInput(AptAptmtRepportLoginSearchInput searchInput) {
		this.searchInput = searchInput;
	}

}
