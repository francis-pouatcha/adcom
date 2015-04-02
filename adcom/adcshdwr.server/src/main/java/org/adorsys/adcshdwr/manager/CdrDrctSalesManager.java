/**
 * 
 */
package org.adorsys.adcshdwr.manager;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemEvt;
import org.adorsys.adcshdwr.rest.CdrCshDrawerEJB;
import org.adorsys.adcshdwr.rest.CdrDrctSalesEJB;
import org.adorsys.adcshdwr.rest.CdrDsArtItemEvtEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
@Stateless
public class CdrDrctSalesManager {
	
	@Inject
	private CdrDrctSalesEJB cdrDrctSalesEJB;
	
	@Inject
	private CdrDsArtItemEvtEJB cdrDsArtItemEJB;
	
	@Inject
	private CdrCshDrawerEJB cdrCshDrawerEJB;
	@Inject
	private SecurityUtil securityUtil;
	
	
	
	

	public CdrDsArtHolder updateOrder(CdrDsArtHolder cdrDsArtHolder){
		CdrDrctSales cdrDrctSales = cdrDsArtHolder.getCdrDrctSales();
		
		boolean modified = false;
		boolean itemModified = deleteHolders(cdrDsArtHolder);
					
		List<CdrDsArtItemHolder> cdrDsArtItemHolders = cdrDsArtHolder.getItems();
		if(cdrDsArtItemHolders == null) cdrDsArtItemHolders = new ArrayList<CdrDsArtItemHolder>();
		
		for (CdrDsArtItemHolder cdrDsArtItemHolder : cdrDsArtItemHolders) {
			CdrDsArtItemEvt cdrDsArtItem = cdrDsArtItemHolder.getItem();

			if(StringUtils.isBlank(cdrDsArtItem.getDsNbr()))
				cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
			// check presence of the article pic
			if(StringUtils.isBlank(cdrDsArtItem.getArtPic()))
				throw new IllegalStateException("Missing article identification code.");

			if(StringUtils.isNotBlank(cdrDsArtItem.getId())){
				// todo check mdified
				CdrDsArtItemEvt persDi = cdrDsArtItemEJB.findById(cdrDsArtItem.getId());
				if(persDi==null) throw new IllegalStateException("Missing delivery item with id: " + cdrDsArtItem.getId());
				if(!cdrDsArtItem.contentEquals(persDi)){
					cdrDsArtItem.copyTo(persDi);
					cdrDsArtItem = cdrDsArtItemEJB.update(persDi);
					itemModified = true;
				}
			} else {
				if (StringUtils.isNotBlank(cdrDsArtItem.getDsNbr())) {
					CdrDsArtItemEvt persDi = cdrDsArtItemEJB.findById(cdrDsArtItem.getDsNbr());
					if(persDi!=null){
						if(!cdrDsArtItem.contentEquals(persDi)){
							cdrDsArtItem.copyTo(persDi);
							cdrDsArtItem = cdrDsArtItemEJB.update(persDi);
							itemModified = true;
						}
					} else {
						cdrDsArtItem.evlte();
						cdrDsArtItem.setDsNbr(cdrDrctSales.getDsNbr());
						cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
						itemModified = true;
					}
				} else {
					cdrDsArtItem.evlte();
					cdrDsArtItem = cdrDsArtItemEJB.create(cdrDsArtItem);
					itemModified = true;
				}
			}

			cdrDsArtItemHolder.setItem(cdrDsArtItem);
		}
		
		if(itemModified){
			recomputeOrder(cdrDrctSales);
//			cdrDrctSales.setPoStatus(BaseProcessStatusEnum.ONGOING.name());
			cdrDrctSales = cdrDrctSalesEJB.update(cdrDrctSales);
			cdrDsArtHolder.setCdrDrctSales(cdrDrctSales);
		}
		if(modified || itemModified){
//			createModifiedOrderHistory(cdrDrctSales);
//			updateCshDrawer(cdrDrctSales);
		}
		return cdrDsArtHolder;
	}
	

	private boolean deleteHolders(CdrDsArtHolder cdrDsArtHolder){
		List<CdrDsArtItemHolder> cdrDsArtItemHolders = cdrDsArtHolder.getItems();
		List<CdrDsArtItemHolder> oiToRemove = new ArrayList<CdrDsArtItemHolder>();
		boolean modified = false;
		for (CdrDsArtItemHolder itemHolder : cdrDsArtItemHolders) {
			if(itemHolder.isDeleted()){
				CdrDsArtItemEvt cdrDsArtItem = itemHolder.getItem();
				String id = StringUtils.isNotBlank(cdrDsArtItem.getId())?cdrDsArtItem.getId():cdrDsArtItem.getDsNbr();
				if(StringUtils.isNotBlank(id)){
					cdrDsArtItemEJB.deleteById(id);
					modified = true;					
				}
				oiToRemove.add(itemHolder);
			}
		}
		cdrDsArtItemHolders.removeAll(oiToRemove);
		return modified;
	}
	

	private void recomputeOrder(final CdrDrctSales cdrDrctSales){
		// recompute order object.
		String dsNbr = cdrDrctSales.getDsNbr();
		Long count = cdrDsArtItemEJB.countByDsNbr(dsNbr);
		int start = 0;
		int max = 100;
		cdrDrctSales.clearAmts();
		while(start<=count){
			List<CdrDsArtItemEvt> list = cdrDsArtItemEJB.findByDsNbr(dsNbr, start, max);
			start +=max;
			for (CdrDsArtItemEvt cdrDsArtItem : list) {
				cdrDrctSales.addGrossSPPreTax(cdrDsArtItem.getGrossSPPreTax());
				cdrDrctSales.addRebate(cdrDsArtItem.getRebate());
				cdrDrctSales.addNetSPPreTax(cdrDsArtItem.getNetSPPreTax());
				cdrDrctSales.addVatAmount(cdrDsArtItem.getVatAmount());
				cdrDrctSales.addNetSPTaxIncl(cdrDsArtItem.getNetSPTaxIncl());
			}
		}
		cdrDrctSales.evlte();
	}
	
}