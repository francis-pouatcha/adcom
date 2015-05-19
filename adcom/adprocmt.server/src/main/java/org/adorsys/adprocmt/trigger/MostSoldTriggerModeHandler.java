package org.adorsys.adprocmt.trigger;

import java.math.BigDecimal;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.rest.CdrDsArtItemLookup;
import org.adorsys.adprocmt.api.PrcmtOrderHolder;
import org.adorsys.adprocmt.api.PrcmtOrderItemHolder;
import org.adorsys.adprocmt.api.PrcmtOrderManager;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.spi.dflt.ProcmtPOTriggerModeEnum;
import org.adorsys.adstock.rest.StkLotStockQtyLookup;
/**
 * 
 * @author guymoyo
 *
 */
@Singleton
public class MostSoldTriggerModeHandler implements TriggerModeExecuter {

	public static final String key = ProcmtPOTriggerModeEnum.MOST_SOLD.name();

	public static String getKey() {
		return key;
	}
	
	@Inject
	private CdrDsArtItemLookup cdrDsArtItemLookup;
	@Inject
	private StkLotStockQtyLookup stkLotStockQtyLookup;
	
	@Override
	public PrcmtOrderHolder executeTriggerMode(PrcmtProcOrder prcmtProcOrder) {	
		if(prcmtProcOrder==null) return null;
		PrcmtOrderHolder prcmtProcOrderHolder = new PrcmtOrderHolder();
		prcmtProcOrderHolder.setPrcmtProcOrder(prcmtProcOrder);
		
		List<CdrDsArtItem> topSales = cdrDsArtItemLookup.topSales(new BigDecimal(100));
		for(CdrDsArtItem saleItem:topSales){
			PrcmtPOItem prcmtPOItem = new PrcmtPOItem();
			BigDecimal stkQtyPreOrder = stkLotStockQtyLookup.countStockByArtPic(saleItem.getArtPic());
			prcmtPOItem.setStkQtyPreOrder(stkQtyPreOrder);
			prcmtPOItem.setArtName(saleItem.getArtName());
			prcmtPOItem.setArtPic(saleItem.getArtPic());
			prcmtPOItem.setStrgSection(saleItem.getSection());
			prcmtPOItem.setPppuCur(saleItem.getSppuCur());
			prcmtPOItem.setPppuPreTax(saleItem.getSppuPreTax());
			prcmtPOItem.setVatPct(saleItem.getVatPct());
			prcmtPOItem.setQtyOrdered(BigDecimal.ONE);
			PrcmtOrderItemHolder prcmtOrderItemHolder = new PrcmtOrderItemHolder();
			prcmtOrderItemHolder.setPrcmtPOItem(prcmtPOItem);
			prcmtProcOrderHolder.getPoItems().add(prcmtOrderItemHolder);
		}	
		return prcmtProcOrderHolder;
	}

}
