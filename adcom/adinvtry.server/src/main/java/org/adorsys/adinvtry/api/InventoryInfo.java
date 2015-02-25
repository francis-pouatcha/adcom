package org.adorsys.adinvtry.api;

import org.adorsys.adinvtry.jpa.InvInvtry;

public class InventoryInfo {
	public static String prinInfo(InvInvtry invInvtry){
		return invInvtry.getInvtryNbr() + "_" + (invInvtry.getInvtryStatus()==null?"null":invInvtry.getInvtryStatus().name())+ "_" + invInvtry.getAcsngUser();
	}
}
