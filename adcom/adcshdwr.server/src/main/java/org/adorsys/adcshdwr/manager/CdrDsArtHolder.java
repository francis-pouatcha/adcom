/**
 * 
 */
package org.adorsys.adcshdwr.manager;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;

/**
 * @author boriswaguia
 *
 */
public class CdrDsArtHolder {
	
	private CdrDrctSales cdrDrctSales;
	private List<CdrDsArtItemHolder> items;

	
	
	public CdrDrctSales getCdrDrctSales() {
		return cdrDrctSales;
	}

	public void setCdrDrctSales(CdrDrctSales cdrDrctSales) {
		this.cdrDrctSales = cdrDrctSales;
	}

	public List<CdrDsArtItemHolder> getItems() {
		return items;
	}

	public void setItems(List<CdrDsArtItemHolder> items) {
		this.items = items;
	}

	@Override
	public String toString() {
		return "CdrDsArtHolder [cdrDrctSales=" + cdrDrctSales + ", items="
				+ items + "]";
	}

}
