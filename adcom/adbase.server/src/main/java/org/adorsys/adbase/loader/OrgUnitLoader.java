package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.rest.OrgUnitEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OrgUnitLoader extends AbstractObjectLoader<OrgUnit> {

	@Inject
	private OrgUnitEJB ejb;
	
	@Override
	protected OrgUnit newObject() {
		return new OrgUnit();
	}

	@Override
	protected OrgUnit findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(OrgUnit entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(OrgUnit entity) {
		ejb.update(entity);
	}
}
