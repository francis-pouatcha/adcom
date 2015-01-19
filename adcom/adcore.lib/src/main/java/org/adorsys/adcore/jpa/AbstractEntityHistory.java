package org.adorsys.adcore.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.javaext.description.Description;

@MappedSuperclass
public abstract class AbstractEntityHistory extends AbstractEntity {

	private static final long serialVersionUID = -7391189914830675838L;

	@Column
	@Description("AbstractEntityHistory_entIdentif_description")
	@NotNull
	private String entIdentif;

	@Column
	@Description("AbstractEntityHistory_entStatus_description")
	@NotNull
	private String entStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Description("AbstractEntityHistory_hstryDt_description")
	@NotNull
	private Date hstryDt;

	@Column
	@Description("AbstractEntityHistory_orignLogin_description")
	@NotNull
	private String orignLogin;

	@Column
	@Description("AbstractEntityHistory_orignWrkspc_description")
	@NotNull
	private String orignWrkspc;

	@Column
	@Description("AbstractEntityHistory_procStep_description")
	@NotNull
	private String procStep;

	@Column
	@Description("AbstractEntityHistory_hstryType_description")
	@NotNull
	private String hstryType;

	@Column
	@Description("AbstractEntityHistory_comment_description")
	private String comment;

	@Column
	@Description("AbstractEntityHistory_addtnlInfo_description")
	private String addtnlInfo;

	public String getEntIdentif() {
		return entIdentif;
	}

	public void setEntIdentif(String entIdentif) {
		this.entIdentif = entIdentif;
	}

	public String getEntStatus() {
		return entStatus;
	}

	public void setEntStatus(String entStatus) {
		this.entStatus = entStatus;
	}

	public Date getHstryDt() {
		return this.hstryDt;
	}

	public void setHstryDt(final Date hstryDt) {
		this.hstryDt = hstryDt;
	}

	public String getOrignLogin() {
		return this.orignLogin;
	}

	public void setOrignLogin(final String orignLogin) {
		this.orignLogin = orignLogin;
	}

	public String getOrignWrkspc() {
		return this.orignWrkspc;
	}

	public void setOrignWrkspc(final String orignWrkspc) {
		this.orignWrkspc = orignWrkspc;
	}

	public String getProcStep() {
		return this.procStep;
	}

	public void setProcStep(final String procStep) {
		this.procStep = procStep;
	}

	public String getHstryType() {
		return this.hstryType;
	}

	public void setHstryType(final String hstryType) {
		this.hstryType = hstryType;
	}

	public String getComment() {
		return this.comment;
	}

	public void setComment(final String comment) {
		this.comment = comment;
	}

	public String getAddtnlInfo() {
		return this.addtnlInfo;
	}

	public void setAddtnlInfo(final String addtnlInfo) {
		this.addtnlInfo = addtnlInfo;
	}
}