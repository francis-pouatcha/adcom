package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.rest.ConverterCurrRateEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class ConverterCurrRateLoader extends AbstractObjectLoader<ConverterCurrRate> {

	@Inject
	private ConverterCurrRateEJB ejb;
	
	@Override
	protected ConverterCurrRate newObject() {
		return new ConverterCurrRate();
	}

	@Override
	protected ConverterCurrRate findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(ConverterCurrRate entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(ConverterCurrRate entity) {
		ejb.update(entity);
	}
}
