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

	@Override
	protected StkArticleLot findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(StkArticleLot entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(StkArticleLot entity) {
		ejb.update(entity);
	}
}
