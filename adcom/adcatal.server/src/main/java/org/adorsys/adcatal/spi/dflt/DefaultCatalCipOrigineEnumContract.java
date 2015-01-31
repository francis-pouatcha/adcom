package org.adorsys.adcatal.spi.dflt;

import javax.ejb.Stateless;

import org.adorsys.adcatal.rest.CatalCipOrigineEnumContract;

@Stateless
public class DefaultCatalCipOrigineEnumContract implements CatalCipOrigineEnumContract{

	@Override
	public String getMain() {
		return CatalCipOrigineEnum.MAIN.name();
	}

}
