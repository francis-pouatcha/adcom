package org.adorsys.adcore.auth;

import java.security.AccessController;
import java.security.Principal;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.security.acl.Group;
import java.util.Enumeration;
import java.util.Set;

import javax.security.auth.Subject;

import org.apache.commons.lang3.StringUtils;
import org.jboss.security.SecurityContext;
import org.jboss.security.SecurityContextAssociation;
public class SecurityActions {

	private static final ThreadLocal<Subject> subjectHolder = new ThreadLocal<Subject>();
	
	static void set(Subject subject){
		if(subject==null) {
			subjectHolder.remove();
		} else {
			subjectHolder.set(subject);
		}
	}
	
	public static TermWsUserPrincipal getCallerPrincipal(){
		Subject subject = subjectHolder.get();
		if(subject==null) return null;
		Set<Principal> principals = subject.getPrincipals();
		for (Principal principal : principals) {
			if(principal instanceof TermWsUserPrincipal) return (TermWsUserPrincipal) principal;
		}
		for (Principal principal : principals) {
			if(StringUtils.equals(principal.getName(), "CALLER_PRINCIPAL_GROUP")){
				Group group = (Group) principal;
				Enumeration<? extends Principal> members = group.members();
				while (members.hasMoreElements()) {
					Principal principal2 = (Principal) members.nextElement();
					if(principal2 instanceof TermWsUserPrincipal) return (TermWsUserPrincipal) principal2;
				}
			}
		}
		return null;
	}
	
	/**
	 * <p>
	 * Loads the specified class.
	 * </p>
	 * 
	 * @param fqn
	 *            the fully-qualified name of the class to be loaded.
	 * @return the loaded class.
	 * @throws PrivilegedActionException
	 *             if an error occurs while loading the class.
	 */
	static Class<?> loadClass(final String fqn)
			throws PrivilegedActionException {
		return AccessController
				.doPrivileged(new PrivilegedExceptionAction<Class<?>>() {
					public Class<?> run() throws Exception {
						ClassLoader loader = SecurityActions.class
								.getClassLoader();
						return loader.loadClass(fqn);
					}
				});
	}

	/**
	 * <p>
	 * Obtains the current {@code SecurityContext}.
	 * </p>
	 * 
	 * @return a reference to the current {@code SecurityContext} instance.
	 */
	static SecurityContext getSecurityContext() {
		return AccessController
				.doPrivileged(new PrivilegedAction<SecurityContext>() {
					public SecurityContext run() {
						return SecurityContextAssociation.getSecurityContext();
					}
				});
	}

}
