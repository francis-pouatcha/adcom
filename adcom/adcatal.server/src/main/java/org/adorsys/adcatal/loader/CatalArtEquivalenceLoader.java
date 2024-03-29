package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.rest.CatalArtEquivalenceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtEquivalenceLoader extends
		AbstractObjectLoader<CatalArtEquivalence> {

	@Inject
	private CatalArtEquivalenceEJB ejb;

	@Override
	protected CatalArtEquivalence newObject() {
		return new CatalArtEquivalence();
	}

	public CatalArtEquivalence findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public CatalArtEquivalence create(CatalArtEquivalence entity) {
		return ejb.create(entity);
	}

	public CatalArtEquivalence update(CatalArtEquivalence found) {
		return ejb.update(found);
	}

	public CatalArtEquivalence deleteById(String id) {
		return ejb.deleteById(id);
	}

}
