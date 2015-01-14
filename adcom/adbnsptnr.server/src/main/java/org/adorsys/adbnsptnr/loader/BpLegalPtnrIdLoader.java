package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.rest.BpLegalPtnrIdEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpLegalPtnrIdLoader extends AbstractObjectLoader<BpLegalPtnrId> {

	@Inject
	private BpLegalPtnrIdEJB ejb;
	
	@Override
	protected BpLegalPtnrId newObject() {
		return new BpLegalPtnrId();
	}

	@Override
	protected BpLegalPtnrId findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpLegalPtnrId entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpLegalPtnrId entity) {
		ejb.update(entity);
	}
}
