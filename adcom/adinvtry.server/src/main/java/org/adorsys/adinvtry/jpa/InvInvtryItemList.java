package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adorsys.adcore.utils.BigDecimalUtils;

public class InvInvtryItemList {

	private String salIndex;
	
	private List<String> invtryNbrs = new ArrayList<String>();
	
	private String lotPic;

	private String artPic;

	private String artName;
	
	private String section;

	private List<InvInvtryItem> invtryItems = new ArrayList<InvInvtryItem>();
	
	private Boolean sameQty;
	
	public InvInvtryItemList() {
	}

	public InvInvtryItemList(String salIndex, List<String> invtryNbrs,
			List<InvInvtryItem> invtryItemsIn) {
		this.salIndex = salIndex;
		this.invtryNbrs = invtryNbrs;
		setCoreData(invtryItemsIn);
		this.invtryItems = invtryItemsIn;

		sameQty = checkSameQty(invtryItemsIn);
	}
	
	public static final Boolean checkSameQty(List<InvInvtryItem> invtryItemsIn){
		BigDecimal qty = null;
		Boolean sameQty = Boolean.FALSE;
		int count = 0;
		for (InvInvtryItem item : invtryItemsIn) {
			if(item.getDisabledDt()!=null) continue;
			if(count==0){
				qty = item.getAsseccedQty();
				if(qty!=null)
					sameQty = Boolean.TRUE;
			} else {
				if(item.getAsseccedQty()==null || !BigDecimalUtils.strictEquals(qty, item.getAsseccedQty())){
					sameQty = Boolean.FALSE;
				}
			}
		}
		return sameQty;
	}
	
	private void setCoreData(List<InvInvtryItem> invtryItemsIn){
		InvInvtryItem invInvtryItem = invtryItemsIn.iterator().next();
		lotPic = invInvtryItem.getLotPic();
		artPic = invInvtryItem.getArtPic();
		artName = invInvtryItem.getArtName();
		section = invInvtryItem.getSection();
	}

	public List<String> getInvtryNbrs() {
		return invtryNbrs;
	}

	public void setInvtryNbrs(List<String> invtryNbrs) {
		this.invtryNbrs = invtryNbrs;
	}

	public String getSalIndex() {
		return salIndex;
	}

	public void setSalIndex(String salIndex) {
		this.salIndex = salIndex;
	}

	public String getLotPic() {
		return lotPic;
	}

	public void setLotPic(String lotPic) {
		this.lotPic = lotPic;
	}

	public String getArtPic() {
		return artPic;
	}

	public void setArtPic(String artPic) {
		this.artPic = artPic;
	}

	public String getArtName() {
		return artName;
	}

	public void setArtName(String artName) {
		this.artName = artName;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	public Boolean getSameQty() {
		return sameQty;
	}

	public void setSameQty(Boolean sameQty) {
		this.sameQty = sameQty;
	}

	public List<InvInvtryItem> getInvtryItems() {
		return invtryItems;
	}

	public void setInvtryItems(List<InvInvtryItem> invtryItems) {
		this.invtryItems = invtryItems;
	}
}
