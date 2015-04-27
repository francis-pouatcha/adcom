package org.adorsys.adstock.recptcls;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEvtLeaseEJB;
import org.adorsys.adinvtry.jpa.InvInvtryEvt;

/**
 * Check for the incoming of inventory closed event and 
 * process corresponding inventory items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDirectSalesEvtProcessorHelper {

	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	@Inject
	private CdrDrctSalesEvtLeaseEJB evtLeaseEJB;

	public void closeEvtLease(String processId, String leaseId, CdrDrctSalesEvt evt) {
		evtLeaseEJB.close(processId, leaseId);
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		evt.copyTo(batchEvt);
		batchEvt.setEvtName(BaseHistoryTypeEnum.COMMITTED.name());
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule("ADSTOCK");
		batchEvt.setEvtKlass(InvInvtryEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}

	public boolean shallProcessEvtLease(CdrDrctSalesEvt evt) {
		List<BaseBatchEvt> found = batchEvtEJB.findByEvtModuleAndEvtKlassAndEvtName("ADSTOCK", CdrDrctSalesEvt.class.getSimpleName(), BaseHistoryTypeEnum.COMMITTED.name(), 0, 1);
		return found.isEmpty();
	}
}
