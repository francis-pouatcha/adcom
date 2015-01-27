package org.adorsys.adprocmt.api;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.enums.BaseProcessStatusEnum;
import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.rest.PrcmtDeliveryEJB;

@Stateless
public class PrcmtDeliveryManager {
	
	@Inject
	private PrcmtDeliveryEJB deliveryEJB;
	
	@Inject
	private SecurityUtil securityUtil;

	/**
	 * Initializes a delivery.
	 * 
	 * @param delivery
	 * @return
	 */
	public PrcmtDelivery newDelivery(PrcmtDelivery delivery){
		// Create the delivery
		Date now = new Date();
		String currentLoginName = securityUtil.getCurrentLoginName();
		delivery.setCreatingUsr(currentLoginName);
		delivery.setCreationDt(now);
		delivery.setDlvryStatus(BaseProcessStatusEnum.ONGOING.name());
		return deliveryEJB.create(delivery);
	}
	
	/**
	 * Add items to a delivery, updating the delivery value.
	 * 
	 * @param deliveryItemsHolder
	 * @return
	 */
	public PrcmtDelivery addDeliveryItems(PrcmtDeliveryItemsHolder deliveryItemsHolder){
//		deliveryEJB.findById(id)
		
		deliveryItemsHolder.getDelieryItems();
		
		return null;
	}
}
