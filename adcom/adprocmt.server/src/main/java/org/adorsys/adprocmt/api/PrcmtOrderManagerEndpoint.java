package org.adorsys.adprocmt.api;

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

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/order")
public class PrcmtOrderManagerEndpoint
{

   @Inject
   private PrcmtOrderManager OrderManager; 

   @POST
   @Path("/create")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtOrderHolder createOrder(PrcmtProcOrder prcmtProcOrder){
      return OrderManager.createPcrmtOrder(prcmtProcOrder);
   }
   
   @POST
   @Path("/update")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtOrderHolder updateOrder(PrcmtOrderHolder prcmtOrderHolder){
      return OrderManager.updateOrder(prcmtOrderHolder);
   }

   @POST
   @Path("/close")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtOrderHolder closeOrder(PrcmtOrderHolder prcmtOrderHolder){
      return OrderManager.closeOrder(prcmtOrderHolder);
   }
   
   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public PrcmtOrderHolder findOrder(@PathParam("id") String id)
   {
	   return OrderManager.findOrder(id);
   }
   
   @POST
   @Path("/transform")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtDeliveryHolder order2Delivery(PrcmtOrderHolder prcmtOrderHolder){
      return OrderManager.order2Delivery(prcmtOrderHolder);
   }
}