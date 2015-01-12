package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkInvtry;
import org.adorsys.adstock.rest.StkInvtryEJB;

@Stateless
public class StkInvtryLoader extends AbstractObjectLoader<StkInvtry> {

	@Inject
	private StkInvtryEJB ejb;
	
	@Override
	protected StkInvtry newObject() {
		return new StkInvtry();
	}

	@Override
	protected StkInvtry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(StkInvtry entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(StkInvtry entity) {
		ejb.update(entity);
	}
}
