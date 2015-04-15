package org.adorsys.adstock.recptcls;

import java.util.List;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adbase.jpa.BaseBatchEvt;
import org.adorsys.adbase.rest.BaseBatchEvtEJB;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtLeaseEJB;

/**
 * Check for the incoming of delivery closed event and 
 * process corresponding delivery items.
 * 
 * @author francis
 *
 */
@Stateless
public class StkDeliveryEvtProcessorHelper {

	@Inject
	private PrcmtDeliveryEvtLeaseEJB evtLeaseEJB;
	@Inject
	private BaseBatchEvtEJB batchEvtEJB;

	public void closeEvtLease(String processId, String leaseId, PrcmtDeliveryEvt deliveryEvt) {
		evtLeaseEJB.close(processId, leaseId);
		BaseBatchEvt batchEvt = new BaseBatchEvt();
		deliveryEvt.copyTo(batchEvt);
		batchEvt.setEvtName(BaseHistoryTypeEnum.COMMITTED.name());
		batchEvt.setId(UUID.randomUUID().toString());
		batchEvt.setEvtModule("ADSTOCK");
		batchEvt.setEvtKlass(PrcmtDeliveryEvt.class.getSimpleName());
		batchEvtEJB.create(batchEvt);
	}

	public boolean shallProcessEvtLease(PrcmtDeliveryEvt deliveryEvt) {
		List<BaseBatchEvt> found = batchEvtEJB.findByEvtModuleAndEvtKlassAndEvtName("ADSTOCK", PrcmtDeliveryEvt.class.getSimpleName(), BaseHistoryTypeEnum.COMMITTED.name(), 0, 1);
		return found.isEmpty();
	}
}
