/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.math.BigDecimal;
import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;

/**
 * @author boriswaguia
 *
 */
public class CdrDsArtHolder {
	
	private CdrDrctSales cdrDrctSales;
	private BigDecimal paidAmt;//amt given by the customer
	private BigDecimal changeAmt;//change 
	
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

	public BigDecimal getPaidAmt() {
		return paidAmt;
	}

	public void setPaidAmt(BigDecimal paidAmt) {
		this.paidAmt = paidAmt;
	}

	public BigDecimal getChangeAmt() {
		return changeAmt;
	}

	public void setChangeAmt(BigDecimal changeAmt) {
		this.changeAmt = changeAmt;
	}

	@Override
	public String toString() {
		return "CdrDsArtHolder [cdrDrctSales=" + cdrDrctSales + ", items="
				+ items + "]";
	}

}
