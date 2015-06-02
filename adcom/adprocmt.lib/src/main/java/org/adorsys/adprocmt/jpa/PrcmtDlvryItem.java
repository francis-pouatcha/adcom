package org.adorsys.adprocmt.jpa;

import javax.persistence.Entity;

import org.adorsys.javaext.description.Description;

@Entity
@Description("PrcmtDlvryItem_description")
public class PrcmtDlvryItem extends PrcmtAbstractDlvryItem {
	private static final long serialVersionUID = 6727828567847544995L;

	public void fillDataFromOrderItem(PrcmtPOItem poItem) {
		this.setArtName(poItem.getArtName());
		this.setArtPic(poItem.getArtPic());
		this.setQtyDlvrd(poItem.getQtyOrdered());
		this.setPppuPreTax(poItem.getPppuPreTax());
		this.setFreeQty(poItem.getFreeQty());
		this.setRebateAmt(poItem.getRebate());
		this.setVatAmt(poItem.getVatAmount());
		this.setSupplier(poItem.getSupplier());
	}
}