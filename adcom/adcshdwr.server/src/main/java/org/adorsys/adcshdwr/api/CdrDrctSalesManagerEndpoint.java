/**
 * 
 */
package org.adorsys.adcshdwr.api;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adcshdwr.exceptions.AdException;

/**
 * @author boriswaguia
 *
 */
@Path("/cdrdrctsalesmanager")
public class CdrDrctSalesManagerEndpoint {

	@Inject
	private CdrDrctSalesManager ejb;
	
	
	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CdrDsArtHolder save(CdrDsArtHolder cdrDsArtHolder) throws AdException {
		return ejb.updateOrder(cdrDsArtHolder);
	}
}
