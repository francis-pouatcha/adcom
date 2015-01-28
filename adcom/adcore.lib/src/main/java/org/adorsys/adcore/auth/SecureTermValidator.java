package org.adorsys.adcore.auth;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.adorsys.adcore.utils.AccessTimeValidator;
import org.apache.commons.lang3.StringUtils;

public class SecureTermValidator {

	public static boolean validate(TermWsUserPrincipal termPrincipal, HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		response.addHeader("X-TERM-NAME", termPrincipal.getTermName());

		String accessTime = termPrincipal.getAccessTime();
		if(StringUtils.isNotBlank(accessTime)){
			String timeZone = termPrincipal.getTimeZone();
			if(StringUtils.isBlank(timeZone)) return false;
			boolean good = AccessTimeValidator.check(accessTime, timeZone);
			if(!good) return false;
		}
		
		String locality = termPrincipal.getLocality();
		
		if(StringUtils.isNotBlank(locality)){
			// Check locality
		}
		
		String macAddress = termPrincipal.getMacAddress();
		if(StringUtils.isNotBlank(macAddress)){
			// validate mac address
		}

		return true;
	}
}
