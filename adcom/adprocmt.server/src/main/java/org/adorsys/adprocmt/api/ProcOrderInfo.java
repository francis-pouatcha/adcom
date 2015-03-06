package org.adorsys.adprocmt.api;

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;

public class ProcOrderInfo {
	public static String prinInfo(PrcmtProcOrder procOrder){
		return procOrder.getPoNbr() + "_" + procOrder.getPoStatus() + "_" + procOrder.getSupplier() + "_" + procOrder.getNetPurchAmt();
	}
}
