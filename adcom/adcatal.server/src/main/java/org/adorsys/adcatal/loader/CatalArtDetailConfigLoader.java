package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.rest.CatalArtDetailConfigEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtDetailConfigLoader extends
		AbstractObjectLoader<CatalArtDetailConfig> {

	@Inject
	private CatalArtDetailConfigEJB ejb;

	@Override
	protected CatalArtDetailConfig newObject() {
		return new CatalArtDetailConfig();
	}

	public CatalArtDetailConfig findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public CatalArtDetailConfig create(CatalArtDetailConfig entity) {
		return ejb.create(entity);
	}

	public CatalArtDetailConfig update(CatalArtDetailConfig found) {
		return ejb.update(found);
	}

	public CatalArtDetailConfig deleteById(String id) {
		return ejb.deleteById(id);
	}

}
