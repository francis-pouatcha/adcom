package org.adorsys.adstock.recptcls;

import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ejb.AccessTimeout;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDlvryEvtLstnr;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEvtEJB;
import org.adorsys.adprocmt.rest.PrcmtDlvryEvtLstnrEJB;

/**
 * Watch over delivery events and update the corresponding stock.
 * 
 * @author francis
 *
 */
@Singleton
public class StkDeliveryListener {
	
	@Inject
	private PrcmtDlvryEvtLstnrEJB evtLstnrEJB;
	
	@Inject
	private PrcmtDeliveryEvtEJB evtEJB;
	
	@Inject
	private StkDeliveryEvtProcessor evtProcessor; 
	
	@PostConstruct
	public void postConstruct(){
		PrcmtDlvryEvtLstnr dlvryEvtLstnr = evtLstnrEJB.findById(PrcmtDlvryEvtLstnr.toId(StkDeliveryListener.class.getSimpleName(), BaseHistoryTypeEnum.CLOSED.name()));
		if(dlvryEvtLstnr==null){
			dlvryEvtLstnr = new PrcmtDlvryEvtLstnr();
			dlvryEvtLstnr.setLstnrName(StkDeliveryListener.class.getSimpleName());
			dlvryEvtLstnr.setEvtName(BaseHistoryTypeEnum.CLOSED.name());
			evtLstnrEJB.create(dlvryEvtLstnr);
		}
	}

	@Schedule(minute = "*/3", hour="*", persistent=false)
	@AccessTimeout(unit=TimeUnit.MINUTES, value=10)
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void process() throws Exception {
		List<PrcmtDeliveryEvt> events = evtEJB.findByLstnrNameAndEvtName(StkDeliveryListener.class.getSimpleName(), BaseHistoryTypeEnum.CLOSED.name());
		for (PrcmtDeliveryEvt deliveryEvt : events) {
			evtProcessor.process(deliveryEvt);
		}
	}
}
