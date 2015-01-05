package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.rest.OuWsRestrictionEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OuWsRestrictionLoader extends AbstractObjectLoader<OuWsRestriction> {

	@Inject
	private OuWsRestrictionEJB ejb;
	
	@Override
	protected OuWsRestriction newObject() {
		return new OuWsRestriction();
	}

	@Override
	protected OuWsRestriction findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(OuWsRestriction entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(OuWsRestriction entity) {
		ejb.update(entity);
	}
}
