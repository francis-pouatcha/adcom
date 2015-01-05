package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.rest.WorkspaceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class WorkspaceLoader extends AbstractObjectLoader<Workspace> {

	@Inject
	private WorkspaceEJB ejb;
	
	@Override
	protected Workspace newObject() {
		return new Workspace();
	}

	@Override
	protected Workspace findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(Workspace entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(Workspace entity) {
		ejb.update(entity);
	}
}
