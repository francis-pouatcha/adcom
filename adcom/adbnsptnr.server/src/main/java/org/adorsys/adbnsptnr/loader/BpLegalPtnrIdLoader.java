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

	public BpLegalPtnrId findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpLegalPtnrId create(BpLegalPtnrId entity) {
		return ejb.create(entity);
	}

	public BpLegalPtnrId update(BpLegalPtnrId found) {
		return ejb.update(found);
	}

	public BpLegalPtnrId deleteById(String id) {
		return ejb.deleteById(id);
	}

}
