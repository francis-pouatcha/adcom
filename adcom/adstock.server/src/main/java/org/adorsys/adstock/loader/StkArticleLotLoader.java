package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.rest.StkArticleLotEJB;

@Stateless
public class StkArticleLotLoader extends AbstractObjectLoader<StkArticleLot> {

	@Inject
	private StkArticleLotEJB ejb;

	@Override
	protected StkArticleLot newObject() {
		return new StkArticleLot();
	}

	public StkArticleLot findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public StkArticleLot create(StkArticleLot entity) {
		return ejb.create(entity);
	}

	public StkArticleLot update(StkArticleLot found) {
		return ejb.update(found);
	}

	public StkArticleLot deleteById(String id) {
		return ejb.deleteById(id);
	}

}
