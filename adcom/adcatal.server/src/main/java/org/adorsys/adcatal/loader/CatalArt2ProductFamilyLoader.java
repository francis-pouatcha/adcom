package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.rest.CatalArt2ProductFamilyEJB;
import org.adorsys.adcatal.rest.CatalProductFamilyEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArt2ProductFamilyLoader extends AbstractObjectLoader<CatalArt2ProductFamily> {

	@Inject
	private CatalArt2ProductFamilyEJB ejb;
	
	@Inject
	private CatalProductFamilyEJB productFamilyEJB;

	@Override
	protected CatalArt2ProductFamily newObject() {
		return new CatalArt2ProductFamily();
	}

	public CatalArt2ProductFamily findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif);
	}

	public CatalArt2ProductFamily create(CatalArt2ProductFamily entity) {
		productFamilyEJB.addProductFamilyMapping(entity.getArtPic(), entity.getFamCode());
		return entity;
	}

	public CatalArt2ProductFamily update(CatalArt2ProductFamily found) {
		return found;
//		return ejb.update(found);
	}

	public CatalArt2ProductFamily deleteById(String id) {
		CatalArt2ProductFamily article = ejb.findById(id);
		if(article!=null)
			ejb.deleteById(article.getId());
		return article;
	}

}
