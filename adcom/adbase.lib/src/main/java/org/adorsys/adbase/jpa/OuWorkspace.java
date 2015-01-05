package org.adorsys.adbase.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("OuWorkspace_description")
public class OuWorkspace extends AbstractTimedData {

	private static final long serialVersionUID = 5003489119950388983L;

	@Column
	@Description("OuWorkspace_ownerOuIdentif_description")
	@NotNull
	private String ownerOuIdentif;

	@Column
	@Description("OuWorkspace_wsIdentif_description")
	@NotNull
	private String wsIdentif;

	@Column
	@Description("OuWorkspace_targetOuIdentif_description")
	@NotNull
	private String targetOuIdentif;

	public String getOwnerOuIdentif() {
		return this.ownerOuIdentif;
	}

	public void setOwnerOuIdentif(final String ownerOuIdentif) {
		this.ownerOuIdentif = ownerOuIdentif;
	}

	public String getWsIdentif() {
		return this.wsIdentif;
	}

	public void setWsIdentif(final String wsIdentif) {
		this.wsIdentif = wsIdentif;
	}

	public String getTargetOuIdentif() {
		return this.targetOuIdentif;
	}

	public void setTargetOuIdentif(final String targetOuIdentif) {
		this.targetOuIdentif = targetOuIdentif;
	}

	@Override
	protected String makeIdentif() {
		return ownerOuIdentif + "_" + wsIdentif + "_" + targetOuIdentif;
	}
}