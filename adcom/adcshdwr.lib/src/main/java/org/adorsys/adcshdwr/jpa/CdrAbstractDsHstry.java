package org.adorsys.adcshdwr.jpa;

import javax.persistence.MappedSuperclass;

import org.adorsys.adcore.jpa.AbstractEntityHistory;
import org.adorsys.javaext.description.Description;

@MappedSuperclass
@Description("CdrDsHstry_description")
public class CdrAbstractDsHstry extends AbstractEntityHistory {
	private static final long serialVersionUID = 6527110988294588752L;
}