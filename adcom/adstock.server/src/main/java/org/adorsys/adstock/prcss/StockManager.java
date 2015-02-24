package org.adorsys.adstock.prcss;

import org.adorsys.adstock.jpa.StkAbstractArticleLot;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.jpa.StkRsrvtn;

/**
 * The stock management module does not synchronously interact with oder modules.
 * All modifying statements are resulting form oder business processes.
 * 
 * For example: 
 * 	- The creation of an article lot record is the result of:
 * 		- A delivery from a procurement operation
 * 		- Or an initial inventory operation.
 * 	- The creation of a StockArtLotQty if always the result of a stock movement.
 *  - The creation of a stock movement can be the result of
 *  	- An initial inventory
 *  	- A procurement delivery operation
 *  	- A sales delivery operation
 *  	- A correction from an inventory operation
 *  	- A return from procurement operation
 *  	- A return from sales operation
 *  	- A trashing of expired article operation
 *  - The creation of a stock reservation can result from
 *  	- A submitted sales order operation
 *  - The deletion of a reservation can result from
 *  	- A fulfillment of a sales order
 *  
 * Calculating the stock quantity of a lot or an article
 * 	- We do no save stock quantities with the lot or article record. Instead, a proper
 * StockQuantity record is used to track and aggregate records. Each stock movement
 * leads to the creation of a new qty record and the deletion of an older record.
 * If two parallel processes affect the stock at the same time, it will lead to
 * the creation of two new record and a record consolidator will take care of the 
 * proper computation of the real remaining stock value.
 *   
 * @author francis
 *
 */
public class StockManager {

	/**
	 * Registers a new article lot with the stock management system.
	 * 
	 * @param lotData
	 */
	public void registerLot(StkAbstractArticleLot lotData, String origDocNbrs){
		
	}
	
	public void moveStock(StkMvnt mvnt){
		
	}

	public void reserveStock(StkRsrvtn rsrvtn){
		
	}
}
