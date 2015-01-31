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

	public CatalArticle findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	public CatalArticle create(CatalArticle entity) {
		return ejb.create(entity);
	}

	public CatalArticle update(CatalArticle found) {
		return ejb.update(found);
	}

	public CatalArticle deleteById(String id) {
		CatalArticle article = ejb.findById(id);
		if(article!=null)
		ejb.deleteByPic(article.getPic());
		return article;
	}

}
