package org.adorsys.adaptmt.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.PrePersist;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.jpa.AbstractTimedData;
import org.adorsys.javaext.description.Description;
import org.adorsys.javaext.format.DateFormatPattern;

@Entity
@Description("AptAptmt_description")
public class AptAptmt extends AbstractTimedData {
	private static final long serialVersionUID = 5118150729653002565L;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_createDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
	private Date createDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AptAptmt_appointmentDate_description")
	@DateFormatPattern(pattern = "dd-MM-yyyy HH:mm")
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
	@Description("AptAptmt_status_description")
	private AptmtStatus status;

	@Column
	@NotNull
	@Description("AptAptmt_title_description")
	private String title;

	@Column
	@Description("AptAptmt_description_description")
	private String description;

	@Column
	@Description("AptAptmt_locality_description")
	private String locality;

	@Column
	@Description("AptAptmt_parentIdentify_description")
	private String parentIdentify;

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

	public String getTitle() {
		return this.title;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getLocality() {
		return this.locality;
	}

	public void setLocality(final String locality) {
		this.locality = locality;
	}

	public String getParentIdentify() {
		return this.parentIdentify;
	}

	public void setParentIdentify(final String parentIdentify) {
		this.parentIdentify = parentIdentify;
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
		if (title != null && !title.trim().isEmpty())
			result += ", title: " + title;
		if (description != null && !description.trim().isEmpty())
			result += ", description: " + description;
		if (locality != null && !locality.trim().isEmpty())
			result += ", locality: " + locality;
		if (parentIdentify != null && !parentIdentify.trim().isEmpty())
			result += ", parentIdentify: " + parentIdentify;
		return result;
	}

	@Override
	protected String makeIdentif() {
		return aptmtnNbr;
	}

	@PrePersist
	public void checkStatus() {
		if (status == null)
			this.setStatus(AptmtStatus.FORTHCOMMING);
	}

}
