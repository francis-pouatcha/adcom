package org.adorsys.adprocmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Entity
public class PrcmtPrcmtEvt extends PrcmtAbstractPrcmtHstry {
	private static final long serialVersionUID = -8495552044735234058L;

	@Column
	@NotNull
	private String lstnrName;

	@Column
	@NotNull
	private String evtName;

	public String getLstnrName() {
		return lstnrName;
	}

	public void setLstnrName(String lstnrName) {
		this.lstnrName = lstnrName;
	}

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}
}