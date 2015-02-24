package org.adorsys.adcore.utils;

import java.math.BigDecimal;

public class BigDecimalUtils {
	public static boolean isNullOrZero(BigDecimal bigDecimal){
		return bigDecimal==null || bigDecimal.compareTo(BigDecimal.ZERO)==0;
	}
	
	public static BigDecimal zeroIfNull(BigDecimal bigDecimal){
		if(bigDecimal==null) return BigDecimal.ZERO;
		return bigDecimal;
	}
	
	public static boolean numericEquals(BigDecimal a, BigDecimal b){
		if(isNullOrZero(a)) return isNullOrZero(b);
		if(isNullOrZero(b)) return false;
		return a.compareTo(b)==0;
	}
	
	public static BigDecimal sum(BigDecimal... sumands){
		BigDecimal result = BigDecimal.ZERO;
		for (BigDecimal sumand : sumands) {
			result = result.add(zeroIfNull(sumand));
		}
		return result;
	}
	
	public static BigDecimal subs(BigDecimal base,BigDecimal ... substracts) {
		for (BigDecimal bigDecimal : substracts) {
			base = FinancialOps.substract(base, bigDecimal);
		}
		return base;
	}
}
