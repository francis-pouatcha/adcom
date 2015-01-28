package org.adorsys.adbase.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.DynEnum;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BaseHistoryType_description")
public class BaseHistoryType extends DynEnum {
	private static final long serialVersionUID = -2236090494971230797L;
}