package org.adorsys.adcshdwr.jpa;
public class CdrDsInfo {
	public static String prinInfo(CdrDrctSales cdrDrctSales){
		return cdrDrctSales.getDsNbr() + "_" + cdrDrctSales.getCdrNbr() + "_" + cdrDrctSales.getNetSPPreTax();
	}
}