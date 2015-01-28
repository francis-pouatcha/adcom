package org.adorsys.adcore.auth;

import javax.servlet.http.Cookie;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractSecureDomainCookie {

	public abstract String getCookieName();
	
	public Cookie selectSecureCookie(Cookie[] cookies){
		if(cookies==null) return null;
		for (Cookie cookie : cookies) {
			if(StringUtils.equals(getCookieName(), cookie.getName())){
				return cookie;
			}
		}
		return null;
	}

	// 365*24*60*60);// 365 days
	public Cookie setSecureCookie(String cookieStr, String serverName, int expiry){
		// TODO secure hash the cookie.
		Cookie cookie = new Cookie(getCookieName(), cookieStr);
		// Makes sure that this will not be sent over a unsecure channel
		cookie.setSecure(true);
		cookie.setHttpOnly(true);
		cookie.setDomain(serverName);
		cookie.setMaxAge(expiry);
		cookie.setPath("/");	
		return cookie;
	}
}
