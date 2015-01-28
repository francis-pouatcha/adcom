package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.rest.CatalProductFamilyEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalProductFamilyLoader extends
		AbstractObjectLoader<CatalProductFamily> {

	@Inject
	private CatalProductFamilyEJB ejb;

	@Override
	protected CatalProductFamily newObject() {
		return new CatalProductFamily();
	}

	public CatalProductFamily findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public CatalProductFamily create(CatalProductFamily entity) {
		return ejb.create(entity);
	}

	public CatalProductFamily update(CatalProductFamily found) {
		return ejb.update(found);
	}

	public CatalProductFamily deleteById(String id) {
		return ejb.deleteById(id);
	}

}
