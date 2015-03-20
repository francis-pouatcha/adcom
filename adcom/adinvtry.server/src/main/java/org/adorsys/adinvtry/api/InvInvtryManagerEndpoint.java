package org.adorsys.adinvtry.api;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryItem;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/inventory")
public class InvInvtryManagerEndpoint
{

   @Inject
   private InvInvtryManager invtryManager; 

   @PUT
   @Path("/prepare")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtry newInventory(InvInvtry invtry){
      return invtryManager.prepareInventory(invtry);
   }
   
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

   @PUT
   @Path("/updateItem")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryItem updateItem(InvInvtryItem invtryItem){
      return invtryManager.updateItem(invtryItem);
   }

   @PUT
   @Path("/addItem")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryItem addItem(InvInvtryItem invtryItem){
      return invtryManager.addItem(invtryItem);
   }

   @PUT
   @Path("/disableItem")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryItem disableItem(InvInvtryItem invtryItem){
      return invtryManager.disableItem(invtryItem);
   }

   @PUT
   @Path("/enableItem")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryItem enableItem(InvInvtryItem invtryItem){
      return invtryManager.enableItem(invtryItem);
   }
}