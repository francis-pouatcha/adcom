package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.rest.BpPtnrCtgryEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrCtgryLoader extends AbstractObjectLoader<BpPtnrCtgry> {

	@Inject
	private BpPtnrCtgryEJB ejb;
	
	@Override
	protected BpPtnrCtgry newObject() {
		return new BpPtnrCtgry();
	}

	@Override
	protected BpPtnrCtgry findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpPtnrCtgry entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpPtnrCtgry entity) {
		ejb.update(entity);
	}
}
