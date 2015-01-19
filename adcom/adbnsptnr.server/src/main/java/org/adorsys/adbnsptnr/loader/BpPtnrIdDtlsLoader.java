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

	public BpPtnrIdDtls findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpPtnrIdDtls create(BpPtnrIdDtls entity) {
		return ejb.create(entity);
	}

	public BpPtnrIdDtls update(BpPtnrIdDtls found) {
		return ejb.update(found);
	}

	public BpPtnrIdDtls deleteById(String id) {
		return ejb.deleteById(id);
	}

}
