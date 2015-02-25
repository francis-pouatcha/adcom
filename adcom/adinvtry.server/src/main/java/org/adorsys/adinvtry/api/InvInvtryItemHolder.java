package org.adorsys.adinvtry.api;

import org.adorsys.adinvtry.jpa.InvInvtryItem;

public class InvInvtryItemHolder {

	private InvInvtryItem invtryItem;

	private boolean deleted;

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public InvInvtryItem getInvtryItem() {
		return invtryItem;
	}

	public void setInvtryItem(InvInvtryItem invtryItem) {
		this.invtryItem = invtryItem;
	}
}
