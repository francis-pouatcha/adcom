package org.adorsys.adinvtry.api;

import java.util.Date;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.rest.InvInvtryEJB;
import org.adorsys.adinvtry.rest.InvInvtryItemEJB;

@Stateless
public class InvInvtryMerger {

	@Inject
	private InvInvtryEJB inventoryEJB;
	
	@Inject
	private InvInvtryItemEJB invInvtryItemEJB; 

	public void setMerged(String invtryNbr) {
		InvInvtry invtry = inventoryEJB.findByIdentif(invtryNbr);
		if(invtry==null) return;
		if(invtry.getMergedDate()!=null) return;
		invtry.setInvtryStatus(InvInvtryStatus.MERGED);
		invtry.setMergedDate(new Date());
		inventoryEJB.update(invtry);
	}

	public void mergeTo(String itemIdentif, String containerId) {
		InvInvtryItem oldItem = invInvtryItemEJB.findByIdentif(itemIdentif);
		if(oldItem==null) return;
		InvInvtryItem invInvtryItem = new InvInvtryItem();
		oldItem.copyTo(invInvtryItem);
		invInvtryItem.setInvtryNbr(containerId);
		String newIdentif = InvInvtryItem.toIdentifier(invInvtryItem.getInvtryNbr(), invInvtryItem.getAcsngUser(), 
				invInvtryItem.getLotPic(), invInvtryItem.getArtPic(), invInvtryItem.getSection());
		InvInvtryItem found = invInvtryItemEJB.findByIdentif(newIdentif);
		// Replace existing if not existent, or not accessed or accessed before current.
		if(found==null || found.getAcsngDt()==null || (oldItem.getAcsngDt()!=null && oldItem.getAcsngDt().after(found.getAcsngDt()))){
			invInvtryItemEJB.create(invInvtryItem);
			invInvtryItemEJB.deleteById(oldItem.getId());
		}
	}

}
