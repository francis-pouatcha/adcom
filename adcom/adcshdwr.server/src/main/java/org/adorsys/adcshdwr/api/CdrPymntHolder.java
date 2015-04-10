/**
 * 
 */
package org.adorsys.adcshdwr.api;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrPymnt;

/**
 * @author boriswaguia
 *
 */
public class CdrPymntHolder {
	private CdrPymnt cdrPymnt;
	private List<CdrPymntItemHolder> pymtItems;
	
	public CdrPymnt getCdrPymnt() {
		return cdrPymnt;
	}
	public void setCdrPymnt(CdrPymnt cdrPymnt) {
		this.cdrPymnt = cdrPymnt;
	}
	public List<CdrPymntItemHolder> getPymtItems() {
		return pymtItems;
	}
	public void setPymtItems(List<CdrPymntItemHolder> pymtItems) {
		this.pymtItems = pymtItems;
	}
	
	
}
