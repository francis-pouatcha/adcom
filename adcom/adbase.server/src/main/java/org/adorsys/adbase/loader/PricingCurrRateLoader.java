package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.rest.PricingCurrRateEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class PricingCurrRateLoader extends AbstractObjectLoader<PricingCurrRate> {

	@Inject
	private PricingCurrRateEJB ejb;
	
	@Override
	protected PricingCurrRate newObject() {
		return new PricingCurrRate();
	}

	@Override
	protected PricingCurrRate findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(PricingCurrRate entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(PricingCurrRate entity) {
		ejb.update(entity);
	}
}
