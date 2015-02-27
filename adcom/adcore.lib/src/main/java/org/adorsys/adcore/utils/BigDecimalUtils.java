package org.adorsys.adcore.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class BigDecimalUtils {
	
	public static final BigDecimal HUNDRED = new BigDecimal("100");
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

	public static BigDecimal basePercentOfRatePct(BigDecimal ratePct, BigDecimal base){
		if(isNullOrZero(base) || isNullOrZero(ratePct)) return BigDecimal.ZERO;
		BigDecimal result = zeroIfNull(base);

		if(numericEquals(ratePct,HUNDRED)) return result;
		
		result = result.multiply(ratePct).divide(HUNDRED);

		return result;
	}

	public static BigDecimal basePercentOfRatePct(BigDecimal ratePct, BigDecimal base, RoundingMode roundingMode){
		if(isNullOrZero(base) || isNullOrZero(ratePct)) return BigDecimal.ZERO;
		BigDecimal result = zeroIfNull(base);

		if(numericEquals(ratePct,HUNDRED)) return result;
		
		result = result.multiply(ratePct).divide(HUNDRED, roundingMode);

		return result;
	}
	
	public static BigDecimal amountWithTax(BigDecimal amtHT, BigDecimal vatRatePct){
		BigDecimal vat = basePercentOfRatePct(vatRatePct, amtHT, RoundingMode.HALF_EVEN);
		return sum(amtHT, vat);
	}
	
	public Map<String, BigDecimal> share(BigDecimal base, Map<String, BigDecimal> perctgs){
		int size = perctgs.size();
		Map<String, BigDecimal> result = new HashMap<String, BigDecimal>(size);
		Set<Entry<String,BigDecimal>> entrySet = perctgs.entrySet();
		for (Entry<String, BigDecimal> entry : entrySet) {
			BigDecimal ratePct = entry.getValue();
			BigDecimal value = basePercentOfRatePct(ratePct, base, RoundingMode.HALF_EVEN);
			result.put(entry.getKey(), value);
		}
		return result ;
	}
}
