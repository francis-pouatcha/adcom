package org.adorsys.adprocmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;

@Entity
public class PrcmtPrcmtEvtLstnr extends AbstractMvmtData {

	private static final long serialVersionUID = 7951186500575283506L;

	@Column
	@NotNull
	private String lstnrName;

	@Column
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