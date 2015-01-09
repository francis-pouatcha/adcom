package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.rest.CatalFamilyFeatMapingEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalFamilyFeatMapingLoader extends AbstractObjectLoader<CatalFamilyFeatMaping> {

	@Inject
	private CatalFamilyFeatMapingEJB ejb;
	
	@Override
	protected CatalFamilyFeatMaping newObject() {
		return new CatalFamilyFeatMaping();
	}

	@Override
	protected CatalFamilyFeatMaping findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalFamilyFeatMaping entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalFamilyFeatMaping entity) {
		ejb.update(entity);
	}
}
