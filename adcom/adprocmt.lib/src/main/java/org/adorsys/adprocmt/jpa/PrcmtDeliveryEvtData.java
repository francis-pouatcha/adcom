package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtDelivery_description")
public class PrcmtDeliveryEvtData extends PrcmtAbstractDelivery {
	private static final long serialVersionUID = -644543225855706107L;
}