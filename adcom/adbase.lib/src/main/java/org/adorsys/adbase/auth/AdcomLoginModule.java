package org.adorsys.adbase.auth;

import java.io.IOException;
import java.security.Principal;
import java.security.acl.Group;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;
import javax.security.jacc.PolicyContext;
import javax.security.jacc.PolicyContextException;
import javax.servlet.http.HttpServletRequest;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.SecTermCredtl;
import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.SecUserSession;
import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.rest.LoginEJB;
import org.adorsys.adbase.rest.SecTermCredtlEJB;
import org.adorsys.adbase.rest.SecTermSessionEJB;
import org.adorsys.adbase.rest.SecTerminalEJB;
import org.adorsys.adbase.rest.SecUserSessionEJB;
import org.adorsys.adbase.rest.UserWorkspaceEJB;
import org.adorsys.adcore.auth.AuthParams;
import org.adorsys.adcore.auth.OpId;
import org.adorsys.adcore.auth.TermCdtl;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.adorsys.adcore.utils.AccessTimeValidator;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.jboss.security.SecurityConstants;
import org.jboss.security.SimpleGroup;
import org.jboss.security.SimplePrincipal;

public class AdcomLoginModule implements LoginModule {

	/** The subject. */
	private Subject subject;

	/** The callback handler. */
	private CallbackHandler callbackHandler;

	/**
	 * Flag indicating if the login phase succeeded. Subclasses that override
	 * the login method must set this to true on successful completion of login
	 */
	protected boolean loginOk;

	private LoginEJB loginEJB;
	private SecTermSessionEJB secTermSessionEJB;
	private SecTermCredtlEJB secTermCredtlEJB;
	private SecTerminalEJB secTerminalEJB;
	private SecUserSessionEJB secUserSessionEJB;
	private UserWorkspaceEJB userWorkspaceEJB;
	private LoginModuleTx loginModuleTx;

	private TermWsUserPrincipal newPrincipal;
//	
//	private String opr = null;

	@Override
	public void initialize(Subject subject, CallbackHandler callbackHandler,
			Map<String, ?> sharedState, Map<String, ?> options) {
		this.subject = subject;
		this.callbackHandler = callbackHandler;
		// this.sharedState = sharedState;
		// this.options = options;

		InitialContext initialContext;
		try {
			initialContext = new InitialContext();
			loginEJB = (LoginEJB) initialContext.lookup("java:module/LoginEJB");
			secTermSessionEJB = (SecTermSessionEJB) initialContext
					.lookup("java:module/SecTermSessionEJB");
			secTermCredtlEJB = (SecTermCredtlEJB) initialContext
					.lookup("java:module/SecTermCredtlEJB");
			secTerminalEJB = (SecTerminalEJB) initialContext
					.lookup("java:module/SecTerminalEJB");
			secUserSessionEJB = (SecUserSessionEJB) initialContext
					.lookup("java:module/SecUserSessionEJB");
			userWorkspaceEJB = (UserWorkspaceEJB) initialContext
					.lookup("java:module/UserWorkspaceEJB");
			loginModuleTx = (LoginModuleTx) initialContext
					.lookup("java:module/LoginModuleTx");
		} catch (NamingException e1) {
			throw new IllegalStateException(e1);
		}
	}

	@Override
	public boolean login() throws LoginException {
		Date currentDate = new Date();

		HttpServletRequest request = null;
		try {
			request = (HttpServletRequest) PolicyContext
					.getContext(HttpServletRequest.class.getName());
		} catch (PolicyContextException e) {
			throw new IllegalStateException(e);
		}

		/*
		 * We generally have two situations at the entrance of the system.
		 * - Either a client is active and contains some in memory informations that can be
		 * sent to the server through the basic auth header [termSessionId and userSessionId?]
		 * - Generally if the termSessionId is not available, we need to rely on other request
		 * information to identify and authenticate the request.
		 */
		// after successful authentication of terminal, authenticate user.
		AuthParams auth = BasicAuthUtils.readAuthHeader(request);
		TermCdtl termCdtl = readTermCredtl();
//		opr = auth.getOpr();
		
		SecTermSession secTermSession = readTerminalSession(auth, termCdtl, request);
		if(secTermSession==null) return false;
		SecTerminal secTerminal = readAndCheckSecTerminal(secTermSession, request, currentDate);

		TermWsUserPrincipal existingPrincipal = (TermWsUserPrincipal) request.getUserPrincipal();
		if(existingPrincipal!=null && !StringUtils.equals(existingPrincipal.getUserSessionId(), secTermSession.getUserSession()))
			return returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.WRONG_USER_SESSION_ERROR.name());
			
