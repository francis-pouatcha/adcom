package org.adorsys.adprocmt.worker;

import java.util.Date;
import java.util.List;

import javax.ejb.Schedule;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtDataCstr;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtDataEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryItemEvtDataEJB;

public class PrcmtDeliveryEvtDataCleanup {
	
	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;
	
	@Inject
	private PrcmtDlvryItemEvtDataEJB itemEvtDataEJB;

	@Schedule(hour="5/2")
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleanUp(){
		Date now = new Date();
		List<PrcmtDeliveryEvtDataCstr> listDeleted = evtDataEJB.listDeleted(now, 20);
		for (PrcmtDeliveryEvtDataCstr cstr : listDeleted) {
			Long count = itemEvtDataEJB.countByDlvryNbr(cstr.getEntIdentif());
			if(count<=0){
				evtDataEJB.deleteCstr(cstr.getId());
			} else {
				itemEvtDataEJB.deleteByDlvryNbr(cstr.getEntIdentif(), 100);
			}
		}
	}
}
