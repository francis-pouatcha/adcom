package org.adorsys.adbase.auth;

import java.math.BigInteger;
import java.util.Date;
import java.util.UUID;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.SecTermSession;
import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.jpa.SecUserSession;
import org.adorsys.adbase.rest.ConnectionHistoryEJB;
import org.adorsys.adbase.rest.LoginEJB;
import org.adorsys.adbase.rest.SecTermSessionEJB;
import org.adorsys.adbase.rest.SecUserSessionEJB;
import org.adorsys.adcore.auth.OpId;
import org.adorsys.adcore.auth.TermCdtl;
import org.adorsys.adcore.auth.TermWsUserPrincipal;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

/**
 * Used to cover tx boundaries in the login module.
 * 
 * @author francis
 *
 */
@Stateless
public class LoginModuleTx {
	
	@Inject
	private ConnectionHistoryEJB connectionHistoryEJB;
	
	@Inject
	private SecUserSessionEJB secUserSessionEJB;
	
	@Inject
	private SecTermSessionEJB secTermSessionEJB;
	
	@Inject
	private LoginEJB loginEJB;

	public TermWsUserPrincipal login_login(SecUserSession existingUserSession, 
			SecTermSession secTermSession, SecTerminal secTerminal, 
			TermCdtl termCdtl, Date currentDate, String ouId, String workspaceId, String langIso2){
		
		if(existingUserSession!=null && StringUtils.isNotBlank(existingUserSession.getId())){
			secUserSessionEJB.deleteById(existingUserSession.getId());
		}

		SecUserSession secUserSession = new SecUserSession();
		secUserSession.setCreated(currentDate);
		secUserSession.setExpires(DateUtils.addDays(currentDate, 1));
		secUserSession.setId(UUID.randomUUID().toString());
		secUserSession.setLoginName(secTermSession.getLoginName());
		secUserSession.setLangIso2(langIso2);
		secUserSession.setTermSessionId(secTermSession.getId());
		secUserSession.setOuId(ouId);
		// Setin upthe workspace identifier 
		secUserSession.setWorkspaceId(workspaceId);

		secUserSession = secUserSessionEJB.create(secUserSession);

		// Update terminal data
		secTermSession.setLoginName(secTermSession.getLoginName());
		secTermSession.setUserSession(secUserSession.getId());
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
				currentDate, secUserSession.getLoginName(), secUserSession.getOuId(),
				termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), secUserSession.getWorkspaceId(),OpId.login.name());
		connectionHistoryEJB.create(connectionHistory);
		
		// instantiate principal
		TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
		newPrincipal.setAccessTime(secTerminal.getAccessTime());
		newPrincipal.setLocality(secTerminal.getLocality());
		newPrincipal.setLoginName(secTermSession.getLoginName());
		newPrincipal.setLangIso2(secUserSession.getLangIso2());
		newPrincipal.setLoginTime(currentDate);
		newPrincipal.setMacAddress(secTerminal.getMacAddress());
		newPrincipal.setTermCred(termCdtl.getCre());
		newPrincipal.setTermName(secTerminal.getTermName());
		newPrincipal.setTermSessionId(secTermSession.getId());
		newPrincipal.setTimeZone(secTerminal.getTimeZone());
		newPrincipal.setUserSessionId(secUserSession.getId());
		newPrincipal.setWorkspaceId(secUserSession.getWorkspaceId());
		return newPrincipal;
	}
	
	public TermWsUserPrincipal login_wsin(SecUserSession existingUserSession, SecTermSession secTermSession, 
			SecTerminal secTerminal, TermCdtl termCdtl, Date currentDate){
		SecUserSession secUserSession = new SecUserSession();
		secUserSession.setCreated(currentDate);
		secUserSession.setExpires(DateUtils.addDays(currentDate, 1));
		secUserSession.setId(UUID.randomUUID().toString());
		secUserSession.setLoginName(existingUserSession.getLoginName());
		secUserSession.setLangIso2(existingUserSession.getLangIso2());
		secUserSession.setTermSessionId(secTermSession.getId());
		secUserSession.setWorkspaceId(existingUserSession.getWorkspaceId());
		secUserSession.setOuId(existingUserSession.getOuId());
		secUserSessionEJB.deleteById(existingUserSession.getId());
		secUserSession = secUserSessionEJB.create(secUserSession);

		// Update terminal data
		secTermSession.setLoginName(secUserSession.getLoginName());
		secTermSession.setUserSession(secUserSession.getId());
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
				currentDate, secUserSession.getLoginName(), secUserSession.getOuId(),
				termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), secUserSession.getWorkspaceId(),OpId.wsin.name());
		connectionHistoryEJB.create(connectionHistory);
		
		// instantiate principal
		TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
		newPrincipal.setAccessTime(secTerminal.getAccessTime());
		newPrincipal.setLocality(secTerminal.getLocality());
		newPrincipal.setLoginName(secUserSession.getLoginName());
		newPrincipal.setLangIso2(secUserSession.getLangIso2());
		newPrincipal.setLoginTime(currentDate);
		newPrincipal.setMacAddress(secTerminal.getMacAddress());
		newPrincipal.setTermCred(termCdtl.getCre());
		newPrincipal.setTermName(secTerminal.getTermName());
		newPrincipal.setTermSessionId(secTermSession.getId());
		newPrincipal.setTimeZone(secTerminal.getTimeZone());
		newPrincipal.setUserSessionId(secUserSession.getId());
		newPrincipal.setWorkspaceId(secUserSession.getWorkspaceId());
		return newPrincipal;
		
	}

	public TermWsUserPrincipal login_wsout(SecUserSession existingUserSession, SecTermSession secTermSession, 
			SecTerminal secTerminal, TermCdtl termCdtl, Date currentDate, String newWorkspace){
		
		String newUserSessionIdString = UUID.randomUUID().toString();
		SecUserSession newUserSession = new SecUserSession();
		newUserSession.setId(newUserSessionIdString);
		newUserSession.setCreated(new Date());
		newUserSession.setExpires(DateUtils.addDays(new Date(), 1));
		newUserSession.setTermSessionId(secTermSession.getId());
		newUserSession.setLoginName(existingUserSession.getLoginName());
		newUserSession.setLangIso2(existingUserSession.getLangIso2());
		newUserSession.setOuId(existingUserSession.getOuId());
		newUserSession.setWorkspaceId(newWorkspace);
		secUserSessionEJB.deleteById(existingUserSession.getId());
		newUserSession = secUserSessionEJB.create(newUserSession);
		
		// Update terminal data
		secTermSession.setLoginName(newUserSession.getLoginName());
		secTermSession.setUserSession(newUserSession.getId());
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
				currentDate, newUserSession.getLoginName(), newUserSession.getOuId(),
				termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), newUserSession.getWorkspaceId(),OpId.wsout.name());
		connectionHistoryEJB.create(connectionHistory);
		
		// instantiate principal
		TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
		newPrincipal.setAccessTime(secTerminal.getAccessTime());
		newPrincipal.setLocality(secTerminal.getLocality());
		newPrincipal.setLoginName(newUserSession.getLoginName());
		newPrincipal.setLangIso2(newUserSession.getLangIso2());
		newPrincipal.setLoginTime(currentDate);
		newPrincipal.setMacAddress(secTerminal.getMacAddress());
		newPrincipal.setTermCred(termCdtl.getCre());
		newPrincipal.setTermName(secTerminal.getTermName());
		newPrincipal.setTermSessionId(secTermSession.getId());
		newPrincipal.setTimeZone(secTerminal.getTimeZone());
		newPrincipal.setUserSessionId(newUserSession.getId());
		newPrincipal.setWorkspaceId(newUserSession.getWorkspaceId());
		return newPrincipal;
	}
	
	public TermWsUserPrincipal login_logout(TermWsUserPrincipal existingPrincipal, 
			SecUserSession existingUserSession, SecTermSession secTermSession,
			SecTerminal secTerminal, TermCdtl termCdtl, Date currentDate){
		
		if(existingUserSession!=null && StringUtils.isNotBlank(existingUserSession.getId())){
			secUserSessionEJB.deleteById(existingUserSession.getId());

			ConnectionHistory connectionHistory = newConnectionHistory(BigInteger.ZERO, currentDate, secTermSession.getIpAddress(),
					currentDate, existingUserSession.getLoginName(), existingUserSession.getOuId(),
					termCdtl.getCre(), secTerminal.getTermName(),secTerminal.getTermId(), existingUserSession.getWorkspaceId(),OpId.logout.name());
			connectionHistoryEJB.create(connectionHistory);
		}
		
		// Update terminal data
		secTermSession.setLoginName(null);
		secTermSession.setUserSession(null);
		secTermSession = secTermSessionEJB.update(secTermSession);
		
		if(existingPrincipal!=null){
			existingPrincipal.setLoginName(null);
			existingPrincipal.setLoginTime(currentDate);
			existingPrincipal.setUserSessionId(null);
			existingPrincipal.setTermSessionId(secTermSession.getId());
			existingPrincipal.setWorkspaceId(existingUserSession.getWorkspaceId());
			return existingPrincipal;
		} else {
			// instantiate principal
			TermWsUserPrincipal newPrincipal = new TermWsUserPrincipal();
			newPrincipal.setAccessTime(secTerminal.getAccessTime());
			newPrincipal.setLocality(secTerminal.getLocality());
			newPrincipal.setLoginName(null);
			newPrincipal.setLoginTime(currentDate);
			newPrincipal.setMacAddress(secTerminal.getMacAddress());
			newPrincipal.setTermCred(termCdtl.getCre());
			newPrincipal.setTermName(secTerminal.getTermName());
			newPrincipal.setTermSessionId(secTermSession.getId());
			newPrincipal.setTimeZone(secTerminal.getTimeZone());
			newPrincipal.setUserSessionId(null);
			newPrincipal.setWorkspaceId(existingUserSession.getWorkspaceId());
			
			return newPrincipal;
		}
		
	}

	public void logWrongPassword(Login login, String ipAdress,
			String termCdtl, String terminalName, String terminalId, String workspaceId,
			Date currentDate) {
		ConnectionHistory history = connectionHistoryEJB.findByLoginName(login.getLoginName());
		if (history != null) {
			if (BigInteger.valueOf(3).compareTo(history.getCounter()) <= 0) {
				login.setAccountLocked(Boolean.TRUE);
				login = loginEJB.update(login);
				newConnectionHistory(history.getCounter().add(BigInteger.ONE), history.getFirstLoginDate(), 
						ipAdress, currentDate, login.getLoginName(), login.getOuIdentif(), termCdtl, 
						terminalName, terminalId, workspaceId, OpId.login.name());
			}
		} else {
			newConnectionHistory(BigInteger.ONE, currentDate, 
					ipAdress, currentDate, login.getLoginName(), login.getOuIdentif(), termCdtl, 
					terminalName, terminalId, workspaceId, OpId.login.name());
		}
	}
	
	private ConnectionHistory newConnectionHistory(BigInteger counter,
			Date firstLoginDate, String ipAdress, Date lastLoginDate,
			String loginName, String ouId, String termCdtl,
			String terminalName, String terminalId, String workspaceId, String opr) {
		ConnectionHistory h = new ConnectionHistory();
		h.setCounter(counter);
		h.setFirstLoginDate(firstLoginDate);
		h.setIpAdress(ipAdress);
		h.setLastLoginDate(lastLoginDate);
		h.setLoginName(loginName);
		h.setOuId(ouId);
		h.setTermCdtl(termCdtl);
		h.setTerminalName(terminalName);
		h.setWorkspaceId(workspaceId);
		h.setTerminalId(terminalId);
		h.setOpr(opr);
		return h;
	}
	
}
