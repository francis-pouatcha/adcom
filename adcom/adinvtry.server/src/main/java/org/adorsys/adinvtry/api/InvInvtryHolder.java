/**
 * 
 */
package org.adorsys.adinvtry.api;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtry;


/**
 * @author boriswaguia
 *
 */
public class InvInvtryHolder {
	
	private InvInvtry invtry;
	
	private List<InvInvtryItemHolder> invtryItemHolders;

	public InvInvtry getInvtry() {
		return invtry;
	}

	public void setInvtry(InvInvtry invtry) {
		this.invtry = invtry;
	}

	public List<InvInvtryItemHolder> getInvtryItemHolders() {
		return invtryItemHolders;
	}

	public void setInvtryItemHolders(List<InvInvtryItemHolder> invtryItemHolders) {
		this.invtryItemHolders = invtryItemHolders;
	}

	@Override
	public String toString() {
		return "InvInvtryHolder [invtry=" + invtry + ", invtryItemHolders="
				+ invtryItemHolders + "]";
	}

}
