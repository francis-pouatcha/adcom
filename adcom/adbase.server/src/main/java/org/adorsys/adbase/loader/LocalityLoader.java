package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.rest.LocalityEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class LocalityLoader extends AbstractObjectLoader<Locality> {

	@Inject
	private LocalityEJB ejb;
	
	@Override
	protected Locality newObject() {
		return new Locality();
	}

	@Override
	protected Locality findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(Locality entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(Locality entity) {
		ejb.update(entity);
	}
}
