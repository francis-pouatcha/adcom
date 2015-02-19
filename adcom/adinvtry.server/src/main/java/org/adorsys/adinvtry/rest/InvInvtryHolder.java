/**
 * 
 */
package org.adorsys.adinvtry.rest;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;


/**
 * @author boriswaguia
 *
 */
public class InvInvtryHolder {
	
	private InvInvtry invtry;
	
	private List<InvInvtryItem> invtryItems;

	public InvInvtry getInvtry() {
		return invtry;
	}

	public void setInvtry(InvInvtry invtry) {
		this.invtry = invtry;
	}

	public List<InvInvtryItem> getInvtryItems() {
		return invtryItems;
	}

	public void setInvtryItems(List<InvInvtryItem> invtryItems) {
		this.invtryItems = invtryItems;
	}

	@Override
	public String toString() {
		return "InvIntryHolder [invtry=" + invtry + ", invtryItems="
				+ invtryItems + "]";
	}
	
}
