package org.adorsys.adcore.auth;

import static org.jboss.web.CatalinaMessages.MESSAGES;

import java.io.IOException;
import java.security.Principal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Request;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.realm.GenericPrincipal;
import org.apache.catalina.util.DateTool;
import org.apache.commons.lang3.StringUtils;

/**
 * Enhanced authenticator, that collects terminal and user informations from the
 * request and allow for a double authentication.
 * 
 * Each connection is associated with a terminal. The terminal authentication
 * occurs by the mean of either a client certificate, or a secure cookies. In
 * the case of a client certificate, the publicKeyId is used to keep track of
 * the client terminal. In the case of a secure cookie, we use the cookie value
 * as a key.
 * 
 * Each terminal can host at most one user at a time. Once the terminal
 * authenticated we proceed with the user authentication. User authentication
 * information are available in the basic auth header. This are either a
 * userName/password pair, or an authToken.
 * 
 * The principal will have the format:
 * {'login':'loginName','terminal':'terminalId'}
 * 
 * Where the loginName is the login name of the user currently working on this
 * terminal and the terminal id is the identifier of the terminal.
 * 
 * The login name must not be the login alias. The login alias is what the user
 * uses to authenticate with the system. The user can change his login alias,
 * but not the login name.
 * 
 * This is the same for the terminalID. This does not change. But the terminal
 * key the the terminal uses to authenticate with the application can change.
 * The terminal key is either the secure cookie or the publickeyId of the key
 * pair used by the terminal to authenticate with the application.
 * 
 * @author francis
 * 
 */
public class AdcomAuthValve extends AdcomAuthBase {

	private Map<String, GenericPrincipal> authPrincipals = new HashMap<String, GenericPrincipal>();
	
	@Override
	protected boolean authenticate(Request request,
            HttpServletResponse response,
            LoginConfig config) throws IOException {

		TermCdtl termCdtl = TermCdtlUtil.readTermCredential(request);
		if(!termCdtl.hasTermCredential()){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,MESSAGES.missingRequestCertificate());
			return (false);
		}

		// after successful authentication of terminal, authenticate user.
    	AuthParams suppliedAuthParams = AuthParamsUtil.readAuthHeader(request);
		String opr = suppliedAuthParams.getOpr();
    	
		GenericPrincipal existingGenericPrincipal = authPrincipals.get(suppliedAuthParams.toIdString());
    	if(existingGenericPrincipal!=null)request.setUserPrincipal(existingGenericPrincipal);

    	Principal authenticated = context.getRealm().authenticate(suppliedAuthParams.toString(), termCdtl.toString());
    	
