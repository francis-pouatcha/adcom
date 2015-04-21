package org.adorsys.adsales.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invoice")
public class SlsInvceManagerEndpoint
{

   @Inject
   private SlsInvoiceManager slsInvoiceManager; 

   
   @POST
   @Path("/processInvoice")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceHolder processInvoice(SlsInvoiceHolder slsInvoiceHolder){
      return slsInvoiceManager.processInvoice(slsInvoiceHolder);
   }
   
   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceHolder findInvoice(@PathParam("id") String id)
   {
	   return slsInvoiceManager.findInvoice(id);
   }
   
}