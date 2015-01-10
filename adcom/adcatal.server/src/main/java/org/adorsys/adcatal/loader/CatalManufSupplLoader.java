package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalManufSuppl;
import org.adorsys.adcatal.rest.CatalManufSupplEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalManufSupplLoader extends AbstractObjectLoader<CatalManufSuppl> {

	@Inject
	private CatalManufSupplEJB ejb;
	
	@Override
	protected CatalManufSuppl newObject() {
		return new CatalManufSuppl();
	}

	@Override
	protected CatalManufSuppl findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalManufSuppl entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalManufSuppl entity) {
		ejb.update(entity);
	}
}
