package org.adorsys.adinvtry.api;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityExistsException;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.auth.AdSystem;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.rest.StkArticleLotLookup;

@Stateless
public class InvInvtryPrepareHelper {

	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 

	@Inject
	private StkArticleLotLookup articleLotLookup;

	@Inject
	private SecurityUtil securityUtil;
		
	@AdSystem
	public boolean createInvntryItems(List<StkArticleLot2StrgSctn> lot2Sections, InvInvtry inventory){
		boolean modified = false;
		String loginName = securityUtil.getCurrentLoginName();
		for (StkArticleLot2StrgSctn lot2StrgSctn : lot2Sections) {
			String identifier = InvInvtryItem.toIdentifier(inventory.getInvtryNbr(), loginName, lot2StrgSctn.getLotPic(), 
					lot2StrgSctn.getArtPic(), lot2StrgSctn.getStrgSection());
			InvInvtryItem invtryItem = invInvtryItemEJB.findByIdentif(identifier);
			if(invtryItem!=null) continue;
			invtryItem = new InvInvtryItem();
			invtryItem.setAcsngUser(loginName);
			invtryItem.setInvtryNbr(inventory.getInvtryNbr());
			invtryItem.setLotPic(lot2StrgSctn.getLotPic());
			invtryItem.setArtPic(lot2StrgSctn.getArtPic());
			invtryItem.setSection(lot2StrgSctn.getStrgSection());
			invtryItem.setArtName(lot2StrgSctn.getArtName());

			StkArticleLot articleLot = articleLotLookup.findByIdentif(invtryItem.getLotPic());
			invtryItem.setExpirDt(articleLot.getExpirDt());
			invtryItem.setMinSppuHT(articleLot.getMinSppuHT());
			invtryItem.setPppuCur(articleLot.getPppuCur());
			invtryItem.setPppuPT(articleLot.getPppuHT());
			invtryItem.setPurchRtrnDays(articleLot.getPurchRtrnDays());
			invtryItem.setPurchWrntyDys(articleLot.getPurchWrntyDys());
			invtryItem.setSalesRtrnDays(articleLot.getSalesRtrnDays());
			invtryItem.setSalesWrntyDys(articleLot.getSalesWrntyDys());
			invtryItem.setSppuCur(articleLot.getSppuCur());
			invtryItem.setSppuPT(articleLot.getSppuHT());
			invtryItem.setSupplier(articleLot.getSupplier());
			invtryItem.setSupplierPic(articleLot.getSupplierPic());
			invtryItem.setVatPurchPct(articleLot.getVatPurchPct());
			invtryItem.setVatSalesPct(articleLot.getVatSalesPct());
			
			//evaluate different amount before save
			try {
				invtryItem = invInvtryItemEJB.create(invtryItem);
				modified = true;
			} catch (EntityExistsException e){
				// Noop
			}
		}
		return modified;
	}
}
