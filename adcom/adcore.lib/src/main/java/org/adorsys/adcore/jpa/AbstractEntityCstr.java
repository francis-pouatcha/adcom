package org.adorsys.adcore.jpa;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import org.adorsys.adcore.utils.FormatedValidFrom;

/**
 * This defines a constraint on an entity. This constraint can be :
 * 	- a time based: means the constrain is occurring sometime in the future. In this case the cstrDt is in the future.
 * 	- an instant constraint. Just happened. The cstrDt is equal to the creationDt.
 * 
 * In some case this constraints will help control the processing of entity data. If for example an entity is frozen, the
 * corresponding constraint can prevent any party from updating the entity.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public class AbstractEntityCstr extends AbstractEntity {

	private static final long serialVersionUID = 7019202452972427220L;

	@Column
	@NotNull
	private String entIdentif;

	@Temporal(TemporalType.TIMESTAMP)
	@NotNull
	private Date cstrDt;

	/*
	 * The type of constraint. See @StandardCstr
	 */
	@Column
	@NotNull
	private String cstrType;

	/*
	 * Addtional detail. For example when the constrain applies to a member. The member name.
	 */
	@Column
	private String cstrDetail;

	@Column
	private String cstrValue;

	@Column
	private String creatingUsr;

	@Temporal(TemporalType.TIMESTAMP)
	private Date creationDt;
	
	public void makeId(boolean unique){
		if(cstrDt==null) cstrDt= new Date();
		if(unique){
			setId(entIdentif +"_"+ cstrType);
		} else {
			setId(entIdentif +"_"+ cstrType + "_" + FormatedValidFrom.format(cstrDt));
		}
	}
	

	public String getEntIdentif() {
		return entIdentif;
	}

	public void setEntIdentif(String entIdentif) {
		this.entIdentif = entIdentif;
	}

	public Date getCstrDt() {
		return cstrDt;
	}

	public void setCstrDt(Date cstrDt) {
		this.cstrDt = cstrDt;
	}

	public String getCstrType() {
		return cstrType;
	}

	public void setCstrType(String cstrType) {
		this.cstrType = cstrType;
	}

	public String getCstrDetail() {
		return cstrDetail;
	}

	public void setCstrDetail(String cstrDetail) {
		this.cstrDetail = cstrDetail;
	}

	public String getCstrValue() {
		return cstrValue;
	}

	public void setCstrValue(String cstrValue) {
		this.cstrValue = cstrValue;
	}

	public String getCreatingUsr() {
		return creatingUsr;
	}

	public void setCreatingUsr(String creatingUsr) {
		this.creatingUsr = creatingUsr;
	}

	public Date getCreationDt() {
		return creationDt;
	}

	public void setCreationDt(Date creationDt) {
		this.creationDt = creationDt;
	}
}