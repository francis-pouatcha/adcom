package org.adorsys.adprocmt.loader;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

@Startup
@Singleton
public class BaseLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private PrcmtDeliveryLoader prcmtDeliveryLoader;
	@Inject
	private PrcmtDlvryItemLoader prcmtDlvryItemLoader;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(PrcmtDeliveryExcel.class.getSimpleName(), prcmtDeliveryLoader);
		dataSheetLoader.registerLoader(PrcmtDlvryItemExcel.class.getSimpleName(), prcmtDlvryItemLoader);
		createTemplate();
	}

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		dataSheetLoader.process();
	}
	
	public void createTemplate(){
	}
	
}
