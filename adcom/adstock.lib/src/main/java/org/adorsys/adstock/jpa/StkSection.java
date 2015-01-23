package org.adorsys.adstock.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("StkSection_description")
public class StkSection extends AbstractTimedData {

	private static final long serialVersionUID = 1441848160823566249L;

	@Column
	@Description("StkSection_sectionCode_description")
	@NotNull
	private String sectionCode;

	@Column
	@Description("StkSection_parentCode_description")
	private String parentCode;

	@Column
	@Description("StkSection_name_description")
	@NotNull
	private String name;

	@Column
	@Description("StkSection_position_description")
	private String position;

	@Column
	@Description("StkSection_geoCode_description")
	private String geoCode;
	
	@Column
	@Description("StkSection_wharehouse_description")
	private String wharehouse;

	public String getSectionCode() {
		return this.sectionCode;
	}

	public void setSectionCode(final String sectionCode) {
		this.sectionCode = sectionCode;
	}

	public String getParentCode() {
		return this.parentCode;
	}

	public void setParentCode(final String parentCode) {
		this.parentCode = parentCode;
	}

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getPosition() {
		return this.position;
	}

	public void setPosition(final String position) {
		this.position = position;
	}

	public String getGeoCode() {
		return this.geoCode;
	}

	public void setGeoCode(final String geoCode) {
		this.geoCode = geoCode;
	}

	public String getWharehouse() {
		return wharehouse;
	}

	public void setWharehouse(String wharehouse) {
		this.wharehouse = wharehouse;
	}

	@Override
	protected String makeIdentif() {
		return StringUtils.isBlank(parentCode) ? sectionCode : parentCode + "_"
				+ sectionCode;
	}
}