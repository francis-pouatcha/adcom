package org.adorsys.adstock.jpa;

import java.util.ArrayList;
import java.util.Date;
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
public class StkArticleLotSearchInput {

	/**
	 * The entity holding search inputs.
	 */
	private StkArticleLot entity;

	/**
	 * The start cursor
	 */
	private int start = -1;

	/**
	 * The max number of records to return.
	 */
	private int max = -1;
	
	private Date from;
	   
	private Date to;

	/**
	 * The field names to be included in the search.
	 */
	private List<String> fieldNames = new ArrayList<String>();

	private boolean withStrgSection;

	private String sectionCode;

	/**
	 * The articles pics. if filled, it means the user want articleslot for all theses pics.
	 */
	private List<String> artPics;
	
	public StkArticleLot getEntity() {
		return entity;
	}

	public void setEntity(StkArticleLot entity) {
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

	public boolean isWithStrgSection() {
		return withStrgSection;
	}

	public void setWithStrgSection(boolean withStrgSection) {
		this.withStrgSection = withStrgSection;
	}

	public String getSectionCode() {
		return sectionCode;
	}

	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public List<String> getArtPics() {
		return artPics;
	}

	public void setArtPics(List<String> artPics) {
		this.artPics = artPics;
	}

	public Date getFrom() {
		return from;
	}

	public void setFrom(Date from) {
		this.from = from;
	}

	public Date getTo() {
		return to;
	}

	public void setTo(Date to) {
		this.to = to;
	}

	/**
	 * isFindByNameRange.
	 *
	 * @return
	 */
	public boolean isFindByNameRange() {
		return artPics != null && !artPics.isEmpty();
	}
}
