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

	public SecTerminal findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public SecTerminal create(SecTerminal entity) {
		return ejb.create(entity);
	}

	public SecTerminal update(SecTerminal found) {
		return ejb.update(found);
	}

	public SecTerminal deleteById(String id) {
		return ejb.deleteById(id);
	}

}
