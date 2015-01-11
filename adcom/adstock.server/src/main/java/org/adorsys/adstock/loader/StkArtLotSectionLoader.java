package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkArtLotSection;
import org.adorsys.adstock.rest.StkArtLotSectionEJB;

@Stateless
public class StkArtLotSectionLoader extends AbstractObjectLoader<StkArtLotSection> {

	@Inject
	private StkArtLotSectionEJB ejb;
	
	@Override
	protected StkArtLotSection newObject() {
		return new StkArtLotSection();
	}

	@Override
	protected StkArtLotSection findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(StkArtLotSection entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(StkArtLotSection entity) {
		ejb.update(entity);
	}
}
