package org.adorsys.adprocmt.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/delivery")
public class PrcmtDeliveryManagerEndpoint
{

   @Inject
   private PrcmtDeliveryManager deliveryManager; 

   @POST
   @Path("/update")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtDeliveryHolder updateDelivery(PrcmtDeliveryHolder deliveryHolder){
      return deliveryManager.updateDelivery(deliveryHolder);
   }

   @POST
   @Path("/close")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtDeliveryHolder closeDelivery(PrcmtDeliveryHolder deliveryHolder){
      return deliveryManager.closeDelivery(deliveryHolder);
   }
}