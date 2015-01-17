package org.adorsys.adsales.jpa;

import javax.persistence.Entity;

import org.adorsys.adcore.jpa.DynEnum;
import org.adorsys.javaext.description.Description;

@Entity
@Description("SlsSalesStatus_description")
public class SlsSalesStatus extends DynEnum {
	private static final long serialVersionUID = 930523344272497950L;
}