		// So far terminal valid
		SecUserSession existingUserSession = null;
		if(StringUtils.isNotBlank(secTermSession.getUserSession()))existingUserSession = secUserSessionEJB.findById(secTermSession.getUserSession());
		
		// login
		if (OpId.login.name().equals(auth.getOpr())) {
			
			// quit existing session.
			if(existingPrincipal!=null && !StringUtils.equals(existingPrincipal.getLoginName(), auth.getLgn()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.USER_SESSION_ACTIVE_ERROR.name());
			
			// Quit if session id exist. If there is a user session client must logout first.
			if(existingUserSession!=null && !StringUtils.equals(existingUserSession.getLoginName(), auth.getLgn()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.USER_SESSION_ACTIVE_ERROR.name() + "," + existingUserSession.getLoginName());

			if (StringUtils.isBlank(auth.getLgn()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.MISSING_LOGIN_NAME_ERROR.name());

			Login login = loginEJB.findByLoginAlias(auth.getLgn());
			if (login == null)
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.NO_LOGIN_WITH_ALIAS_ERROR.name());
			
			if (login.getAccountExpir() != null
					&& currentDate.after(login.getAccountExpir()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.ACCOUNT_EXPIRATION_ERROR.name());
			if (login.getCredtlExpir() != null
					&& currentDate.after(login.getCredtlExpir()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.CREDENTIAL_EXPIRATION_ERROR.name());
			if (login.getDisableLogin()!=null && Boolean.TRUE.equals(login.getDisableLogin()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.DISEABLE_LOGIN_ERROR.name());
			if (login.getAccountLocked()!=null && Boolean.TRUE.equals(login.getAccountLocked()))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.ACCOUNT_LOCKED_ERROR.name());

			String password = auth.getPwd();
			// check password
			boolean pwdOk = PasswordChecker.checkPassword(password,
					login.getPwdHashed());
			String ipAddress = request != null ? request.getRemoteAddr() : null;
			
			secTermSession.setIpAddress(ipAddress);
			secTermSession.setLoginName(login.getLoginName());
			
			if (!pwdOk) {
				loginModuleTx.logWrongPassword(login, ipAddress, termCdtl.getCre(),
						secTerminal.getTermName(), secTerminal.getTermId(), login.toLoginWorkspace(), currentDate);
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.BAD_CREDENTIAL.name());
			}

			// create user session
			newPrincipal = loginModuleTx.login_login(existingUserSession, 
					secTermSession, secTerminal, termCdtl, currentDate, login.getOuIdentif(), login.toLoginWorkspace());

			return (true);
		}

		if (OpId.wsin.name().equals(auth.getOpr())) {
			// quit existing session.
			if(existingPrincipal!=null)
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.USER_SESSION_ACTIVE_ERROR.name());
			
			String existingUserSessionId = auth.getUsr();
			if(existingUserSessionId==null)
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.NO_USER_SESSION.name());
			
			// Quit if session id exist. If there is a user session client must logout first.
			if(existingUserSession==null || !existingUserSession.getId().equals(existingUserSessionId))
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.WRONG_USER_SESSION_ERROR.name());

			newPrincipal = loginModuleTx.login_wsin(existingUserSession, secTermSession, secTerminal, termCdtl, currentDate);

			return (true);
		}
		
		// ws change cookie
		if (OpId.wsout.name().equals(auth.getOpr())) {
			if(existingUserSession==null)
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.MISSING_SESSION_ERROR.name());
			
