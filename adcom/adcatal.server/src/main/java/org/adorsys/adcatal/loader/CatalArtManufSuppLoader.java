package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.rest.CatalArtManufSuppEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtManufSuppLoader extends AbstractObjectLoader<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppEJB ejb;
	
	@Override
	protected CatalArtManufSupp newObject() {
		return new CatalArtManufSupp();
	}

	@Override
	protected CatalArtManufSupp findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalArtManufSupp entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalArtManufSupp entity) {
		ejb.update(entity);
	}
}
