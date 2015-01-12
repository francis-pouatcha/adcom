package org.adorsys.adstock.loader;

import javax.ejb.Stateless;

import org.adorsys.adcore.xls.AbstractObjectConverter;
import org.adorsys.adstock.jpa.StkInvtryStatus;

@Stateless
public class StkInvtryStatusConverter extends AbstractObjectConverter<StkInvtryStatus> {
	@Override
	protected StkInvtryStatus parse(String val) {
		return StkInvtryStatus.valueOf(val);
	}
}
