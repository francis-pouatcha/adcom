package org.adorsys.adsales.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsInvcePtnr_description")
public class SlsInvcePtnr extends AbstractIdentifData {

	private static final long serialVersionUID = -6896759485795494124L;

	@Column
	@Description("SlsInvcePtnr_invceNbr_description")
	@NotNull
	private String invceNbr;

	@Column
	@Description("SlsInvcePtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("SlsInvcePtnr_roleInInvce_description")
	@NotNull
	private String roleInInvce;

	public String getInvceNbr() {
		return this.invceNbr;
	}

	public void setInvceNbr(final String invceNbr) {
		this.invceNbr = invceNbr;
	}

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getRoleInInvce() {
		return this.roleInInvce;
	}

	public void setRoleInInvce(final String roleInInvce) {
		this.roleInInvce = roleInInvce;
	}

	@Override
	protected String makeIdentif() {
		return invceNbr + "_" + ptnrNbr + "_" + roleInInvce;
	}
}