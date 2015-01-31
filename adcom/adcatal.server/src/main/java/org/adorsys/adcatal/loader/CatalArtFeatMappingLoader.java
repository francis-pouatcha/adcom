package org.adorsys.adcatal.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.rest.CatalArtFeatMappingEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class CatalArtFeatMappingLoader extends
		AbstractObjectLoader<CatalArtFeatMapping> {

	@Inject
	private CatalArtFeatMappingEJB ejb;

	@Override
	protected CatalArtFeatMapping newObject() {
		return new CatalArtFeatMapping();
	}

	public CatalArtFeatMapping findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif);
	}

	public CatalArtFeatMapping create(CatalArtFeatMapping entity) {
		return ejb.create(entity);
	}

	public CatalArtFeatMapping update(CatalArtFeatMapping found) {
		return ejb.update(found);
	}

	public CatalArtFeatMapping deleteById(String id) {
		CatalArtFeatMapping featMapping = ejb.findById(id);
		if(featMapping!=null) ejb.deleteByPic(featMapping.getArtIdentif());
		return featMapping;
	}

}
