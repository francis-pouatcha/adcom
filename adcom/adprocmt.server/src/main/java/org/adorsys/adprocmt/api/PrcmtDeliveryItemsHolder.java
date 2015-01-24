package org.adorsys.adprocmt.api;

import java.util.ArrayList;
import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

public class PrcmtDeliveryItemsHolder {
	
	private String dlvryNbr;
	
	private List<PrcmtDlvryItem> delieryItems = new ArrayList<PrcmtDlvryItem>();

	public String getDlvryNbr() {
		return dlvryNbr;
	}

	public void setDlvryNbr(String dlvryNbr) {
		this.dlvryNbr = dlvryNbr;
	}

	public List<PrcmtDlvryItem> getDelieryItems() {
		return delieryItems;
	}

	public void setDelieryItems(List<PrcmtDlvryItem> delieryItems) {
		this.delieryItems = delieryItems;
	}
}
