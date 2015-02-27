package org.adorsys.adstock.worker;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Asynchronous;
import javax.ejb.Singleton;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.OptimisticLockException;

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.rest.StkLotStockQtyEJB;
import org.apache.commons.lang3.time.DateUtils;

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
	
	private Map<String, StkLotStockQty> cache = new HashMap<String, StkLotStockQty>();
	@Inject
	StkLotStockQtyEJB lotStockQtyEJB;

	/**
	 * This will remove an obsolete record from the data base. This call is performed by the 
	 * Listener of the transaction creating the new record, after that transaction succeeds.
	 */
	@Asynchronous
	@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
	public void cleaup(StkLotStockQty lotStockQty){
		if(lotStockQty.getQtyDt()==null) return;
		String artAndLotPic = lotStockQty.artAndLotPic();
		if(!cache.containsKey(artAndLotPic))cache.put(artAndLotPic, lotStockQty);
		
		process(artAndLotPic);
	}

	private void process(String artAndLotPic) {
		StkLotStockQty lotStockQty = cache.get(artAndLotPic);
		if(DateUtils.addMinutes(new Date(), -3).after(lotStockQty.getQtyDt())) return;
		consolidate(artAndLotPic);
	}
	
	private void consolidate(String artAndLotPic) {
		StkLotStockQty lotStockQty = cache.get(artAndLotPic);
		if(lotStockQty==null) return;
		String artPic = lotStockQty.getArtPic();
		String lotPic = lotStockQty.getLotPic();
		StkLotStockQty base = lotStockQtyEJB.findBase(artPic, lotPic);
		if(base==null) return;
		Integer seqNbr = base.getSeqNbr();
		List<StkLotStockQty> picAndSeq = lotStockQtyEJB.findByArtPicAndLotPicAndSeq(artPic, lotPic, seqNbr);
		BigDecimal baseQty = base.getStockQty();
		for (StkLotStockQty lsq : picAndSeq) {
			baseQty = baseQty.add(lsq.getStockQty());
			if(seqNbr < lsq.getSeqNbr()) seqNbr = lsq.getSeqNbr();
		}
		base.setQtyDt(new Date());
		base.setStockQty(baseQty);
		base.setSeqNbr(seqNbr);
		try {
			lotStockQtyEJB.update(base);
		} catch(OptimisticLockException e){
			// No opt. Had been taken care of by another thread.
		}
		
		// cleanup
		for (StkLotStockQty lsq : picAndSeq) {
			lotStockQtyEJB.deleteById(lsq.getId());
		}
	}
}
