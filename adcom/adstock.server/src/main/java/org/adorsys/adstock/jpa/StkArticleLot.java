package org.adorsys.adstock.jpa;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Transient;

import org.adorsys.javaext.description.Description;

@Entity
@Description("StkArticleLot_description")
public class StkArticleLot extends StkAbstractArticleLot {
	private static final long serialVersionUID = -4912132065652417884L;

	@Transient
	private List<StkArticleLot2StrgSctn> strgSctns = new ArrayList<StkArticleLot2StrgSctn>();

	public List<StkArticleLot2StrgSctn> getStrgSctns() {
		return strgSctns;
	}

	public void setStrgSctns(List<StkArticleLot2StrgSctn> strgSctns) {
		this.strgSctns = strgSctns;
	}
	
}