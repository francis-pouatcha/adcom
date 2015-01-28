package org.adorsys.adstock.worker;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;

/**
 * Controls parallel posting of stock quantity movement and synchronizes them 
 * into the most actual record.
 * 
 * This worker also triggers deletion of older records after consolidation.
 *   
 * @author francis
 *
 */
@Singleton
public class StkStockQtyCtrlWkr {

	/**
	 * This will remove an obsolete record from the data base. This call is performed by the 
	 * Listener of the transaction creating the new record, after that transaction succeeds.
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleaup(){
		
	}
}
