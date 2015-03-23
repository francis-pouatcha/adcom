package org.adorsys.adinvtry.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

public class InvInvtryItemList {

	private String salIndex;
	
	private List<String> invtryNbrs = new ArrayList<String>();
	
	private String lotPic;

	private String artPic;

	private String artName;
	
	private String section;
	
	private List<InvInvtryItem> invtryItems = new ArrayList<InvInvtryItem>();
	
	public InvInvtryItemList() {
	}
	
	public InvInvtryItemList(String salIndex, List<String> invtryNbrs,
			List<InvInvtryItem> invtryItems) {
		this.salIndex = salIndex;
		this.invtryNbrs = invtryNbrs;
		InvInvtryItem invInvtryItem = invtryItems.iterator().next();
		lotPic = invInvtryItem.getLotPic();
		artPic = invInvtryItem.getArtPic();
		artName = invInvtryItem.getArtName();
		section = invInvtryItem.getSection();
		// Sort the inventory items in the order of the intryNbrs
//		this.invtryItems = new ArrayList<InvInvtryItem>(invtryNbrs.size());
		InvInvtryItem[] invtryItemsArray = new InvInvtryItem[invtryNbrs.size()];
		int size = invtryNbrs.size();
		for (int i = 0; i < size; i++) {
			String invtryNbr = invtryNbrs.get(i);
			for (InvInvtryItem invtryItem : invtryItems) {
				if(StringUtils.equals(invtryNbr, invtryItem.getInvtryNbr())){
					invtryItemsArray[i]=invtryItem;
					break;
				}
			}
		}
		this.invtryItems = Arrays.asList(invtryItemsArray);
	}

	public List<String> getInvtryNbrs() {
		return invtryNbrs;
	}

	public void setInvtryNbrs(List<String> invtryNbrs) {
		this.invtryNbrs = invtryNbrs;
	}

	public List<InvInvtryItem> getInvtryItems() {
		return invtryItems;
	}

	public void setInvtryItems(List<InvInvtryItem> invtryItems) {
		this.invtryItems = invtryItems;
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
	
}
