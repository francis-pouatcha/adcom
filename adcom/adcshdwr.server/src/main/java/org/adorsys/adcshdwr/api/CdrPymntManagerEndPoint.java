/**
 * 
 */
package org.adorsys.adcshdwr.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author boriswaguia
 *
 */
@Path("/cdrpymntmanager")
public class CdrPymntManagerEndPoint {
	@Inject
	private CdrPymntManager ejb;
	
	
	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrPymntHolder save(CdrPymntHolder cdrPymntHolder) {
		return ejb.saveAndClovePymt(cdrPymntHolder);
	}
}