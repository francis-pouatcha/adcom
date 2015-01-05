package org.adorsys.adcore.auth;

import static org.jboss.web.CatalinaMessages.MESSAGES;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.Authenticator;
import org.apache.catalina.Container;
import org.apache.catalina.Context;
import org.apache.catalina.Lifecycle;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.LifecycleListener;
import org.apache.catalina.Realm;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.catalina.deploy.LoginConfig;
import org.apache.catalina.deploy.SecurityConstraint;
import org.apache.catalina.util.LifecycleSupport;
import org.apache.catalina.valves.ValveBase;
import org.jboss.web.CatalinaLogger;

public abstract class AdcomAuthBase extends ValveBase implements Authenticator,
		Lifecycle {

    /**
     * The lifecycle event support for this component.
     */
    protected LifecycleSupport lifecycle = new LifecycleSupport(this);

    /**
     * Has this component been started?
     */
    protected boolean started = false;

    /**
     * The Context to which this Valve is attached.
     */
    protected Context context = null;
        
    /**
     * Authenticate the user making this request, based on the specified
     * login configuration.  Return <code>true</code> if any specified
     * constraint has been satisfied, or <code>false</code> if we have
     * created a response challenge already.
     *
     * @param request Request we are processing
     * @param response Response we are creating
     * @param config    Login configuration describing how authentication
     *              should be performed
     *
     * @exception IOException if an input/output error occurs
     */
    protected abstract boolean authenticate(Request request,
                                            HttpServletResponse response,
                                            LoginConfig config)
        throws IOException;
    
    @Override
	public void start() throws LifecycleException {

        // Validate and update our current component state
        if (started)
            throw new LifecycleException(MESSAGES.authenticatorAlreadyStarted());
        lifecycle.fireLifecycleEvent(START_EVENT, null);
        started = true;
	}

	@Override
	public void stop() throws LifecycleException {

        // Validate and update our current component state
        if (!started)
            throw new LifecycleException(MESSAGES.authenticatorNotStarted());
        lifecycle.fireLifecycleEvent(STOP_EVENT, null);
        started = false;
	}

	@Override
	public boolean authenticate(Request request, HttpServletResponse response)
			throws IOException, ServletException {
        return authenticate(request, response, this.context.getLoginConfig());
	}

	@Override
	public void invoke(Request request, Response response) throws IOException,
			ServletException {

        if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled())
            CatalinaLogger.AUTH_LOGGER.debug("Security checking request " +
                request.getMethod() + " " + request.getRequestURI());
        LoginConfig config = this.context.getLoginConfig();

        Realm realm = this.context.getRealm();
        // Is this request URI subject to a security constraint?
        SecurityConstraint [] constraints
            = realm.findSecurityConstraints(request, this.context);
       
        if ((constraints == null) /* &&
            (!Constants.FORM_METHOD.equals(config.getAuthMethod())) */ ) {
            if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled())
                CatalinaLogger.AUTH_LOGGER.debug(" Not subject to any constraint");
            getNext().invoke(request, response);
            return;
        }

        int i;
        // Enforce any user data constraint for this security constraint
        if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
            CatalinaLogger.AUTH_LOGGER.debug(" Calling hasUserDataPermission()");
        }
        if (!realm.hasUserDataPermission(request, response,
                                         constraints)) {
            if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
                CatalinaLogger.AUTH_LOGGER.debug(" Failed hasUserDataPermission() test");
            }
            /*
             * ASSERT: Authenticator already set the appropriate
             * HTTP status code, so we do not have to do anything special
             */
            return;
        }

        // Since authenticate modifies the response on failure,
        // we have to check for allow-from-all first.
        boolean authRequired = true;
        for(i=0; i < constraints.length && authRequired; i++) {
            if(!constraints[i].getAuthConstraint()) {
                authRequired = false;
            } else if(!constraints[i].getAllRoles()) {
                String [] roles = constraints[i].findAuthRoles();
                if(roles == null || roles.length == 0) {
                    authRequired = false;
                }
            }
        }
             
        if(authRequired) {  
            if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
                CatalinaLogger.AUTH_LOGGER.debug(" Calling authenticate()");
            }
            if (!authenticate(request, response, config)) {
                if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
                    CatalinaLogger.AUTH_LOGGER.debug(" Failed authenticate() test");
                }
                /*
                 * ASSERT: Authenticator already set the appropriate
                 * HTTP status code, so we do not have to do anything
                 * special
                 */
                return;
            } 
        }
    
        if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
            CatalinaLogger.AUTH_LOGGER.debug(" Calling accessControl()");
        }
        if (!realm.hasResourcePermission(request, response,
                                         constraints,
                                         this.context)) {
            if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
                CatalinaLogger.AUTH_LOGGER.debug(" Failed accessControl() test");
            }
            /*
             * ASSERT: AccessControl method has already set the
             * appropriate HTTP status code, so we do not have to do
             * anything special
             */
            return;
        }
    
        // Any and all specified constraints have been satisfied
        if (CatalinaLogger.AUTH_LOGGER.isDebugEnabled()) {
            CatalinaLogger.AUTH_LOGGER.debug(" Successfully passed all security constraints");
        }
        getNext().invoke(request, response);
	}



	@Override
	public void addLifecycleListener(LifecycleListener listener) {
        lifecycle.addLifecycleListener(listener);
	}

	@Override
	public LifecycleListener[] findLifecycleListeners() {
        return lifecycle.findLifecycleListeners();
	}

	@Override
	public void removeLifecycleListener(LifecycleListener listener) {
        lifecycle.removeLifecycleListener(listener);
	}



    /**
     * Return the Container to which this Valve is attached.
     */
	@Override
    public Container getContainer() {

        return (this.context);

    }

    /**
     * Set the Container to which this Valve is attached.
     *
     * @param container The container to which we are attached
     */
	@Override
    public void setContainer(Container container) {

        if (!(container instanceof Context))
            throw MESSAGES.authenticatorNeedsContext();

        super.setContainer(container);
        this.context = (Context) container;

    }
}
