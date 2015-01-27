package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.rest.CatalPicMappingEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalPicMappingLoader extends
		AbstractObjectLoader<CatalPicMapping> {

	@Inject
	private CatalPicMappingEJB ejb;

	@Override
	protected CatalPicMapping newObject() {
		return new CatalPicMapping();
	}

	public CatalPicMapping findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public CatalPicMapping create(CatalPicMapping entity) {
		return ejb.create(entity);
	}

	public CatalPicMapping update(CatalPicMapping found) {
		return ejb.update(found);
	}

	public CatalPicMapping deleteById(String id) {
		return ejb.deleteById(id);
	}

}
