package org.adorsys.adcore.auth;

import java.io.UnsupportedEncodingException;

import org.apache.catalina.connector.Request;
import org.apache.commons.lang3.StringUtils;

public class AuthParamsUtil {

    public static final String BASIC_PREFIX = "Basic ";
    
    public static AuthParams readAuthHeader(Request request){
    	String chunks = parse(request);
    	return AuthParams.fromString(chunks);
    }

    private static String parse(Request request){
    	String authorizationHeaderBase64 = request.getHeader("authorization");
    	if(StringUtils.isBlank(authorizationHeaderBase64)) return null;
    	if(!StringUtils.startsWithIgnoreCase(authorizationHeaderBase64, BASIC_PREFIX)) return null;
    	authorizationHeaderBase64 = StringUtils.substringAfter(authorizationHeaderBase64.trim(), " ");
    	byte[] decodeBase64 = org.apache.commons.codec.binary.Base64.decodeBase64(authorizationHeaderBase64);
    	String authorizationHeaderStr;
		try {
			authorizationHeaderStr = StringUtils.toString(decodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			throw new IllegalStateException(e);
		}
    	if(StringUtils.isBlank(authorizationHeaderStr)) return null;
    	return authorizationHeaderStr;
    }
}
