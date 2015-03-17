package org.adorsys.adstock.recptcls;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ejb.Asynchronous;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtLease;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtDataEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtLeaseEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEvtDataEJB;
import org.adorsys.adstock.jpa.StkDlvryItemHstry;
import org.adorsys.adstock.rest.StkDlvryItemHstryEJB;
import org.apache.commons.lang3.time.DateUtils;

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
	private StkDeliveryItemEvtProcessor itemEvtProcessor;
	@Inject
	private PrcmtDeliveryEvtLeaseEJB evtLeaseEJB;
	@Inject
	private StkDlvryItemHstryEJB dlvryItemHstryEJB;

	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
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
				leaseId = evtLeaseEJB.recover(processId, lease.getId());
			}
		}
		if(leaseId==null) return;
		
		String entIdentif = deliveryEvt.getEntIdentif();
		PrcmtDeliveryEvtData deliveryEvtData = evtDataEJB.findById(entIdentif);
		if(deliveryEvtData==null) {
			evtLeaseEJB.close(processId, leaseId);
			return;
		}
		
		String dlvryNbr = deliveryEvtData.getDlvryNbr();
		Long evtDataCount = itemEvtDataEJB.countByDlvryNbr(dlvryNbr);
		
		int start = 0;
		int max = 100;
		List<String> itemEventDataToProcess = new ArrayList<String>();
		while(start<=evtDataCount){
			List<PrcmtDlvryItemEvtData> list = itemEvtDataEJB.findByDlvryNbr(dlvryNbr, start, max);
			start +=max;
			for (PrcmtDlvryItemEvtData itemEvtData : list) {
				StkDlvryItemHstry hstry = dlvryItemHstryEJB.findById(itemEvtData.getDlvryItemNbr());
				if(hstry!=null) continue;

				itemEventDataToProcess.add(itemEvtData.getId());
			}
		}
		if(itemEventDataToProcess.isEmpty()) {
			evtLeaseEJB.close(processId, leaseId);
			return;
		}
		Date time = new Date();
		for (String itemEvtDataId : itemEventDataToProcess) {
			itemEvtProcessor.process(itemEvtDataId, deliveryEvt);
			if(DateUtils.addMinutes(new Date(), 1).before(time)){
				evtLeaseEJB.recover(processId, leaseId);
			}
		}
	}	
	private String getHandlerName(){
		return StkDeliveryEvtProcessor.class.getSimpleName();
	}
}
