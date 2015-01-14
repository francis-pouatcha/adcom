package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrContact;
import org.adorsys.adbnsptnr.rest.BpPtnrContactEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrContactLoader extends AbstractObjectLoader<BpPtnrContact> {

	@Inject
	private BpPtnrContactEJB ejb;
	
	@Override
	protected BpPtnrContact newObject() {
		return new BpPtnrContact();
	}

	@Override
	protected BpPtnrContact findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpPtnrContact entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpPtnrContact entity) {
		ejb.update(entity);
	}
}