			// Quit if session id db
			if(!StringUtils.equals(existingUserSession.getId(), auth.getUsr()))
				return returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.WRONG_USER_SESSION_ERROR.name());

			// Verify that the user has access to that workspace.
			if(!Login.loginWorkspace(auth.getWrk())){
				UserWorkspace userWorkspace = userWorkspaceEJB.findByIdentif(auth.getWrk(), currentDate);
				if(userWorkspace==null)
					return returnWithAttr(request, "AUTH-ERROR",
							MessagesKeys.WRONG_WORKSPACE_ERROR.name());
			}
			
			newPrincipal = loginModuleTx.login_wsout(existingUserSession, secTermSession, secTerminal, termCdtl, currentDate, auth.getWrk());

			return (true);
		}

		if (OpId.logout.name().equals(auth.getOpr())) {

			newPrincipal = loginModuleTx.login_logout(existingPrincipal, existingUserSession, secTermSession, secTerminal, termCdtl, currentDate);

			return (true);
		}

		if (OpId.req.name().equals(auth.getOpr())) {
			if(existingUserSession==null)
				return returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.MISSING_SESSION_ERROR.name());
			
			// Quit if session id db
			if(!StringUtils.equals(existingUserSession.getId(), auth.getUsr()))
				return returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.WRONG_USER_SESSION_ERROR.name());
			
			if(existingPrincipal!=null){
				newPrincipal = existingPrincipal;
			} else {
				// instantiate principal
				newPrincipal = new TermWsUserPrincipal();
				newPrincipal.setAccessTime(secTerminal.getAccessTime());
				newPrincipal.setLocality(secTerminal.getLocality());
				newPrincipal.setLoginName(existingUserSession.getLoginName());
				newPrincipal.setLoginTime(currentDate);
				newPrincipal.setMacAddress(secTerminal.getMacAddress());
				newPrincipal.setTermCred(termCdtl.getCre());
				newPrincipal.setTermName(secTerminal.getTermName());
				newPrincipal.setTermSessionId(secTermSession.getId());
				newPrincipal.setTimeZone(secTerminal.getTimeZone());
				newPrincipal.setUserSessionId(existingUserSession.getId());
				newPrincipal.setWorkspaceId(existingUserSession.getWorkspaceId());
			}
			return (true);
		}

		return (false);
	}

	@Override
	public boolean commit() throws LoginException {
		if (newPrincipal == null) return false;
		
		/*
		 * The set of principals of this subject. We will add the
		 * SecurityConstants.CALLER_PRINCIPAL_GROUP and the
		 * SecurityConstants.ROLES_GROUP to this set.
		 */
		Set<Principal> principals = subject.getPrincipals();

		
		/*
		 * The user identity.
		 */
		if(!principals.contains(newPrincipal))
			principals.add(newPrincipal);

		// get the Roles group
		Group[] roleSets = getRoleSets();
		for (Group group : roleSets) {
			Group subjectGroup = findGroup(group.getName(), principals);
			if (subjectGroup == null) {
				subjectGroup = new SimpleGroup(group.getName());
				principals.add(subjectGroup);
			}
			// Copy the group members to the Subject group
			Enumeration<? extends Principal> members = group.members();
			while (members.hasMoreElements()) {
				Principal role = (Principal) members.nextElement();
				subjectGroup.addMember(role);
			}
		}
		return true;
	}

	@Override
	public boolean abort() throws LoginException {
		newPrincipal = null;
		return true;
	}

	@Override
	public boolean logout() throws LoginException {
		newPrincipal = null;
		// Remove all principals and groups of classes defined here.
		Set<Principal> principals = subject.getPrincipals();
		Set<SimplePrincipal> principals2Remove = subject.getPrincipals(SimplePrincipal.class);
		principals.removeAll(principals2Remove);
//		TermWsUserPrincipal
		Set<TermWsUserPrincipal> principals2Remove2 = subject.getPrincipals(TermWsUserPrincipal.class);
		principals.removeAll(principals2Remove2);
		return true;
	}

	private boolean returnWithAttr(HttpServletRequest request, String key,
			String value) {
		if (request != null)
			request.setAttribute(key, value);
		return false;
	}
	
	private boolean checkTermAccessTime(HttpServletRequest request, String accessTime, String timeZone){
		if(StringUtils.isBlank(accessTime) || StringUtils.isBlank(timeZone)) return true;
		if(!AccessTimeValidator.check(accessTime, timeZone))
			return returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_ACCESS_TIME_ERROR.name());
		return true;
	}
	
	private boolean checkLocality(HttpServletRequest request, String locality){
		if(StringUtils.isBlank(locality)) return true;
		
		// check locality
		
		return true;
	}

	private boolean checkValidity(HttpServletRequest request, Date validFrom, Date validTo, Date currentDate){
		if((validFrom!=null && currentDate.before(validFrom)) || (validTo!=null && currentDate.after(validTo)))
			return returnWithAttr(request, "AUTH-ERROR",MessagesKeys.TERM_VALIDITY_ERROR.name());
		return true;
	}
	
	private TermCdtl readTermCredtl(){
		NameCallback nameCallback = new NameCallback("Enter your user name: ");
		PasswordCallback passwordCallback = new PasswordCallback(
				"Enter your password", false);
		try {
			callbackHandler.handle(new Callback[] { nameCallback,
					passwordCallback });
		} catch (IOException e) {
			throw new IllegalStateException(e);
		} catch (UnsupportedCallbackException e) {
			throw new IllegalStateException(e);
		}
		char[] termPwd = passwordCallback.getPassword();
		String termPwdStr = new String(termPwd);
		return TermCdtl.fromString(termPwdStr);
	}
	
	private SecTermSession readTerminalSession(AuthParams auth, TermCdtl termCdtl, HttpServletRequest request){
		String opr = auth.getOpr();
		String providedTermCred = termCdtl.getCre();
		SecTermCredtl secTermCredtl = secTermCredtlEJB.findById(providedTermCred);
		if (secTermCredtl == null){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_CRED_NOT_FOUND_ERROR.name());
			return null;
		}

		if (StringUtils.isBlank(auth.getTrm())){
			// This situation can only happen in the case of a login or workspace in.
			if(!OpId.login.name().equals(opr)){
				returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.NO_TERM_SESSION_ERROR.name());
				return null;
			}
		}
		
		SecTermSession secTermSession = null;

		String storedTermSessionId = secTermCredtl.getTermSessionId();
		// Means there is no session associated with this terminal
		if(StringUtils.isNotBlank(auth.getTrm())){
			if(!StringUtils.equals(storedTermSessionId,auth.getTrm())){
				// This can not be.
				returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.WRONG_TERM_SESSION_ERROR.name());
				return null;
			}
			secTermSession = secTermSessionEJB.findById(auth
					.getTrm());
			if (secTermSession != null) return secTermSession;
			
			returnWithAttr(request, "AUTH-ERROR",
				MessagesKeys.NO_TERM_SESSION_ERROR.name());
			return null;
		}

		if(StringUtils.isBlank(storedTermSessionId)){
			if(StringUtils.isBlank(termCdtl.getCert())){
				returnWithAttr(request, "AUTH-ERROR",
						MessagesKeys.NO_TERM_SESSION_ERROR.name());
				return null;
			} 
			// create the term session
			Date now = new Date();
			secTermSession = new SecTermSession();
			secTermSession.setTermName(secTermCredtl.getTermName());
			secTermSession.setCreated(now);
			secTermSession.setExpires(DateUtils.addYears(now, 1));
			secTermSession.setTermId(secTermCredtl.getTermId());
			secTermSession.setTermCredtl(secTermCredtl.getId());
			secTermSession = secTermSessionEJB.create(secTermSession);
			storedTermSessionId = secTermSession.getId();
			secTermCredtl.setTermSessionId(storedTermSessionId);
			secTermCredtlEJB.update(secTermCredtl);
			return secTermSession;
		}

		// Identify the terminal
		secTermSession = secTermSessionEJB.findById(storedTermSessionId);
		if (secTermSession == null){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_CRED_NOT_FOUND_ERROR.name());
			return null;
		}
		// validate the session if not a ssl session
		if(StringUtils.isBlank(termCdtl.getCert()) && !StringUtils.equals(auth.getTky(), secTermSession.getTermKey())){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.TERM_AUTH_ERROR.name());
			return null;
		}
		return secTermSession;
	}
	
	private SecTerminal readAndCheckSecTerminal(SecTermSession secTermSession, HttpServletRequest request, Date currentDate){
		SecTerminal secTerminal = secTerminalEJB.findById(secTermSession.getTermId());
		if (secTerminal == null){
			returnWithAttr(request, "AUTH-ERROR",
					MessagesKeys.UNKNOWN_TERM_ERROR.name());
			return null;
		}

		// Validate terminal
		if(!checkLocality(request, secTerminal.getLocality())) return null;
		if(!checkValidity(request, secTerminal.getValidFrom(), secTerminal.getValidTo(), currentDate)) return null;
		if(!checkTermAccessTime(request, secTerminal.getAccessTime(), secTerminal.getTimeZone())) return null;
		return secTerminal;
	}
	
	/**
	 * Gets the role sets.
	 * 
	 * @return the role sets
	 */
	private Group[] getRoleSets()
	{
		SimpleGroup simpleGroup = new SimpleGroup(SecurityConstants.ROLES_IDENTIFIER);
		simpleGroup.addMember(new SimplePrincipal("LOGIN"));
		return new Group[] { simpleGroup };
	}

	/**
	 * Find group.
	 * 
	 * @param name
	 *            the name
	 * @param principals
	 *            the principals
	 * @return the group
	 */
	private Group findGroup(String name, Set<Principal> principals)
	{
		for (Principal principal : principals)
		{
			if (!(principal instanceof Group))
				continue;
			Group group = Group.class.cast(principal);
			if (name.equals(group.getName()))
				return group;
		}
		return null;
	}
	
}
