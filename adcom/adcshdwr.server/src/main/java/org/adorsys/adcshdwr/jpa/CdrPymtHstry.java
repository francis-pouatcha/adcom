package org.adorsys.adcshdwr.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.AbstractEntityHistory;
import org.adorsys.javaext.description.Description;

@Entity
@Description("CdrPymtHstry_description")
public class CdrPymtHstry extends AbstractEntityHistory {
	private static final long serialVersionUID = -8329760021728145209L;
}