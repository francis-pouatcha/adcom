package org.adorsys.adbase.security;

import java.security.Principal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.repo.LoginRepository;
import org.adorsys.adbase.rest.OrgUnitEJB;
import org.adorsys.adbase.rest.SecUserSessionEJB;
import org.adorsys.adcore.auth.SecurityActions;
import org.adorsys.adcore.auth.TermWsUserPrincipal;

@Stateless
public class SecurityUtil {

	@Resource
	private SessionContext sessionContext;

	@Inject
	private SecUserSessionEJB secUsrSessEjb;
	
	@Inject
	private LoginRepository loginRepository;
	
	@Inject
	private OrgUnitEJB orgUnitEJB;
	
	public TermWsUserPrincipal getCallerPrincipal(){
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if(callerPrincipal!=null && (callerPrincipal instanceof TermWsUserPrincipal))return (TermWsUserPrincipal) callerPrincipal;
		
		callerPrincipal = SecurityActions.getCallerPrincipal();
		if(callerPrincipal!=null && (callerPrincipal instanceof TermWsUserPrincipal))return (TermWsUserPrincipal) callerPrincipal;
		
		return null;
	}
	
	public String getCurrentLoginName() {
		TermWsUserPrincipal callerPrincipal = getCallerPrincipal();
		if(callerPrincipal==null) return null;
		return callerPrincipal.getLoginName();
	}

	public Login getConnectedUser() {
		String loginName = getCurrentLoginName();
		if(loginName==null) throw new IllegalStateException("No user connected");
		List<Login> resultList = loginRepository.findByIdentif(loginName).maxResults(1).getResultList();
		if(resultList.isEmpty()) throw new IllegalStateException("user with name not found: " + loginName);
		return resultList.iterator().next();
	}
	
//	public SecUserSession getCurrentSecUserSession() {
//		TermWsUserPrincipal userPrincipal = getCallerPrincipal();
//		String workspaceId = userPrincipal.getWorkspaceId();
//		SecUserSession secUserSession = secUsrSessEjb.findOneByWorkspaceId(workspaceId, new Date());
//		if(secUserSession == null) {
//			throw new IllegalStateException("The current security session should not be null");
//		}
//		return secUserSession;
//	}
	
	public OrgUnit getCurrentOrgUnit() {
//		SecUserSession currentSecUserSession = getCurrentSecUserSession();
//		String ouId = currentSecUserSession.getOuId();
		TermWsUserPrincipal callerPrincipal = getCallerPrincipal();
		String workspaceId = callerPrincipal.getWorkspaceId();
		OuWorkspace ouWorkspace = OuWorkspace.toOuWorkspace(workspaceId);
		OrgUnit orgUnit = orgUnitEJB.findByIdentif(ouWorkspace.getTargetOuIdentif(), new Date());
		if(orgUnit == null) {
			throw new IllegalStateException("The orgUnit should not be null");
		}
		return orgUnit;
	}

	public String getUserLange() {
		TermWsUserPrincipal userPrincipal = getCallerPrincipal();
		if(userPrincipal!=null)
			return userPrincipal.getLangIso2();
		return getUserLangePrefs().iterator().next();
	}

	public List<String> getUserLangePrefs() {
		return Arrays.asList("fr","en");
	}
}
