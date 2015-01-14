package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.rest.BpBnsPtnrEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpBnsPtnrLoader extends AbstractObjectLoader<BpBnsPtnr> {

	@Inject
	private BpBnsPtnrEJB ejb;
	
	@Override
	protected BpBnsPtnr newObject() {
		return new BpBnsPtnr();
	}

	@Override
	protected BpBnsPtnr findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpBnsPtnr entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpBnsPtnr entity) {
		ejb.update(entity);
	}
}
