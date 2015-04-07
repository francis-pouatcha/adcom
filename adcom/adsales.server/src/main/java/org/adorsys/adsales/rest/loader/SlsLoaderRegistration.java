package org.adorsys.adsales.rest.loader;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.time.DateUtils;

@Startup
@Singleton
public class SlsLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private SlsSalesOrderLoader salesOrderLoader;
	@Inject
	private SlsSOItemLoader soItemLoader;
	
	@Inject
	private SlsSalesOrderManagerClient salesOrderManagerClient;
	
	private Date firstCall = new Date();
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(SlsSalesOrderExcel.class.getSimpleName(), salesOrderLoader);
		dataSheetLoader.registerLoader(SlsSOItemExcel.class.getSimpleName(), soItemLoader);
		createTemplate();
	}

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		// only start 10 mins after server start.
		Date now = new Date();
		if(now.before(DateUtils.addMinutes(firstCall, 10))) return;
		
		dataSheetLoader.process();
	}

	@Schedule(minute = "*/2", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void doSale() throws Exception {
		salesOrderManagerClient.doSale();
	}
	
	public void createTemplate(){
	}
	
}
