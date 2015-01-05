package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
public class SecAbstractTerminal extends AbstractTimedData {

	private static final long serialVersionUID = 3459575818704625971L;

	@Column
	@Description("SecTerminal_termName_description")
	@NotNull
	private String termName;

	@Column
	@Description("SecTerminal_termId_description")
	@NotNull
	private String termId;

	@Column
	@Description("SecTerminal_partnerId_description")
	private String partnerIds;

	@Column
	@Description("SecTerminal_locality_description")
	private String locality;

	@Column
	@Description("SecTerminal_timeZone_description")
	private String timeZone;

	@Column
	@Description("SecTerminal_accessTime_description")
	private String accessTime;

	@Column
	@Description("SecTerminal_authUsers_description")
	private String authUsers;

	@Column
	@Description("SecTerminal_authWorkspaces_description")
	private String authWorkspaces;

	@Column
	@Description("SecTerminal_macAddress_description")
	private String macAddress;
	
	public SecAbstractTerminal() {}

	public SecAbstractTerminal(SecAbstractTerminal d) {
		super();
		this.termName = d.termName;
		this.termId = d.termId;
		this.partnerIds = d.partnerIds;
		this.locality = d.locality;
		this.timeZone = d.timeZone;
		this.accessTime = d.accessTime;
		this.authUsers = d.authUsers;
		this.authWorkspaces = d.authWorkspaces;
		this.macAddress = d.macAddress;
	}

	public String getTermName() {
		return termName;
	}

	public void setTermName(String termName) {
		this.termName = termName;
	}

	public String getTermId() {
		return termId;
	}

	public void setTermId(String termId) {
		this.termId = termId;
	}

	public String getPartnerIds() {
		return partnerIds;
	}

	public void setPartnerIds(String partnerIds) {
		this.partnerIds = partnerIds;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getTimeZone() {
		return timeZone;
	}

	public void setTimeZone(String timeZone) {
		this.timeZone = timeZone;
	}

	public String getAccessTime() {
		return accessTime;
	}

	public void setAccessTime(String accessTime) {
		this.accessTime = accessTime;
	}

	public String getAuthUsers() {
		return authUsers;
	}

	public void setAuthUsers(String authUsers) {
		this.authUsers = authUsers;
	}

	public String getAuthWorkspaces() {
		return authWorkspaces;
	}

	public void setAuthWorkspaces(String authWorkspaces) {
		this.authWorkspaces = authWorkspaces;
	}

	public String getMacAddress() {
		return macAddress;
	}

	public void setMacAddress(String macAddress) {
		this.macAddress = macAddress;
	}

	@Override
	protected String makeIdentif() {
		return termId;
	}
	
}
