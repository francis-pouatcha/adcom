package org.adorsys.adcatal.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Holds an entity and corresponding field descriptions for a search by example
 * call.
 * 
 * @author francis pouatcha
 *
 */
@XmlRootElement
public class CatalArticleSearchInput {

	/**
	 * The entity holding search inputs.
	 */
	private CatalArticle entity;

	/**
	 * The start cursor
	 */
	private int start = -1;

	/**
	 * The max number of records to return.
	 */
	private int max = -1;

	/*
	 * Used to hold an all purpose search string. Will help use the same field
	 * to search for codes and names.
	 */
	private String codesAndNames;

	/**
	 * The field names to be included in the search.
	 */
	private List<String> fieldNames = new ArrayList<String>();

	private String startRange;
	private String endRange;
	
	public CatalArticle getEntity() {
		return entity;
	}

	public void setEntity(CatalArticle entity) {
		this.entity = entity;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public String getCodesAndNames() {
		return codesAndNames;
	}

	public void setCodesAndNames(String codesAndNames) {
		this.codesAndNames = codesAndNames;
	}

	public String getStartRange() {
		return startRange;
	}

	public void setStartRange(String startRange) {
		this.startRange = startRange;
	}

	public String getEndRange() {
		return endRange;
	}

	public void setEndRange(String endRange) {
		this.endRange = endRange;
	}
	
}
