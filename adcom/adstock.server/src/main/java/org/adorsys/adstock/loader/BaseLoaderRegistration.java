package org.adorsys.adstock.loader;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Startup
@Singleton
public class BaseLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private StkSectionLoader stkSectionLoader;
	@Inject
	private StkArticleLotLoader stkArticleLotLoader;
	@Inject
	private StkArtLotSectionLoader stkArtLotSectionLoader;
	@Inject
	private StkInvtryItemLoader stkInvtryItemLoader;
	@Inject
	private StkInvtryLoader stkInvtryLoader;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(StkSectionLoader.class.getSimpleName(), stkSectionLoader);
		dataSheetLoader.registerLoader(StkArticleLotLoader.class.getSimpleName(), stkArticleLotLoader);
		dataSheetLoader.registerLoader(StkArtLotSectionLoader.class.getSimpleName(), stkArtLotSectionLoader);
		dataSheetLoader.registerLoader(StkInvtryItemLoader.class.getSimpleName(), stkInvtryItemLoader);
		dataSheetLoader.registerLoader(StkInvtryLoader.class.getSimpleName(), stkInvtryLoader);
		createTemplate();
	}

	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		dataSheetLoader.process();
	}
	
	public void createTemplate(){
		HSSFWorkbook workbook = new HSSFWorkbook();
		stkSectionLoader.createTemplate(workbook);
		stkArticleLotLoader.createTemplate(workbook);
		stkArtLotSectionLoader.createTemplate(workbook);
		stkInvtryItemLoader.createTemplate(workbook);
		stkInvtryLoader.createTemplate(workbook);
		dataSheetLoader.exportTemplate(workbook);
	}
	
}