    	GenericPrincipal generatedGenericPrincipal = null;
    	if(authenticated==null){
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED,MESSAGES.authenticationFailure());
			return (false);
    	} else if (authenticated==existingGenericPrincipal){// cached
    		return (true);
    	} else if (authenticated instanceof GenericPrincipal){
    		generatedGenericPrincipal = (GenericPrincipal) authenticated;
    		request.setUserPrincipal(generatedGenericPrincipal);
    	} else {
			throw new IllegalStateException("Returned principal is instance of : " + authenticated.getClass().getName() + " We are expecting an instance of : " + GenericPrincipal.class.getName());
    	}
    	
    	if(generatedGenericPrincipal.getUserPrincipal()==null)
			throw new IllegalStateException("Returned principal does not contain a user principal.");
    	if(!(generatedGenericPrincipal.getUserPrincipal() instanceof TermWsUserPrincipal))
			throw new IllegalStateException("Included user principal is instance of : " + generatedGenericPrincipal.getUserPrincipal().getClass().getName() + " We are expecting an instance of : " + TermWsUserPrincipal.class.getName());
    	TermWsUserPrincipal generatedUserPrincipal = (TermWsUserPrincipal) generatedGenericPrincipal.getUserPrincipal();
    	
    	
    	setSessionHeader(generatedUserPrincipal, response);

		AuthParams generatedAuthParams = new AuthParams(generatedUserPrincipal.getTermSessionId(), generatedUserPrincipal.getUserSessionId());
    	if(OpId.login.name().equals(opr)){
    		updatePrincipal(suppliedAuthParams, generatedAuthParams, generatedGenericPrincipal);
    		noCache(request,response);
    	} else if (OpId.wsin.name().equals(opr)){
    		updatePrincipal(suppliedAuthParams, generatedAuthParams, generatedGenericPrincipal);
//    		clearSsoCookie(request, response);
    		noCache(request,response);
    	} else if (OpId.wsout.name().equals(opr)) {
    		clearPrincipal(suppliedAuthParams, generatedGenericPrincipal);
//    		setSsoCookie(generatedUserPrincipal, request, response);
    		noCache(request,response);
    	} else if (OpId.req.name().equals(opr) || OpId.refresh.name().equals(opr)) {
    		updatePrincipal(suppliedAuthParams, generatedAuthParams, generatedGenericPrincipal);
    		privateCache(request,response);
    	} else {
    		clearPrincipal(suppliedAuthParams, generatedGenericPrincipal);
    	}
		return (true);
	}

	@Override
	public void login(Request request, String username, String password)
			throws ServletException {
		throw new UnsupportedOperationException();
	}

	@Override
	public void logout(Request request) throws ServletException {

        // Remove the authentication information from our request
        request.setAuthType(null);
        request.setUserPrincipal(null);
	}
	
    /**
     * "Expires" header always set to Date(1), so generate once only
     */
    private static final String DATE_ONE =
        (new SimpleDateFormat(DateTool.HTTP_RESPONSE_DATE_HEADER,
                              Locale.US)).format(new Date(1));
	private void noCache(Request request, HttpServletResponse response){
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
	        response.setHeader("Pragma", "No-cache");
	        response.setHeader("Cache-Control", "no-cache");    		
	        response.setHeader("Expires", DATE_ONE);
        }
	}
	private void privateCache(Request request, HttpServletResponse response){
        if (!"POST".equalsIgnoreCase(request.getMethod())) {
	        response.setHeader("Cache-Control", "private");
	        response.setHeader("Expires", DATE_ONE);
        }
	}
//	private void clearSsoCookie(Request request, HttpServletResponse response){
//		SecureSSOCookie secureSSOCookie = new SecureSSOCookie();
//		Cookie secureSsoCookie = secureSSOCookie.selectSecureCookie(request.getCookies());
//		if(secureSsoCookie!=null){
//			secureSsoCookie.setMaxAge(0);
//    		response.addCookie(secureSsoCookie);
//		}
//	}
//	private void setSsoCookie(TermWsUserPrincipal userPrincipal, Request request, HttpServletResponse response){
//		SecureSSOCookie secureSSOCookie = new SecureSSOCookie();
//		Cookie secureSsoCookie = secureSSOCookie.setSecureCookie(userPrincipal.getUserSessionId(), request.getServerName(), 300);
//		response.addCookie(secureSsoCookie);
//	}	
	private void updatePrincipal(AuthParams suppliedAuthParams, AuthParams generatedAuthParams,GenericPrincipal genericPrincipal ){
		if(!StringUtils.equals(suppliedAuthParams.toIdString(), generatedAuthParams.toIdString())){
	    	authPrincipals.remove(suppliedAuthParams.toIdString());
	    	authPrincipals.put(generatedAuthParams.toIdString(), genericPrincipal);
		} else {
			if(!authPrincipals.containsKey(generatedAuthParams.toIdString()))
			    authPrincipals.put(generatedAuthParams.toIdString(), genericPrincipal);
		}
	}
	private void setSessionHeader(TermWsUserPrincipal userPrincipal, HttpServletResponse response){
		response.addHeader("X-USER-LOGIN", userPrincipal.getLoginName());
		response.addHeader("X-USER-SESSION", userPrincipal.getUserSessionId());
		response.addHeader("X-TERM-SESSION", userPrincipal.getTermSessionId());		
	}

	private void clearPrincipal(AuthParams suppliedAuthParams,
			GenericPrincipal generatedGenericPrincipal) {
    	authPrincipals.remove(suppliedAuthParams.toIdString());
	}
}
