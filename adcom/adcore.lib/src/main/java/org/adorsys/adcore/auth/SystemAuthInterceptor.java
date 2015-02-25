package org.adorsys.adcore.auth;

import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import javax.security.auth.Subject;
import javax.security.auth.login.LoginContext;

@Interceptor
@AdSystem
public class SystemAuthInterceptor {
	
	@AroundInvoke
	public Object aroundInvoke(final InvocationContext context) throws Exception{
		final LoginContext loginContext = new LoginContext("adcom");
		loginContext.login();
		Subject subject = loginContext.getSubject();
		SecurityActions.set(subject);
		try {
			Object object = context.proceed();
			return object;
		} finally {
			loginContext.logout();
			SecurityActions.set(null);
		}
	}
}
