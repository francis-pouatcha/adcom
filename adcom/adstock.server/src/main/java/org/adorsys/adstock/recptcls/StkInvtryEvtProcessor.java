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

import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.adorsys.adinvtry.jpa.InvInvtryEvtLease;
import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.adorsys.adinvtry.rest.InvInvtryEvtDataEJB;
import org.adorsys.adinvtry.rest.InvInvtryEvtLeaseEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEvtDataEJB;
import org.adorsys.adstock.jpa.StkInvtryItemHstry;
import org.adorsys.adstock.rest.StkInvtryItemHstryEJB;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Check for the incoming of inventory closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkInvtryEvtProcessor {

	@Inject
	private InvInvtryEvtDataEJB evtDataEJB;
	@Inject
	private InvInvtryItemEvtDataEJB itemEvtDataEJB;
	@Inject
	private StkInvtryItemEvtProcessor itemEvtProcessor;
	@Inject
	private InvInvtryEvtLeaseEJB evtLeaseEJB;
	@Inject
	private StkInvtryItemHstryEJB invtryItemHstryEJB;
	
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process(InvInvtryEvt invtryEvt) {
		// This identifies a run.
		String processId = UUID.randomUUID().toString();
		
		Date now = new Date();
		String leaseId = null;
		
		// 1. Check if there is a lease associated with this processor.
		List<InvInvtryEvtLease> leases = evtLeaseEJB.findByEvtIdAndHandlerName(invtryEvt.getId(), getHandlerName());
		if(leases.isEmpty()){
			// create one
			InvInvtryEvtLease lease = new InvInvtryEvtLease();
			lease.setEvtId(invtryEvt.getId());
			lease.setEvtName(invtryEvt.getEvtName());
			lease.setHandlerName(getHandlerName());
			lease.setProcessOwner(processId);
			lease = evtLeaseEJB.create(lease);
			leaseId = lease.getId();
		} else {
			InvInvtryEvtLease lease = leases.iterator().next();
			// look if lease expired.
			if(lease.expired(now)){
				leaseId = evtLeaseEJB.recover(processId, lease.getId());
			}
		}
		if(leaseId==null) return;
		
		String entIdentif = invtryEvt.getEntIdentif();
		InvInvtryEvtData invtryEvtData = evtDataEJB.findById(entIdentif);
		if(invtryEvtData==null) {
			evtLeaseEJB.close(processId, leaseId);
			return;
		}
		
		String invtryNbr = invtryEvtData.getInvtryNbr();
		Long evtDataCount = itemEvtDataEJB.countByInvtryNbr(invtryNbr);
		
		int start = 0;
		int max = 100;
		List<String> itemEventDataToProcess = new ArrayList<String>();
		while(start<=evtDataCount){
			List<InvInvtryItemEvtData> list = itemEvtDataEJB.findByInvtryNbr(invtryNbr, start, max);
			start +=max;
			for (InvInvtryItemEvtData itemEvtData : list) {
				StkInvtryItemHstry invtryItemEvt = invtryItemHstryEJB.findById(itemEvtData.getIdentif());
				if(invtryItemEvt!=null) continue;// processed.
				itemEventDataToProcess.add(itemEvtData.getId());
			}
		}
		if(itemEventDataToProcess.isEmpty()) {
			evtLeaseEJB.close(processId, leaseId);
			return;
		}
		Date time = new Date();
		for (String itemEvtDataId : itemEventDataToProcess) {
			itemEvtProcessor.process(itemEvtDataId, invtryEvt);
			if(DateUtils.addMinutes(new Date(), 1).before(time)){
				evtLeaseEJB.recover(processId, leaseId);
			}
		}
	}

	private String getHandlerName(){
		return StkInvtryEvtProcessor.class.getSimpleName();
	}
}
