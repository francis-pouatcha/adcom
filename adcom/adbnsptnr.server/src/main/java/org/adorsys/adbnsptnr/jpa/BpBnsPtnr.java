package org.adorsys.adbnsptnr.jpa;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BpBnsPtnr_description")
public class BpBnsPtnr extends AbstractTimedData {

	private static final long serialVersionUID = -9038535636658903372L;

	@Column
	@Description("BpBnsPtnr_ptnrNbr_description")
	@NotNull
	private String ptnrNbr;

	@Column
	@Description("BpBnsPtnr_ptnrType_description")
	@Enumerated
	private BpPtnrType ptnrType;

	@Column
	@Description("BpBnsPtnr_ctryOfRsdnc_description")
	private String ctryOfRsdnc;

	public String getPtnrNbr() {
		return this.ptnrNbr;
	}

	public void setPtnrNbr(final String ptnrNbr) {
		this.ptnrNbr = ptnrNbr;
	}

	public BpPtnrType getPtnrType() {
		return this.ptnrType;
	}

	public void setPtnrType(final BpPtnrType ptnrType) {
		this.ptnrType = ptnrType;
	}

	public String getCtryOfRsdnc() {
		return this.ctryOfRsdnc;
	}

	public void setCtryOfRsdnc(final String ctryOfRsdnc) {
		this.ctryOfRsdnc = ctryOfRsdnc;
	}

	@Override
	protected String makeIdentif() {
		return ptnrNbr;
	}
}