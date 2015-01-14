package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrIdDtls;
import org.adorsys.adbnsptnr.rest.BpPtnrIdDtlsEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrIdDtlsLoader extends AbstractObjectLoader<BpPtnrIdDtls> {

	@Inject
	private BpPtnrIdDtlsEJB ejb;
	
	@Override
	protected BpPtnrIdDtls newObject() {
		return new BpPtnrIdDtls();
	}

	@Override
	protected BpPtnrIdDtls findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpPtnrIdDtls entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpPtnrIdDtls entity) {
		ejb.update(entity);
	}
}
