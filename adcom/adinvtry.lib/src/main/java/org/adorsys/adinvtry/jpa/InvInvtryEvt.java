package org.adorsys.adinvtry.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class InvInvtryEvt extends InvAbstractInvtryHstry {
	private static final long serialVersionUID = 2103133595272538429L;

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