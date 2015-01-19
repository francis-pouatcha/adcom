package org.adorsys.adbnsptnr.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.rest.BpIndivPtnrNameEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class BpIndivPtnrNameLoader extends
		AbstractObjectLoader<BpIndivPtnrName> {

	@Inject
	private BpIndivPtnrNameEJB ejb;

	@Override
	protected BpIndivPtnrName newObject() {
		return new BpIndivPtnrName();
	}

	public BpIndivPtnrName findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public BpIndivPtnrName create(BpIndivPtnrName entity) {
		return ejb.create(entity);
	}

	public BpIndivPtnrName update(BpIndivPtnrName found) {
		return ejb.update(found);
	}

	public BpIndivPtnrName deleteById(String id) {
		return ejb.deleteById(id);
	}

}
