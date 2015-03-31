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
@Path("/sale")
public class SlsSOManagerEndpoint
{

   @Inject
   private SaleOrderManager saleOrderManager; 

   
   @POST
   @Path("/doSale")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsSalesOrderHolder doSale(SlsSalesOrderHolder saleOrderHolder){
      return saleOrderManager.doSale(saleOrderHolder);
   }
   
   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public SlsSalesOrderHolder findSaleOrder(@PathParam("id") String id)
   {
	   return saleOrderManager.findSaleOrder(id);
   }
}