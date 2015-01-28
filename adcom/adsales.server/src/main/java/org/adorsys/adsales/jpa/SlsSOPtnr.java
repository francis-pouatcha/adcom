package org.adorsys.adsales.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsSOPtnr_description")
public class SlsSOPtnr extends AbstractIdentifData {

	private static final long serialVersionUID = -9179784936255120208L;

	@Column
	@Description("SlsSOPtnr_soNbr_description")
	@NotNull
	private String soNbr;

	@Column
	@Description("SlsSOPtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("SlsSOPtnr_roleInSO_description")
	@NotNull
	private String roleInSO;

	public String getSoNbr() {
		return this.soNbr;
	}

	public void setSoNbr(final String soNbr) {
		this.soNbr = soNbr;
	}

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public String getRoleInSO() {
		return this.roleInSO;
	}

	public void setRoleInSO(final String roleInSO) {
		this.roleInSO = roleInSO;
	}

	@Override
	protected String makeIdentif() {
		return soNbr + "_" + ptnrNbr + "_" + roleInSO;
	}
}