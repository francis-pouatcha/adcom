package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.rest.WorkspaceRestrictionEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class WorkspaceRestrictionLoader extends AbstractObjectLoader<WorkspaceRestriction> {

	@Inject
	private WorkspaceRestrictionEJB ejb;
	
	@Override
	protected WorkspaceRestriction newObject() {
		return new WorkspaceRestriction();
	}

	@Override
	protected WorkspaceRestriction findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(WorkspaceRestriction entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(WorkspaceRestriction entity) {
		ejb.update(entity);
	}
}
