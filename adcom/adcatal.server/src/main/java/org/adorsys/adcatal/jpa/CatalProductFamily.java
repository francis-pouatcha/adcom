package org.adorsys.adcatal.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CatalProductFamily_description")
public class CatalProductFamily extends AbstractTimedData {

	private static final long serialVersionUID = -3125003867184463811L;

	@Column
	@Description("CatalProductFamily_famCode_description")
	@NotNull
	private String famCode;

	@Column
	@Description("CatalProductFamily_parentIdentif_description")
	private String parentIdentif;

	public String getParentIdentif() {
		return this.parentIdentif;
	}

	public void setParentIdentif(final String parentIdentif) {
		this.parentIdentif = parentIdentif;
	}

	public String getFamCode() {
		return famCode;
	}

	public void setFamCode(String famCode) {
		this.famCode = famCode;
	}

	@Override
	protected String makeIdentif() {
		return famCode;
	}
}