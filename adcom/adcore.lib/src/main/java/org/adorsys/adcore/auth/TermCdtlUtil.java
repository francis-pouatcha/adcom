package org.adorsys.adcore.auth;

import java.security.cert.X509Certificate;

import javax.servlet.http.Cookie;

import org.apache.catalina.connector.Request;
import org.apache.commons.lang3.StringUtils;

public class TermCdtlUtil {

	private static SecureTermCookieUtils secureTermCookieUtils = new SecureTermCookieUtils();
	
	public static TermCdtl readTermCredential(Request request){
		TermCdtl termCdtl = new TermCdtl();
        X509Certificate certs[] = request.getCertificateChain();
        if ((certs != null) && (certs.length >= 1)) {
        	// Validate the cert chain and extract the publicKeyId
        	String publicKeyId = validateCert(certs);
        	if(StringUtils.isNotBlank(publicKeyId)){
        		termCdtl.setCre(publicKeyId);
        		termCdtl.setCert(Boolean.TRUE.toString());
        	}
        } else {
        	// read the cookie
    		Cookie secureCookie = secureTermCookieUtils.selectSecureCookie(request.getCookies());
    		if(secureCookie!=null) {
        		termCdtl.setCre(secureCookie.getValue());
        		termCdtl.setCert(Boolean.FALSE.toString());
    		}
        }
        
        return termCdtl;
	}

	private static String validateCert(X509Certificate[] certs) {
		return null;
	}
}
