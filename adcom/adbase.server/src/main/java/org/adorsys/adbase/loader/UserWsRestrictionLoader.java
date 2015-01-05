package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.rest.UserWsRestrictionEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class UserWsRestrictionLoader extends AbstractObjectLoader<UserWsRestriction> {

	@Inject
	private UserWsRestrictionEJB ejb;
	
	@Override
	protected UserWsRestriction newObject() {
		return new UserWsRestriction();
	}

	@Override
	protected UserWsRestriction findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(UserWsRestriction entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(UserWsRestriction entity) {
		ejb.update(entity);
	}
}
