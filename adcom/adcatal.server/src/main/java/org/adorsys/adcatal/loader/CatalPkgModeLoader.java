package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.adorsys.adcatal.rest.CatalPkgModeEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalPkgModeLoader extends AbstractObjectLoader<CatalPkgMode> {

	@Inject
	private CatalPkgModeEJB ejb;
	
	@Override
	protected CatalPkgMode newObject() {
		return new CatalPkgMode();
	}

	@Override
	protected CatalPkgMode findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalPkgMode entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalPkgMode entity) {
		ejb.update(entity);
	}
}
