package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;

public class DeliveryInfo {
	public static String prinInfo(PrcmtDelivery delivery){
		return delivery.getDlvryNbr() + "_" + delivery.getDlvryStatus() + "_" + delivery.getSupplier() + "_" + delivery.getNetPurchAmt();
	}
}
