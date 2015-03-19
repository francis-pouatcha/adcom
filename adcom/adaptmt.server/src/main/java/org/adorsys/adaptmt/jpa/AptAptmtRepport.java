package org.adorsys.adaptmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractIdentifData;

@Entity
public class AptAptmtRepport extends AbstractIdentifData {

	@Column
	private String repportNbr;

	@Column
	private String aptmtIdentify;

	@Column
	private String reason;

	@Temporal(TemporalType.TIMESTAMP)
	private Date createDate;

	@Column
	@NotNull
	private String createdUserId;

	public String getAptmtIdentify() {
		return aptmtIdentify;
	}

	public void setAptmtIdentify(String aptmtIdentify) {
		this.aptmtIdentify = aptmtIdentify;
	}

	public String getRepportNbr() {
		return repportNbr;
	}

	public void setRepportNbr(String repportNbr) {
		this.repportNbr = repportNbr;
	}

	public String getReason() {
		return this.reason;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	@Override
	protected String makeIdentif() {
		// TODO Auto-generated method stub
		return repportNbr;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(final String createdUserId) {
		this.createdUserId = createdUserId;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (repportNbr != null && !repportNbr.trim().isEmpty())
			result += "repportNbr: " + repportNbr;
		if (reason != null && !reason.trim().isEmpty())
			result += ", reason: " + reason;
		if (createdUserId != null && !createdUserId.trim().isEmpty())
			result += ", createdUserId: " + createdUserId;
		return result;
	}
}