package org.adorsys.adcore.auth;


public class SecureSSOCookie extends AbstractSecureDomainCookie {

	public static final String SECURE_COOKIE_NAME = "ADSSO";

	@Override
	public String getCookieName() {
		return SECURE_COOKIE_NAME;
	}
}
