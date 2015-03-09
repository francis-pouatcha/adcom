package org.adorsys.adinvtry.loader;

import javax.ejb.Singleton;
import javax.inject.Inject;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.api.InvInvtryHolder;
import org.adorsys.adinvtry.api.InvInvtryItemHolder;
import org.adorsys.adinvtry.api.InvInvtryManager;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.apache.commons.lang3.StringUtils;

@Singleton
public class InvInvtryManagerClient {

	@Inject
	private InvInvtryManager invtryManager;

	InvInvtryHolder invtryHolder = new InvInvtryHolder();
	
	public void saveInvtry(InvInvtryExcel invtryExcel){
		InvInvtry invtry = new InvInvtry();
		invtryExcel.copyTo(invtry);
		// New Holder
		this.invtryHolder = new InvInvtryHolder();
		this.invtryHolder.setInvtry(invtry);
		// Process org units.
	}
	
	public void saveInvtryItem(InvInvtryItemExcel invtryItemExcel){
		InvInvtryItem invtryItem = new InvInvtryItem();
		invtryItemExcel.copyTo(invtryItem);
		if(StringUtils.isBlank(invtryItem.getLotPic())){
			String lotPic = SequenceGenerator.getSequence(SequenceGenerator.LOT_SEQUENCE_PREFIXE);
			invtryItem.setLotPic(lotPic);
		}
		InvInvtryItemHolder invtryItemHolder = new InvInvtryItemHolder();
		invtryItemHolder.setInvtryItem(invtryItem);
		
		invtryHolder.getInvtryItemHolders().add(invtryItemHolder);
		
		if(invtryHolder.getInvtryItemHolders().size()>=20){
			InvInvtryHolder updateInvtry = invtryManager.updateInventory(invtryHolder);
			invtryHolder = new InvInvtryHolder();
			invtryHolder.setInvtry(updateInvtry.getInvtry());
		}
	}

	public void done() {
		invtryManager.closeInventory(invtryHolder);
		invtryHolder = new InvInvtryHolder();
	}
	
	public InvInvtryHolder update(){
		return invtryManager.updateInventory(invtryHolder);
	}
}
