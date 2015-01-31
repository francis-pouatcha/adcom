package org.adorsys.adcatal.loader;

import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.spi.CatalEnumDataProvider;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

@Startup
@Singleton
public class BaseLoaderRegistration {

	@Inject
	private DataSheetLoader dataSheetLoader;
	@Inject
	private CatalEnumDataProvider catalEnumDataProvider;
	@Inject
	private CatalArtDetailConfigLoader catalArtDetailConfigLoader;
	@Inject
	private CatalArtEquivalenceLoader catalArtEquivalenceLoader;
	@Inject
	private CatalArtFeatMappingLoader catalArtFeatMappingLoader;
	@Inject
	private CatalArticleLoader catalArticleLoader;
	@Inject
	private CatalArtManufSuppLoader catalArtManufSuppLoader;
	@Inject
	private CatalPicMappingLoader catalPicMappingLoader;
	@Inject
	private CatalPkgModeLoader catalPkgModeLoader;
	@Inject
	private CatalProductFamilyLoader catalProductFamilyLoader;
	@Inject
	private CatalFamilyFeatMapingLoader catalFamilyFeatMapingLoader;
	@Inject
	private CatalCipOrigineLoader catalCipOrigineLoader;
	
	@PostConstruct
	public void postConstruct(){
		dataSheetLoader.registerLoader(CatalArtDetailConfig.class.getSimpleName(), catalArtDetailConfigLoader);
		dataSheetLoader.registerLoader(CatalArtEquivalence.class.getSimpleName(), catalArtEquivalenceLoader);
		dataSheetLoader.registerLoader(CatalArtFeatMapping.class.getSimpleName(), catalArtFeatMappingLoader);
		dataSheetLoader.registerLoader(CatalArticle.class.getSimpleName(), catalArticleLoader);
		dataSheetLoader.registerLoader(CatalArtManufSupp.class.getSimpleName(), catalArtManufSuppLoader);
		dataSheetLoader.registerLoader(CatalPicMapping.class.getSimpleName(), catalPicMappingLoader);
		dataSheetLoader.registerLoader(CatalProductFamily.class.getSimpleName(), catalProductFamilyLoader);
		dataSheetLoader.registerLoader(CatalFamilyFeatMaping.class.getSimpleName(), catalFamilyFeatMapingLoader);
		
		catalPkgModeLoader.load(catalEnumDataProvider.getPkgModeData());
		catalCipOrigineLoader.load(catalEnumDataProvider.getCipOrigineData());
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
		catalProductFamilyLoader.createTemplate(workbook);
		catalFamilyFeatMapingLoader.createTemplate(workbook);
		catalArticleLoader.createTemplate(workbook);
		catalArtFeatMappingLoader.createTemplate(workbook);
		catalPicMappingLoader.createTemplate(workbook);
		catalArtManufSuppLoader.createTemplate(workbook);
		catalArtEquivalenceLoader.createTemplate(workbook);
		catalArtDetailConfigLoader.createTemplate(workbook);
		dataSheetLoader.exportTemplate(workbook);
	}
	
}
