package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.rest.UserWorkspaceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class UserWorkspaceLoader extends AbstractObjectLoader<UserWorkspace> {

	@Inject
	private UserWorkspaceEJB ejb;
	
	@Override
	protected UserWorkspace newObject() {
		return new UserWorkspace();
	}

	@Override
	protected UserWorkspace findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(UserWorkspace entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(UserWorkspace entity) {
		ejb.update(entity);
	}
}
