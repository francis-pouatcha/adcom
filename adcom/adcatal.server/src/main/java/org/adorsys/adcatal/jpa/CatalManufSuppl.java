package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalManufSuppl_description")
public class CatalManufSuppl extends AbstractTimedData {

	private static final long serialVersionUID = -3858948604703024433L;

	@Column
	@Description("CatalManufSuppl_name_description")
	private String name;

	@Column
	@Description("CatalManufSuppl_msCode_description")
	private String msCode;

	@Column
	@Description("CatalManufSuppl_mobile_description")
	private String mobile;

	@Column
	@Description("CatalManufSuppl_fax_description")
	private String fax;

	@Column
	@Description("CatalManufSuppl_email_description")
	private String email;

	@Column
	@Description("CatalManufSuppl_zipCode_description")
	private String zipCode;

	@Column
	@Description("CatalManufSuppl_country_description")
	private String country;

	@Column
	@Description("CatalManufSuppl_internetSite_description")
	private String internetSite;

	@Column
	@Description("CatalManufSuppl_responsable_description")
	private String responsable;

	@Column
	@Description("CatalManufSuppl_revenue_description")
	private String revenue;

	@Column
	@Description("CatalManufSuppl_taxIdNumber_description")
	private String taxIdNumber;

	@Column
	@Description("CatalManufSuppl_contact_description")
	private String contact;

	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getMsCode() {
		return this.msCode;
	}

	public void setMsCode(final String msCode) {
		this.msCode = msCode;
	}

	public String getMobile() {
		return this.mobile;
	}

	public void setMobile(final String mobile) {
		this.mobile = mobile;
	}

	public String getFax() {
		return this.fax;
	}

	public void setFax(final String fax) {
		this.fax = fax;
	}

	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	public String getZipCode() {
		return this.zipCode;
	}

	public void setZipCode(final String zipCode) {
		this.zipCode = zipCode;
	}

	public String getCountry() {
		return this.country;
	}

	public void setCountry(final String country) {
		this.country = country;
	}

	public String getInternetSite() {
		return this.internetSite;
	}

	public void setInternetSite(final String internetSite) {
		this.internetSite = internetSite;
	}

	public String getResponsable() {
		return this.responsable;
	}

	public void setResponsable(final String responsable) {
		this.responsable = responsable;
	}

	public String getRevenue() {
		return this.revenue;
	}

	public void setRevenue(final String revenue) {
		this.revenue = revenue;
	}

	public String getTaxIdNumber() {
		return this.taxIdNumber;
	}

	public void setTaxIdNumber(final String taxIdNumber) {
		this.taxIdNumber = taxIdNumber;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(final String contact) {
		this.contact = contact;
	}

	@Override
	protected String makeIdentif() {
		return msCode;
	}
}