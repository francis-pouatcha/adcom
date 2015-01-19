package org.adorsys.adbase.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.DynEnum;
import org.adorsys.javaext.description.Description;

@Entity
@Description("BaseProcessStatus_description")
public class BaseProcessStatus extends DynEnum {
	private static final long serialVersionUID = 652055187511281118L;
}