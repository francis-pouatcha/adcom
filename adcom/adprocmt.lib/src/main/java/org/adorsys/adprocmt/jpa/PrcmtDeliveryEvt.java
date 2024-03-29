package org.adorsys.adprocmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class PrcmtDeliveryEvt extends PrcmtAbstractDlvrHstry {

	private static final long serialVersionUID = 5284266505616864038L;

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