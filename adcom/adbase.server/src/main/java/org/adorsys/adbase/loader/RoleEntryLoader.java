package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.rest.RoleEntryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class RoleEntryLoader extends AbstractObjectLoader<RoleEntry> {

	@Inject
	private RoleEntryEJB ejb;
	
	@Override
	protected RoleEntry newObject() {
		return new RoleEntry();
	}

	@Override
	protected RoleEntry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(RoleEntry entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(RoleEntry entity) {
		ejb.update(entity);
	}
}
