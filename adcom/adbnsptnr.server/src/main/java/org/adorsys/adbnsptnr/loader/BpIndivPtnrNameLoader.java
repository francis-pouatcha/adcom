package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.rest.BpIndivPtnrNameEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpIndivPtnrNameLoader extends AbstractObjectLoader<BpIndivPtnrName> {

	@Inject
	private BpIndivPtnrNameEJB ejb;
	
	@Override
	protected BpIndivPtnrName newObject() {
		return new BpIndivPtnrName();
	}

	@Override
	protected BpIndivPtnrName findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(BpIndivPtnrName entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(BpIndivPtnrName entity) {
		ejb.update(entity);
	}
}
