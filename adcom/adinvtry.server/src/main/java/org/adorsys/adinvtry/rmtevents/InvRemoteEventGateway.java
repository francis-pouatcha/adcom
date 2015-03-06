package org.adorsys.adinvtry.rmtevents;

import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adinvtry.event.InvInvtryClosedEvent;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryEvtDataCstr;
import org.adorsys.adinvtry.jpa.InvInvtryEvtLease;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.rest.InvInvtryEvtDataEJB;
import org.adorsys.adinvtry.rest.InvInvtryEvtEJB;
import org.adorsys.adinvtry.rest.InvInvtryEvtLeaseEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEvtDataEJB;
import org.apache.commons.lang3.time.DateUtils;

public class InvRemoteEventGateway {

	@Inject
	private InvInvtryEvtLeaseEJB evtLeaseEJB;

	@Inject
	private InvInvtryEvtEJB evtEJB;

	@Inject
	private InvInvtryEvtDataEJB evtDataEJB;

	public void handleInvtryClosedEvent(
			@Observes @InvInvtryClosedEvent InvInvtryHstry invtryHstry) {
		// Move this operation to an event.
		String evtName = invtryHstry.getHstryType();
		InvInvtryEvt evt = new InvInvtryEvt();
		invtryHstry.copyTo(evt);
		evt.setEvtName(evtName);
		evt.setId(invtryHstry.getId());
		evtEJB.create(evt);
	}

	@Schedule(minute = "*/43", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	public void cleanUpProcessedEventData(InvInvtryEvt evt, Date now) {
		Date hstryDt = evt.getHstryDt();

		// 1 hour wait time for listeners to process the event.
		if (now.before(DateUtils.addHours(hstryDt, 1)))
			return;

		List<InvInvtryEvtLease> leases = evtLeaseEJB.findByEvtId(evt.getId());
		for (InvInvtryEvtLease lease : leases) {
			if (!lease.expired(now))
				return;
		}
		// remove leases;
		leases = evtLeaseEJB.findByEvtId(evt.getId());
		for (InvInvtryEvtLease lease : leases) {
			// if there is any lease not expired. Withdraw and wait.
			if (!lease.expired(now))
				return;
		}
		for (InvInvtryEvtLease lease : leases) {
			evtLeaseEJB.deleteById(lease.getId());
		}

		// remove event data
		evtDataEJB.deleteById(evt.getEntIdentif());

		// remove evt
		evtEJB.deleteById(evt.getId());
	}

	@Inject
	private InvInvtryItemEvtDataEJB itemEvtDataEJB;

	@Schedule(minute = "*/47", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.HOURS, value=2)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanUp() {
		Date now = new Date();
		List<InvInvtryEvtDataCstr> listDeleted = evtDataEJB
				.listDeleted(now, 20);
		for (InvInvtryEvtDataCstr cstr : listDeleted) {
			Long count = itemEvtDataEJB.countByInvtryNbr(cstr.getEntIdentif());
			if (count <= 0) {
				evtDataEJB.deleteCstr(cstr.getId());
			} else {
				itemEvtDataEJB.deleteByInvtryNbr(cstr.getEntIdentif(), 100);
			}
		}
	}
}
