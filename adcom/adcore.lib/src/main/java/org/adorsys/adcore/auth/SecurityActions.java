package org.adorsys.adcore.auth;

import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;

import org.jboss.security.SecurityContext;
import org.jboss.security.SecurityContextAssociation;
class SecurityActions {

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
