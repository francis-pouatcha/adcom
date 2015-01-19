package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.rest.CatalArtManufSuppEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtManufSuppLoader extends
		AbstractObjectLoader<CatalArtManufSupp> {

	@Inject
	private CatalArtManufSuppEJB ejb;

	@Override
	protected CatalArtManufSupp newObject() {
		return new CatalArtManufSupp();
	}

	public CatalArtManufSupp findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public CatalArtManufSupp create(CatalArtManufSupp entity) {
		return ejb.create(entity);
	}

	public CatalArtManufSupp update(CatalArtManufSupp found) {
		return ejb.update(found);
	}

	public CatalArtManufSupp deleteById(String id) {
		return ejb.deleteById(id);
	}

}
