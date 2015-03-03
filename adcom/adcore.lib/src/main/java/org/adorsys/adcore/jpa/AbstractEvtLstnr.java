package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public abstract class AbstractEvtLstnr extends AbstractMvmtData {

	private static final long serialVersionUID = 1474296137695915462L;

	public static final String DEFAULT_LISTENER_NAME="default";
	
	@Column
	@NotNull
	private String lstnrName;

	@Column
	@NotNull
	private String evtName;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(makeId());
	}
	
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
	
	public String makeId(){
		return toId(lstnrName, evtName);
	}
	
	public static String toId(String lstnrName, String evtName){
		return lstnrName + "_" + evtName;
	}
}