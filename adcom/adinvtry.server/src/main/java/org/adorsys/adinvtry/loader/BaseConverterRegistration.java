package org.adorsys.adinvtry.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.xls.XlsConverterFactory;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.jpa.InvInvtryType;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private InvInvtryStatusConverter invInvtryStatusConverter;

	@Inject
	private InvInvtryTypeConverter invInvtryTypeConverter;
	
	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(InvInvtryStatus.class.getName(), invInvtryStatusConverter);
		converterFactory.registerConverter(InvInvtryType.class.getName(), invInvtryTypeConverter);
	}
}
