package org.adorsys.adcshdwr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class CdrDrctSalesEvt extends CdrAbstractDsHstry {
	private static final long serialVersionUID = -5845445994055585067L;

	@Column
	@NotNull
	private String evtName;

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
}