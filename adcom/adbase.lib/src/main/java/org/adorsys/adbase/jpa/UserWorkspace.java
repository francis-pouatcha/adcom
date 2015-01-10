package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity 
@Table(name="BaseUserWorkspace")
@Description("UserWorkspace_description")
public class UserWorkspace extends AbstractTimedData {

	private static final long serialVersionUID = 6373469098816623372L;

	@Column
	@Description("UserWorkspace_loginName_description")
	@NotNull
	private String loginName;

	@Column
	@Description("UserWorkspace_ouWsIdentif_description")
	@NotNull
	private String ouWsIdentif;

	public String getLoginName() {
		return this.loginName;
	}

	public void setLoginName(final String loginName) {
		this.loginName = loginName;
	}

	public String getOuWsIdentif() {
		return this.ouWsIdentif;
	}

	public void setOuWsIdentif(final String ouWsIdentif) {
		this.ouWsIdentif = ouWsIdentif;
	}

	@Override
	protected String makeIdentif() {
		return ouWsIdentif+"_"+loginName;
	}

}