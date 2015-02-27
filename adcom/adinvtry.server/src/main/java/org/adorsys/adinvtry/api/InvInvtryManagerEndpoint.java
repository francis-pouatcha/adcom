package org.adorsys.adinvtry.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/inventory")
public class InvInvtryManagerEndpoint
{

   @Inject
   private InvInvtryManager invtryManager; 

   @PUT
   @Path("/update")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryHolder updateInventory(InvInvtryHolder invtryHolder){
      return invtryManager.updateInventory(invtryHolder);
   }

   @PUT
   @Path("/close")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryHolder closeDelivery(InvInvtryHolder invtryHolder){
      return invtryManager.closeInventory(invtryHolder);
   }
}