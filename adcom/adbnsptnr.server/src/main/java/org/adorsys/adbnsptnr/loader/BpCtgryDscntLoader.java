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

	public BpCtgryDscnt findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpCtgryDscnt create(BpCtgryDscnt entity) {
		return ejb.create(entity);
	}

	public BpCtgryDscnt update(BpCtgryDscnt found) {
		return ejb.update(found);
	}

	public BpCtgryDscnt deleteById(String id) {
		return ejb.deleteById(id);
	}

}
