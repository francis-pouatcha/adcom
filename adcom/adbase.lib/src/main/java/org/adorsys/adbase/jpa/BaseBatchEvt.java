package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractEntityHistory;

@Entity
public class BaseBatchEvt extends AbstractEntityHistory {

	private static final long serialVersionUID = -962269413175700054L;

	@Column
	@NotNull
	private String evtName;

	@Column
	@NotNull
	private String evtKlass;

	@Column
	@NotNull
	private String evtModule;
	
	public String getEvtName() {
		return evtName;
	}

	public void setEvtName(String evtName) {
		this.evtName = evtName;
	}

	public String getEvtKlass() {
		return evtKlass;
	}

	public void setEvtKlass(String evtKlass) {
		this.evtKlass = evtKlass;
	}

	public String getEvtModule() {
		return evtModule;
	}

	public void setEvtModule(String evtModule) {
		this.evtModule = evtModule;
	}
}
