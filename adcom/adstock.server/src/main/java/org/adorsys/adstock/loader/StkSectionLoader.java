package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.rest.StkSectionEJB;

@Stateless
public class StkSectionLoader extends AbstractObjectLoader<StkSection> {

	@Inject
	private StkSectionEJB ejb;
	
	@Override
	protected StkSection newObject() {
		return new StkSection();
	}

	@Override
	protected StkSection findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(StkSection entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(StkSection entity) {
		ejb.update(entity);
	}
}
