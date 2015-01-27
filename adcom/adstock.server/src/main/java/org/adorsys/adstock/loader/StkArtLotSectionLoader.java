package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkArtLotSection;
import org.adorsys.adstock.rest.StkArtLotSectionEJB;

@Stateless
public class StkArtLotSectionLoader extends
		AbstractObjectLoader<StkArtLotSection> {

	@Inject
	private StkArtLotSectionEJB ejb;

	@Override
	protected StkArtLotSection newObject() {
		return new StkArtLotSection();
	}

	public StkArtLotSection findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public StkArtLotSection create(StkArtLotSection entity) {
		return ejb.create(entity);
	}

	public StkArtLotSection update(StkArtLotSection found) {
		return ejb.update(found);
	}

	public StkArtLotSection deleteById(String id) {
		return ejb.deleteById(id);
	}

}
