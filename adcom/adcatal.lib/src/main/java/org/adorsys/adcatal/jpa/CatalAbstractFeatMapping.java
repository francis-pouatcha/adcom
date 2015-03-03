package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
@Description("CatalAbstractFeatMapping_description")
public abstract class CatalAbstractFeatMapping extends AbstractIdentifData {

	private static final long serialVersionUID = 1768686745037227154L;

	@Column
	@Description("CatalAbstractFeatMapping_langIso2_description")
	@NotNull
	private String langIso2;

	@Column
	@Size(max=256)
	@Description("CatalAbstractFeatMapping_purpose_description")
	private String purpose;
	
	@Column
	@Description("CatalAbstractFeatMapping_usage_description")
	@Size(max=256)
	private String usage;
	
	@Column
	@Description("CatalAbstractFeatMapping_warnings_description")
	@Size(max=256)
	private String warnings;

	@Column
	@Description("CatalAbstractFeatMapping_substances_description")
	@Size(max=256)
	private String substances;
	
	public String getLangIso2() {
		return this.langIso2;
	}

	public void setLangIso2(final String langIso2) {
		this.langIso2 = langIso2;
	}

	public String getSubstances() {
		return this.substances;
	}

	public void setSubstances(final String substances) {
		this.substances = substances;
	}

	public String getUsage() {
		return this.usage;
	}

	public void setUsage(final String usage) {
		this.usage = usage;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getWarnings() {
		return warnings;
	}

	public void setWarnings(String warnings) {
		this.warnings = warnings;
	}

	public static boolean hasData(CatalAbstractFeatMapping obj){
		return 
				StringUtils.isBlank(obj.langIso2) && 
				StringUtils.isBlank(obj.purpose) && 
				StringUtils.isBlank(obj.usage) &&
				StringUtils.isBlank(obj.substances) && 
				StringUtils.isBlank(obj.warnings) && 
				StringUtils.isBlank(obj.identif);
	}
}