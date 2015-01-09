package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.rest.CatalArtFeatMappingEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtFeatMappingLoader extends AbstractObjectLoader<CatalArtFeatMapping> {

	@Inject
	private CatalArtFeatMappingEJB ejb;
	
	@Override
	protected CatalArtFeatMapping newObject() {
		return new CatalArtFeatMapping();
	}

	@Override
	protected CatalArtFeatMapping findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalArtFeatMapping entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalArtFeatMapping entity) {
		ejb.update(entity);
	}
}
