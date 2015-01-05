package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.rest.PermEntryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class PermEntryLoader extends AbstractObjectLoader<PermEntry> {

	@Inject
	private PermEntryEJB ejb;
	
	@Override
	protected PermEntry newObject() {
		return new PermEntry();
	}

	@Override
	protected PermEntry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(PermEntry entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(PermEntry entity) {
		ejb.update(entity);
	}
}
