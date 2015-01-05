package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.rest.SecTerminalEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class SecTerminalLoader extends AbstractObjectLoader<SecTerminal> {

	@Inject
	private SecTerminalEJB ejb;
	
	@Override
	protected SecTerminal newObject() {
		return new SecTerminal();
	}

	@Override
	protected SecTerminal findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(SecTerminal entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(SecTerminal entity) {
		ejb.update(entity);
	}
}
