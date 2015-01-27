package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractEntityHistory;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrDsHstry_description")
public class CdrDsHstry extends AbstractEntityHistory {
	private static final long serialVersionUID = -4580391223984647280L;
}