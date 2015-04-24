package org.adorsys.adcshdwr.rmtevents;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adcshdwr.event.CdrDrctSalesClosedEvent;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvtLease;
import org.adorsys.adcshdwr.jpa.CdrDsHstry;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEvtEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEvtLeaseEJB;
import org.apache.commons.lang3.time.DateUtils;

@Stateless
public class CdrRemoteEventGateway {

	@Inject
	private CdrDrctSalesEvtEJB evtEJB;

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;
	
	@Inject
	private CdrDrctSalesEvtLeaseEJB evtLeaseEJB;

	public void handleInvtryPostedEvent(
			@Observes @CdrDrctSalesClosedEvent CdrDsHstry dsHstry) {
		// Move this operation to an event.
		String evtName = dsHstry.getHstryType();
		CdrDrctSalesEvt evt = new CdrDrctSalesEvt();
		dsHstry.copyTo(evt);
		evt.setEvtName(evtName);
		evt.setId(dsHstry.getId());
		evtEJB.create(evt);
		
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		dsHstry.copyTo(batchEvt);
		batchEvt.setEvtName(evtName);
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule("ADCSHDWR");
		batchEvt.setEvtKlass(CdrDrctSalesEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}


	@Schedule(minute = "*", second="1/35" ,hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanUpProcessedEvent() {
		List<CdrDrctSalesEvt> listAll = evtEJB.listAll(0, 10);
		for (CdrDrctSalesEvt evt : listAll) {
			Date now = new Date();
			Date hstryDt = evt.getHstryDt();
			
			// 1 hour wait time for listeners to process the event.
			if (now.before(DateUtils.addHours(hstryDt, 1)))
				return;
			
			List<CdrDrctSalesEvtLease> leases = evtLeaseEJB.findByEvtId(evt.getId());
			for (CdrDrctSalesEvtLease lease : leases) {
				if (!lease.expired(now))
					return;
			}
			// remove leases;
			leases = evtLeaseEJB.findByEvtId(evt.getId());
			for (CdrDrctSalesEvtLease lease : leases) {
				evtLeaseEJB.deleteById(lease.getId());
			}

			// remove evt
			evtEJB.deleteById(evt.getId());
		}
	}
}
