package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpPtnrCreditDtls;
import org.adorsys.adbnsptnr.rest.BpPtnrCreditDtlsEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpPtnrCreditDtlsLoader extends AbstractObjectLoader<BpPtnrCreditDtls> {

	@Inject
	private BpPtnrCreditDtlsEJB ejb;
	
	@Override
	protected BpPtnrCreditDtls newObject() {
		return new BpPtnrCreditDtls();
	}

	@Override
	protected BpPtnrCreditDtls findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpPtnrCreditDtls entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpPtnrCreditDtls entity) {
		ejb.update(entity);
	}
}
