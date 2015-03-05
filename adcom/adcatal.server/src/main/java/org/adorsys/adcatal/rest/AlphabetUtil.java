/**
 * 
 */
package org.adorsys.adcatal.rest;

import org.apache.commons.lang3.StringUtils;

/**
 * @author boriswaguia
 *
 */
public class AlphabetUtil {
	private static final String alphabet = "abcdefghijklmnopqrstuvwxyz";
	
	public static String extractRange(String start,String end) {
		if(StringUtils.isBlank(start) || StringUtils.isBlank(end)) throw new IllegalArgumentException("invalid boundaries");
		String substring = StringUtils.substringBetween(alphabet, start, end);
		substring = start.concat(substring).concat(end);
		return substring;
	}
	
	public static char[] extractToChar(String start, String end) {
		String range = extractRange(start, end);
		return range.toCharArray();
	}
}
