package org.adorsys.adstock.loader;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcore.xls.AbstractObjectLoader;
import org.adorsys.adstock.jpa.StkInvtryItem;
import org.adorsys.adstock.rest.StkInvtryItemEJB;

@Stateless
public class StkInvtryItemLoader extends AbstractObjectLoader<StkInvtryItem> {

	@Inject
	private StkInvtryItemEJB ejb;
	
	@Override
	protected StkInvtryItem newObject() {
		return new StkInvtryItem();
	}

	@Override
	protected StkInvtryItem findByIdentif(String identif, Date validOn) {
		return ejb.findByIdentif(identif, validOn);
	}

	@Override
	protected void create(StkInvtryItem entity) {
		ejb.create(entity);
	}

	@Override
	protected void update(StkInvtryItem entity) {
		ejb.update(entity);
	}
}
