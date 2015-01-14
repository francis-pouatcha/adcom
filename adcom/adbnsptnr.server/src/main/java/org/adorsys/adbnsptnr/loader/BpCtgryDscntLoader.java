package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpCtgryDscnt;
import org.adorsys.adbnsptnr.rest.BpCtgryDscntEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpCtgryDscntLoader extends AbstractObjectLoader<BpCtgryDscnt> {

	@Inject
	private BpCtgryDscntEJB ejb;
	
	@Override
	protected BpCtgryDscnt newObject() {
		return new BpCtgryDscnt();
	}

	@Override
	protected BpCtgryDscnt findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpCtgryDscnt entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpCtgryDscnt entity) {
		ejb.update(entity);
	}
}
