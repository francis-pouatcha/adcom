package org.adorsys.adinvtry.jpa;

import java.math.BigDecimal;

public class InvInvtryGap {
	private BigDecimal gapSaleAmtHT;

	private BigDecimal gapPurchAmtHT;

	public InvInvtryGap() {
	}

	public InvInvtryGap(BigDecimal gapPurchAmtHT, BigDecimal gapSaleAmtHT) {
		super();
		this.gapPurchAmtHT = gapPurchAmtHT;
		this.gapSaleAmtHT = gapSaleAmtHT;
	}

	public BigDecimal getGapSaleAmtHT() {
		return gapSaleAmtHT;
	}

	public void setGapSaleAmtHT(BigDecimal gapSaleAmtHT) {
		this.gapSaleAmtHT = gapSaleAmtHT;
	}

	public BigDecimal getGapPurchAmtHT() {
		return gapPurchAmtHT;
	}

	public void setGapPurchAmtHT(BigDecimal gapPurchAmtHT) {
		this.gapPurchAmtHT = gapPurchAmtHT;
	}
}
