package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.rest.OuWorkspaceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OuWorkspaceLoader extends AbstractObjectLoader<OuWorkspace> {

	@Inject
	private OuWorkspaceEJB ejb;
	
	@Override
	protected OuWorkspace newObject() {
		return new OuWorkspace();
	}

	@Override
	protected OuWorkspace findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(OuWorkspace entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(OuWorkspace entity) {
		ejb.update(entity);
	}
}
