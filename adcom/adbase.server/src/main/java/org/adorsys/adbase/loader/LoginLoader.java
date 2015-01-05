package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.rest.LoginEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class LoginLoader extends AbstractObjectLoader<Login> {

	@Inject
	private LoginEJB ejb;
	
	@Override
	protected Login newObject() {
		return new Login();
	}

	@Override
	protected Login findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(Login entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(Login entity) {
		ejb.update(entity);
	}
}
