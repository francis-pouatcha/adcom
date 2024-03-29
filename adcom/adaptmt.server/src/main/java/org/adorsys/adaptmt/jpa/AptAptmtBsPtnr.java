package org.adorsys.adaptmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;

@Entity
public class AptAptmtBsPtnr extends AbstractTimedData {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column
	@NotNull
	private String aptmtIdentify;
	
	@Column
	@NotNull
	private String contactnNbr;

	public String getAptmtIdentify() {
		return this.aptmtIdentify;
	}

	public void setAptmtIdentify(final String aptmtIdentify) {
		this.aptmtIdentify = aptmtIdentify;
	}
	

	public String getcontactnNbr() {
		return contactnNbr;
	}

	public void setcontactnNbr(String contactnNbr) {
		this.contactnNbr = contactnNbr;
	}

	@Override
	public String toString() {
		return "AptAptmtBsPtnr [aptmtIdentify=" + aptmtIdentify
				+ ", contactnNbr=" + contactnNbr + "]";
	}

	@Override
	protected String makeIdentif() {
		return aptmtIdentify + "_" + contactnNbr;
	}
}