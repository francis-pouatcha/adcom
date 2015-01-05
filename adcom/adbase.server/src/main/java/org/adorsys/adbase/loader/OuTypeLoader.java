package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.rest.OuTypeEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OuTypeLoader extends AbstractObjectLoader<OuType> {

	@Inject
	private OuTypeEJB ejb;
	
	@Override
	protected OuType newObject() {
		return new OuType();
	}

	@Override
	protected OuType findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(OuType entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(OuType entity) {
		ejb.update(entity);
	}
}
