package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractEntityHistory;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrVchrHstry_description")
public class CdrVchrHstry extends AbstractEntityHistory {
	private static final long serialVersionUID = -6477744212419272347L;
}