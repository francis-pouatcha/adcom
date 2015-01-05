package org.adorsys.adbase.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.rest.OrgContactEJB;
import org.adorsys.adcore.xls.AbstractObjectLoader;

@Stateless
public class OrgContactLoader extends AbstractObjectLoader<OrgContact> {

	@Inject
	private OrgContactEJB ejb;
	
	@Override
	protected OrgContact newObject() {
		return new OrgContact();
	}

	@Override
	protected OrgContact findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(OrgContact entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(OrgContact entity) {
		ejb.update(entity);
	}
}
