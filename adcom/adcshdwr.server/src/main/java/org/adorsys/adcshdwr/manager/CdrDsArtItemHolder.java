/**
 * 
 */
package org.adorsys.adcshdwr.manager;

import org.adorsys.adcshdwr.jpa.CdrDsArtItemEvt;

/**
 * @author boriswaguia
 *
 */
public class CdrDsArtItemHolder {
	
	private String artName;
	private String maxStockQty;
	private CdrDsArtItemEvt item;
	private boolean deleted;
	public String getMaxStockQty() {
		return maxStockQty;
	}
	public void setMaxStockQty(String maxStockQty) {
		this.maxStockQty = maxStockQty;
	}
	public String getArtName() {
		return artName;
	}
	public void setArtName(String artName) {
		this.artName = artName;
	}
	public CdrDsArtItemEvt getItem() {
		return item;
	}
	public void setItem(CdrDsArtItemEvt item) {
		this.item = item;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
	@Override
	public String toString() {
		return "CdrDsArtItemHolder [artName=" + artName + ", maxStockQty="
				+ maxStockQty + ", item=" + item + "]";
	}
}