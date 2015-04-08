/**
 * 
 */
package org.adorsys.adcshdwr.api;

import org.adorsys.adcshdwr.jpa.CdrPymntItem;


/**
 * @author boriswaguia
 *
 */
public class CdrPymntItemInfo {
	
	public static String getDocType() {
		return CdrPymntItem.class.getName();
	}
	
}
