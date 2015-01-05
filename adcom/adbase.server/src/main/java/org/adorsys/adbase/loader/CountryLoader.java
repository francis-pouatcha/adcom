package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.rest.CountryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CountryLoader extends AbstractObjectLoader<Country> {

	@Inject
	private CountryEJB ejb;
	
	@Override
	protected Country newObject() {
		return new Country();
	}

	@Override
	protected Country findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(Country entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(Country entity) {
		ejb.update(entity);
	}
}
