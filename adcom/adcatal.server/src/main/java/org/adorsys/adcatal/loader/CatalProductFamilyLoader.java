package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.rest.CatalProductFamilyEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalProductFamilyLoader extends AbstractObjectLoader<CatalProductFamily> {

	@Inject
	private CatalProductFamilyEJB ejb;
	
	@Override
	protected CatalProductFamily newObject() {
		return new CatalProductFamily();
	}

	@Override
	protected CatalProductFamily findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalProductFamily entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalProductFamily entity) {
		ejb.update(entity);
	}
}
