package org.adorsys.adbnsptnr.loader;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;

import org.adorsys.adcore.xls.XlsConverterFactory;

@Startup
@Singleton
public class BaseConverterRegistration {

	@Inject
	private BpPtnrContactRoleConverter bpPtnrContactRoleConverter;

	@Inject
	private BpPtnrIdTypeConverter bpPtnrIdTypeConverter;

	@Inject
	private BpPtnrRoleConverter bpPtnrRoleConverter;

	@Inject
	private BpPtnrTypeConverter bpPtnrTypeConverter;

	@Inject
	private XlsConverterFactory converterFactory;
	
	@PostConstruct
	public void postConstruct(){
		converterFactory.registerConverter(BpPtnrContactRoleConverter.class.getName(), bpPtnrContactRoleConverter);
		converterFactory.registerConverter(BpPtnrIdTypeConverter.class.getName(), bpPtnrIdTypeConverter);
		converterFactory.registerConverter(BpPtnrRoleConverter.class.getName(), bpPtnrRoleConverter);
		converterFactory.registerConverter(BpPtnrTypeConverter.class.getName(), bpPtnrTypeConverter);
	}
}
