package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

@MappedSuperclass
public abstract class AbstractEvtLease extends AbstractLease {

	private static final long serialVersionUID = 7956652728817830663L;

	@Column
	@NotNull
	private String evtName;
	
	@Column
	@NotNull
	private String evtId;

	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtId() {
		return evtId;
	}

	public void setEvtId(String evtId) {
		this.evtId = evtId;
	}
	
	@Override
	protected String makeIdentif() {
		return getHandlerName() + "_" + evtName + "_" + evtId;
	}
}
