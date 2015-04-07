package org.adorsys.adsales.rest.loader;

import javax.ejb.Stateless;

import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adcore.xls.AbstractObjectConverter;

@Stateless
public class BaseProcessStatusConverter extends AbstractObjectConverter<BaseProcessStatusEnum> {
	@Override
	protected BaseProcessStatusEnum parse(String val) {
		return BaseProcessStatusEnum.valueOf(val);
	}
}
