package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.rest.CatalArtDetailConfigEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtDetailConfigLoader extends AbstractObjectLoader<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigEJB ejb;
	
	@Override
	protected CatalArtDetailConfig newObject() {
		return new CatalArtDetailConfig();
	}

	@Override
	protected CatalArtDetailConfig findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalArtDetailConfig entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalArtDetailConfig entity) {
		ejb.update(entity);
	}
}
