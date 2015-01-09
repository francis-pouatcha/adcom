package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.rest.CatalPicMappingEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalPicMappingLoader extends AbstractObjectLoader<CatalPicMapping> {

	@Inject
	private CatalPicMappingEJB ejb;
	
	@Override
	protected CatalPicMapping newObject() {
		return new CatalPicMapping();
	}

	@Override
	protected CatalPicMapping findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalPicMapping entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalPicMapping entity) {
		ejb.update(entity);
	}
}
