package org.adorsys.adbase.security;

import java.security.Principal;
import java.util.List;

import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.repo.LoginRepository;
import org.adorsys.adcore.auth.TermWsUserPrincipal;

@Stateless
public class SecurityUtil {

	@Resource
	private SessionContext sessionContext;

	@Inject
	private LoginRepository loginRepository;
	
	private TermWsUserPrincipal getCallerPrincipal(){
		Principal callerPrincipal = sessionContext.getCallerPrincipal();
		if(callerPrincipal==null || !(callerPrincipal instanceof TermWsUserPrincipal)) return null;
		return (TermWsUserPrincipal) callerPrincipal;
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
}
