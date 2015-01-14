package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.adorsys.adbnsptnr.rest.BpCtgryOfPtnrEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpCtgryOfPtnrLoader extends AbstractObjectLoader<BpCtgryOfPtnr> {

	@Inject
	private BpCtgryOfPtnrEJB ejb;
	
	@Override
	protected BpCtgryOfPtnr newObject() {
		return new BpCtgryOfPtnr();
	}

	@Override
	protected BpCtgryOfPtnr findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpCtgryOfPtnr entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpCtgryOfPtnr entity) {
		ejb.update(entity);
	}
}
