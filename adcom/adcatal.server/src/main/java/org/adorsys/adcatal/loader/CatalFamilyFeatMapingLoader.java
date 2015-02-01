package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.rest.CatalFamilyFeatMapingEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalFamilyFeatMapingLoader extends
		AbstractObjectLoader<CatalFamilyFeatMaping> {

	@Inject
	private CatalFamilyFeatMapingEJB ejb;

	@Override
	protected CatalFamilyFeatMaping newObject() {
		return new CatalFamilyFeatMaping();
	}

	public CatalFamilyFeatMaping findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif);
	}

	public CatalFamilyFeatMaping create(CatalFamilyFeatMaping entity) {
		return ejb.create(entity);
	}

	public CatalFamilyFeatMaping update(CatalFamilyFeatMaping found) {
		return ejb.update(found);
	}

	public CatalFamilyFeatMaping deleteById(String id) {
		CatalFamilyFeatMaping featMaping = ejb.findById(id);
		if(featMaping==null) return null;
		ejb.deleteByPfIdentif(featMaping.getPfIdentif());
		return featMaping;
	}

}
