package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.rest.CatalArticleEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArticleLoader extends AbstractObjectLoader<CatalArticle> {

	@Inject
	private CatalArticleEJB ejb;
	
	@Override
	protected CatalArticle newObject() {
		return new CatalArticle();
	}

	@Override
	protected CatalArticle findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(CatalArticle entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(CatalArticle entity) {
		ejb.update(entity);
	}
}
