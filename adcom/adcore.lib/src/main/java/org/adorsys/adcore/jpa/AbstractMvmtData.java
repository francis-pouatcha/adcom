package org.adorsys.adcore.jpa;

import java.util.UUID;

import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

@MappedSuperclass
public abstract class AbstractMvmtData extends AbstractEntity {

	private static final long serialVersionUID = -8592023482340812797L;

	@PrePersist
	public void prePersist() {
		if (getId() == null)
			setId(UUID.randomUUID().toString());
	}
}