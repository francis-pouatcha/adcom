package org.adorsys.adprocmt.trigger;

import java.math.BigDecimal;

import javax.inject.Singleton;

import org.adorsys.adprocmt.api.PrcmtOrderHolder;
import org.adorsys.adprocmt.api.PrcmtOrderItemHolder;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.spi.dflt.ProcmtPOTriggerModeEnum;

@Singleton
public class MostSoldTriggerModeHandler implements TriggerModeExecuter {

	public static final String key = ProcmtPOTriggerModeEnum.MOST_SOLD.name();
	
	public static String getKey() {
		return key;
	}
	
	@Override
	public PrcmtOrderHolder executeTriggerMode(PrcmtProcOrder prcmtProcOrder) {
		// TODO implement most sold trigger		
		if(prcmtProcOrder==null) return null;
		PrcmtOrderHolder prcmtProcOrderHolder = new PrcmtOrderHolder();
		prcmtProcOrderHolder.setPrcmtProcOrder(prcmtProcOrder);
		
		//data test
		PrcmtPOItem prcmtPOItem = new PrcmtPOItem();
		prcmtPOItem.setArtName("XEROLYS FL POMPE 500ML");
		prcmtPOItem.setArtPic("9003602");
		prcmtPOItem.setQtyOrdered(new BigDecimal(4));
		PrcmtOrderItemHolder prcmtOrderItemHolder = new PrcmtOrderItemHolder();
		prcmtOrderItemHolder.setPrcmtPOItem(prcmtPOItem);
		prcmtProcOrderHolder.getPoItems().add(prcmtOrderItemHolder);
		
		return prcmtProcOrderHolder;
	}

}
