package org.adorsys.adprocmt.rmtevents;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtLease;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtEJB;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtLeaseEJB;
import org.apache.commons.lang3.time.DateUtils;


public class PrcmtRemoteEventGateway {

	
	@Inject
	private PrcmtDeliveryEvtLeaseEJB evtLeaseEJB;
	
	@Inject
	private PrcmtDeliveryEvtEJB evtEJB;
	
	public void handleDeliveryClosedEvent(@Observes @PrcmtDeliveryClosedEvent PrcmtDeliveryHstry deliveryHstry){
		// Move this operation to an event.
		String evtName = deliveryHstry.getHstryType();
		PrcmtDeliveryEvt evt = new PrcmtDeliveryEvt();
		deliveryHstry.copyTo(evt);
		evt.setEvtName(evtName);
		evt.setId(deliveryHstry.getId());
		evtEJB.create(evt);
	}
	
	@Schedule(minute = "*/39", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	public void cleanUpProcessedEventData(PrcmtDeliveryEvt evt, Date now){
		Date hstryDt = evt.getHstryDt();
		// 1 hour wait time for listeners to process the event.
		if(now.before(DateUtils.addHours(hstryDt, 1))) return;
		
		List<PrcmtDeliveryEvtLease> leases = evtLeaseEJB.findByEvtId(evt.getId());
		for (PrcmtDeliveryEvtLease lease : leases) {
			if(!lease.expired(now)) return;
		}
		// remove leases;
		leases = evtLeaseEJB.findByEvtId(evt.getId());
		for (PrcmtDeliveryEvtLease lease : leases) {
			// if there is any lease not expired. Withdraw and wait.
			if(!lease.expired(now)) return;
		}
		for (PrcmtDeliveryEvtLease lease : leases) {
			evtLeaseEJB.deleteById(lease.getId());
		}

		// remove evt
		evtEJB.deleteById(evt.getId());
	}
}
