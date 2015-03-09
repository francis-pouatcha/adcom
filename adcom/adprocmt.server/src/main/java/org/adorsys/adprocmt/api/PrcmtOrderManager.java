package org.adorsys.adprocmt.api;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.rest.PrcmtProcOrderEJB;
import org.adorsys.adprocmt.trigger.TriggerModeExecuter;
import org.adorsys.adprocmt.trigger.TriggerModeHandlerFactoryProducer;

@Stateless
public class PrcmtOrderManager {
	@Inject
	private PrcmtProcOrderEJB prcmtOrderEJB;
	@Inject
	private TriggerModeHandlerFactoryProducer triggerModeHandlerFactoryProducer;
	
	public PrcmtOrderHolder createOrder(PrcmtProcOrder prcmtOrder){
		
		PrcmtProcOrder prcmtProcOrder = prcmtOrderEJB.createCustom(prcmtOrder);
		TriggerModeExecuter triggerModeExecuter = triggerModeHandlerFactoryProducer.getTriggerModeHandlerFactory().findHandler(prcmtOrder.getPoTriggerMode());
		PrcmtOrderHolder prcmtOrderHolder = triggerModeExecuter.executeTriggerMode(prcmtProcOrder);
		return prcmtOrderHolder;
	}

	public PrcmtOrderHolder updateOrder(PrcmtOrderHolder prcmtOrderHolder){
		//TODO implement
		return null;
	}
	
	public PrcmtOrderHolder closeOrder(PrcmtOrderHolder prcmtOrderHolder){
		//TODO implement
		return null;
	}
	
	public PrcmtOrderHolder findOrder(String id){
		//TODO implement
		return null;
	}
	
	public PrcmtDeliveryHolder Order2Delivery(PrcmtOrderHolder prcmtOrderHolder){
		//TODO implement
		return null;
	}
	
}
