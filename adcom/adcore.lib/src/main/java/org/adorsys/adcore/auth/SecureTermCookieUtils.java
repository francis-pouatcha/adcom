package org.adorsys.adcore.auth;


public class SecureTermCookieUtils extends AbstractSecureDomainCookie {

	public static final String SECURE_COOKIE_NAME = "JSSESINOID";

	@Override
	public String getCookieName() {
		return SECURE_COOKIE_NAME;
	}
}
