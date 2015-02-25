package org.adorsys.adstock.recptcls;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtDataEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEvtDataEJB;
import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.rest.StkArticleLotEJB;

/**
 * Check for the incomming of delivery closed event and 
 * process corresponding delivery items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDeliveryEvtProcessor {

	@Inject
	private PrcmtDeliveryEvtEJB evtEJB;
	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;
	@Inject
	private PrcmtDlvryItemEvtDataEJB itemEvtDataEJB;
	@Inject
	private StkArticleLotEJB articleLotEJB;
	@Inject
	private StkDeliveryItemEvtProcessor itemEvtProcessor;
	
	public void process(PrcmtDeliveryEvt deliveryEvt) {
		String entIdentif = deliveryEvt.getEntIdentif();
		PrcmtDeliveryEvtData deliveryEvtData = evtDataEJB.findById(entIdentif);
		if(deliveryEvtData==null) {
			evtEJB.deleteById(deliveryEvt.getId());
			return;
		}
		
		String dlvryNbr = deliveryEvtData.getDlvryNbr();
		Long count = itemEvtDataEJB.countByDlvryNbr(dlvryNbr);
		int start = 0;
		int max = 100;
		List<PrcmtDlvryItemEvtData> itemEventDataToProcess = new ArrayList<PrcmtDlvryItemEvtData>();
		while(start<=count){
			List<PrcmtDlvryItemEvtData> list = itemEvtDataEJB.findByDlvryNbr(dlvryNbr, start, max);
			start +=max;
			for (PrcmtDlvryItemEvtData itemEvtData : list) {
//				String artPic = itemEvtData.getArtPic();
				String lotPic = itemEvtData.getLotPic();
				// Check if this lot exists.
				StkAbstractArticleLot articleLot = articleLotEJB.findByIdentif(StkAbstractArticleLot.toId(lotPic));
				if(articleLot==null){
					itemEventDataToProcess.add(itemEvtData);
				}
			}
		}
		if(itemEventDataToProcess.isEmpty()) {
			evtEJB.deleteById(deliveryEvt.getId());
			return;
		}
		for (PrcmtDlvryItemEvtData itemEvtDataId : itemEventDataToProcess) {
			itemEvtProcessor.process(itemEvtDataId, deliveryEvt);
		}
	}

}
