package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.rest.SecTermRegistEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class SecTermRegistLoader extends AbstractObjectLoader<SecTermRegist> {

	@Inject
	private SecTermRegistEJB ejb;
	
	@Override
	protected SecTermRegist newObject() {
		return new SecTermRegist();
	}

	@Override
	protected SecTermRegist findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(SecTermRegist entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(SecTermRegist entity) {
		ejb.update(entity);
	}
}
