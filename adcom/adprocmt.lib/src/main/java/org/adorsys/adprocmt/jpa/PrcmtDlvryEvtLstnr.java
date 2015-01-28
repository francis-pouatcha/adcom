package org.adorsys.adprocmt.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractMvmtData;
import org.apache.commons.lang3.StringUtils;

@Entity
public class PrcmtDlvryEvtLstnr extends AbstractMvmtData {

	private static final long serialVersionUID = 1188761804226479118L;

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