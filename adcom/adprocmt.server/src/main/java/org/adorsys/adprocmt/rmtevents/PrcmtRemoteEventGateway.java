package org.adorsys.adprocmt.rmtevents;

import java.util.Date;
import java.util.List;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtLease;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtDataEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtLeaseEJB;
import org.apache.commons.lang3.time.DateUtils;


public class PrcmtRemoteEventGateway {

	
	@Inject
	private PrcmtDeliveryEvtLeaseEJB evtLeaseEJB;
	
	@Inject
	private PrcmtDeliveryEvtEJB evtEJB;
	
	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;

	public void handleDeliveryClosedEvent(@Observes @PrcmtDeliveryClosedEvent PrcmtDeliveryHstry deliveryHstry){
		// Move this operation to an event.
		String evtName = deliveryHstry.getHstryType();
		PrcmtDeliveryEvt evt = new PrcmtDeliveryEvt();
		deliveryHstry.copyTo(evt);
		evt.setEvtName(evtName);
		evt.setId(deliveryHstry.getId());
		evtEJB.create(evt);
	}
	
	public void cleanUpProcessedEventData(PrcmtDeliveryEvt evt, Date now){
		Date hstryDt = evt.getHstryDt();
		if(now.before(DateUtils.addDays(hstryDt, 1))) return;// 24 hours wait time for listeners to process the event.
		
		List<PrcmtDeliveryEvtLease> leases = evtLeaseEJB.findByEvtId(evt.getId());
		for (PrcmtDeliveryEvtLease lease : leases) {
			if(!lease.expired(now)) return;
		}
		// remove leases;
		leases = evtLeaseEJB.findByEvtId(evt.getId());
		for (PrcmtDeliveryEvtLease lease : leases) {
			evtLeaseEJB.deleteById(lease.getId());
		}
		
		// remove event data
		evtDataEJB.deleteById(evt.getEntIdentif());
		
		// remove evt
		evtEJB.deleteById(evt.getId());
	}
}
