package org.adorsys.adaptmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.adorsys.adcore.jpa.AbstractIdentifData;
import org.adorsys.javaext.description.Description;
import org.apache.commons.lang3.StringUtils;

@Entity
@Description("AptAptmt_description")
public class AptAptmt extends AbstractIdentifData {

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_createDate_description")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_appointmentDate_description")
	private Date appointmentDate;

	@Column
	@Description("AptAptmt_aptmtnNbr_description")
	private String aptmtnNbr;

	@Column
	@Description("AptAptmt_createdUserId_description")
	private String createdUserId;

	@Column
	@Description("AptAptmt_closedUserId_description")
	private String closedUserId;

	@Column
	private AptmtStatus status;

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(final Date createDate) {
		this.createDate = createDate;
	}

	public String getAptmtnNbr() {
		return aptmtnNbr;
	}

	public void setAptmtnNbr(String aptmtnNbr) {
		this.aptmtnNbr = aptmtnNbr;
	}

	public String getCreatedUserId() {
		return this.createdUserId;
	}

	public void setCreatedUserId(final String createdUserId) {
		this.createdUserId = createdUserId;
	}

	public String getClosedUserId() {
		return this.closedUserId;
	}

	public void setClosedUserId(final String closedUserId) {
		this.closedUserId = closedUserId;
	}

	public AptmtStatus getStatus() {
		return this.status;
	}

	public void setStatus(final AptmtStatus status) {
		this.status = status;
	}

	public Date getAppointmentDate() {
		return appointmentDate;
	}

	public void setAppointmentDate(Date appointmentDate) {
		this.appointmentDate = appointmentDate;
	}

	@Override
	public String toString() {
		String result = getClass().getSimpleName() + " ";
		if (aptmtnNbr != null && !aptmtnNbr.trim().isEmpty())
			result += "aptmtnNbr: " + aptmtnNbr;
		if (createdUserId != null && !createdUserId.trim().isEmpty())
			result += ", createdUserId: " + createdUserId;
		if (closedUserId != null && !closedUserId.trim().isEmpty())
			result += ", closedUserId: " + closedUserId;
		return result;
	}

	@Override
	protected String makeIdentif() {
		// TODO Auto-generated method stub
		return aptmtnNbr;
	}

	@PrePersist
	public void checkStatus() {
		if (StringUtils.isBlank(status.toString()))
			setStatus(AptmtStatus.FORTHCOMMING);

	}
}