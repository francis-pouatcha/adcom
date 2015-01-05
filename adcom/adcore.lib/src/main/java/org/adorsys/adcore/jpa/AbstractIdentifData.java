package org.adorsys.adcore.jpa;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;

/**
 * Data records that can be identified by one or mamy of their field.
 * 
 * @author francis
 *
 */
@MappedSuperclass
public abstract class AbstractIdentifData extends AbstractEntity {

	private static final long serialVersionUID = -6116613863022444415L;

	@Column
	@NotNull
	private String identif;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(getIdentif());
	}

	public String getIdentif() {
		if (StringUtils.isBlank(identif)) {
			identif = makeIdentif();
		}
		return identif;
	}

	public void setIdentif(String identif) {
		this.identif = identif;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + " [identif=" + getIdentif() + "]";
	}

	protected abstract String makeIdentif();
}