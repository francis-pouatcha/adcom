package org.adorsys.adcore.jpa;

import java.util.UUID;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.StringUtils;

@MappedSuperclass
public abstract class AbstractMvmtData extends AbstractEntity {

	private static final long serialVersionUID = -8592023482340812797L;

	@PrePersist
	public void prePersist() {
		if (StringUtils.isBlank(getId()))
			setId(UUID.randomUUID().toString());
	}
}