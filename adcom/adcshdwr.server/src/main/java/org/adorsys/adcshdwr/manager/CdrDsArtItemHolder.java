/**
 * 
 */
package org.adorsys.adcshdwr.manager;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;

/**
 * @author boriswaguia
 *
 */
public class CdrDsArtItemHolder {
	
	private String artName;
	private CdrDsArtItem item;
	
	public String getArtName() {
		return artName;
	}
	public void setArtName(String artName) {
		this.artName = artName;
	}
	public CdrDsArtItem getItem() {
		return item;
	}
	public void setItem(CdrDsArtItem item) {
		this.item = item;
	}
	@Override
	public String toString() {
		return "CdrDsArtItemHolder [artName=" + artName + ", item=" + item
				+ "]";
	}
	
}