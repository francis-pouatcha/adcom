package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.adorsys.adcatal.rest.CatalPkgModeEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class CatalPkgModeLoader extends AbstractEnumLoader<CatalPkgMode> {

	@Inject
	private CatalPkgModeEJB ejb;

	@Override
	protected CatalPkgMode newObject() {
		return new CatalPkgMode();
	}

	public CatalPkgMode findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public CatalPkgMode create(CatalPkgMode entity) {
		return ejb.create(entity);
	}

	public CatalPkgMode update(CatalPkgMode found) {
		return ejb.update(found);
	}

	public CatalPkgMode deleteById(String id) {
		return ejb.deleteById(id);
	}
}
