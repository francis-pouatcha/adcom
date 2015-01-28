package org.adorsys.adcore.jpa;

public enum CurrencyEnum {
	
	XAF(0),EUR(2),NGN(0),USD(2);
	private final int dcmlPos;

	private CurrencyEnum(int dcmlPos) {
		this.dcmlPos = dcmlPos;
	}

	public int getDcmlPos() {
		return dcmlPos;
	}
	
}
