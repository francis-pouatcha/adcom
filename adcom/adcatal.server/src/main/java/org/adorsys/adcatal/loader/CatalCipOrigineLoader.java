package org.adorsys.adcatal.loader;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.adorsys.adcatal.rest.CatalCipOrigineEJB;
import org.adorsys.adcore.xls.AbstractEnumLoader;

@Stateless
public class CatalCipOrigineLoader extends AbstractEnumLoader<CatalCipOrigine> {

	@Inject
	private CatalCipOrigineEJB ejb;

	@Override
	protected CatalCipOrigine newObject() {
		return new CatalCipOrigine();
	}

	public CatalCipOrigine findByIdentif(String identif) {
		return ejb.findByIdentif(identif);
	}

	public CatalCipOrigine create(CatalCipOrigine entity) {
		return ejb.create(entity);
	}

	public CatalCipOrigine update(CatalCipOrigine found) {
		return ejb.update(found);
	}

	public CatalCipOrigine deleteById(String id) {
		return ejb.deleteById(id);
	}
}
