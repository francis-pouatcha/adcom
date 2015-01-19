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

	public SecTermRegist findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public SecTermRegist create(SecTermRegist entity) {
		return ejb.create(entity);
	}

	public SecTermRegist update(SecTermRegist found) {
		return ejb.update(found);
	}

	public SecTermRegist deleteById(String id) {
		return ejb.deleteById(id);
	}

}
