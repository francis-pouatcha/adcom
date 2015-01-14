package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.rest.BpPtnrCtgryDtlsEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrCtgryDtlsLoader extends AbstractObjectLoader<BpPtnrCtgryDtls> {

	@Inject
	private BpPtnrCtgryDtlsEJB ejb;
	
	@Override
	protected BpPtnrCtgryDtls newObject() {
		return new BpPtnrCtgryDtls();
	}

	@Override
	protected BpPtnrCtgryDtls findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpPtnrCtgryDtls entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpPtnrCtgryDtls entity) {
		ejb.update(entity);
	}
}
