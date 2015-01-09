package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.rest.CatalArtEquivalenceEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtEquivalenceLoader extends AbstractObjectLoader<CatalArtEquivalence> {

	@Inject
	private CatalArtEquivalenceEJB ejb;
	
	@Override
	protected CatalArtEquivalence newObject() {
		return new CatalArtEquivalence();
	}

	@Override
	protected CatalArtEquivalence findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalArtEquivalence entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalArtEquivalence entity) {
		ejb.update(entity);
	}
}
