package org.adorsys.adstock.recptcls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtLease;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtDataEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtLeaseEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEvtDataEJB;
import org.adorsys.adstock.rest.StkArticleLotEJB;

/**
 * Check for the incoming of delivery closed event and 
 * process corresponding delivery items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDeliveryEvtProcessor {

	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;
	@Inject
	private PrcmtDlvryItemEvtDataEJB itemEvtDataEJB;
	@Inject
	private StkArticleLotEJB articleLotEJB;
	@Inject
	private StkDeliveryItemEvtProcessor itemEvtProcessor;
	@Inject
	private PrcmtDeliveryEvtLeaseEJB evtLeaseEJB;
	
	public void process(PrcmtDeliveryEvt deliveryEvt) {
		// This identifies a run.
		String processId = UUID.randomUUID().toString();
		
		Date now = new Date();
		String leaseId = null;
		
		// 1. Check if there is a lease associated with this processor.
		List<PrcmtDeliveryEvtLease> leases = evtLeaseEJB.findByEvtIdAndHandlerName(deliveryEvt.getId(), getHandlerName());
		if(leases.isEmpty()){
			// create one
			PrcmtDeliveryEvtLease lease = new PrcmtDeliveryEvtLease();
			lease.setEvtId(deliveryEvt.getId());
			lease.setEvtName(deliveryEvt.getEvtName());
			lease.setHandlerName(getHandlerName());
			lease.setProcessOwner(processId);
			lease = evtLeaseEJB.create(lease);
			leaseId = lease.getId();
		} else {
			PrcmtDeliveryEvtLease lease = leases.iterator().next();
			// look if lease expired.
			if(lease.expired(now)){
				leaseId = recover(processId, lease);
			}
		}
		if(leaseId==null) return;
		
		String entIdentif = deliveryEvt.getEntIdentif();
		PrcmtDeliveryEvtData deliveryEvtData = evtDataEJB.findById(entIdentif);
		if(deliveryEvtData==null) {
			PrcmtDeliveryEvtLease lease = evtLeaseEJB.findById(leaseId);
			lease.setValidTo(now);
			lease.setProcessingStatus(BaseProcessStatusEnum.CLOSED.name());
			evtLeaseEJB.update(lease);
			return;
		}
		
		String dlvryNbr = deliveryEvtData.getDlvryNbr();
		Long evtDataCount = itemEvtDataEJB.countByDlvryNbr(dlvryNbr);
		Long articleLotCount = articleLotEJB.countByDlvryNbr(dlvryNbr);
		// By checking the number of processed items, we know if there is still some to process 
		if(evtDataCount==articleLotCount) {
			// event is processed.
			PrcmtDeliveryEvtLease lease = evtLeaseEJB.findById(leaseId);
			lease.setValidTo(now);
			lease.setProcessingStatus(BaseProcessStatusEnum.CLOSED.name());
			evtLeaseEJB.update(lease);
			return;
		}
		
		int start = 0;
		int max = 100;
		List<PrcmtDlvryItemEvtData> itemEventDataToProcess = new ArrayList<PrcmtDlvryItemEvtData>();
		while(start<=evtDataCount){
			List<PrcmtDlvryItemEvtData> list = itemEvtDataEJB.findByDlvryNbr(dlvryNbr, start, max);
			start +=max;
			for (PrcmtDlvryItemEvtData itemEvtData : list) {
				List<String> found = articleLotEJB.findIdByDlvryItemNbr(itemEvtData.getDlvryItemNbr());
				if(!found.isEmpty()) continue;
				itemEventDataToProcess.add(itemEvtData);
			}
		}
		if(itemEventDataToProcess.isEmpty()) {
			// event is processed.
			PrcmtDeliveryEvtLease lease = evtLeaseEJB.findById(leaseId);
			lease.setValidTo(now);
			lease.setProcessingStatus(BaseProcessStatusEnum.CLOSED.name());
			evtLeaseEJB.update(lease);
			return;
		}
		for (PrcmtDlvryItemEvtData itemEvtDataId : itemEventDataToProcess) {
			itemEvtProcessor.process(itemEvtDataId, deliveryEvt);
		}
	}

	private String recover(String processOwner, PrcmtDeliveryEvtLease lease) {
		lease.extend(processOwner);
		PrcmtDeliveryEvtLease recovered = evtLeaseEJB.recover(lease);
		return recovered.getId();
	}

	private String getHandlerName(){
		return StkDeliveryEvtProcessor.class.getSimpleName();
	}
}
