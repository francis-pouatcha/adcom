package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;
import javax.persistence.PrePersist;

import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtProcOrder_description")
public class PrcmtProcOrder extends PrcmtAbstractProcOrder {
	private static final long serialVersionUID = 5194929467038747949L;

	@PrePersist
	public void prePersist() {
		setId(getPoNbr());
	}

}