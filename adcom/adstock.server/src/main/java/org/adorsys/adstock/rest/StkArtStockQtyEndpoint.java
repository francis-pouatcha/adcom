package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.jpa.StkArtStockQtySearchInput;
import org.adorsys.adstock.jpa.StkArtStockQtySearchResult;
import org.adorsys.adstock.jpa.StkArtStockQty_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkartstockqtys")
public class StkArtStockQtyEndpoint
{

   @Inject
   private StkArtStockQtyEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkArtStockQty create(StkArtStockQty entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkArtStockQty deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArtStockQty update(StkArtStockQty entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkArtStockQty found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StkArtStockQtySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkArtStockQty> resultList = ejb.listAll(start, max);
      StkArtStockQtySearchInput searchInput = new StkArtStockQtySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkArtStockQtySearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }

   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArtStockQtySearchResult findBy(StkArtStockQtySearchInput searchInput)
   {
      SingularAttribute<StkArtStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkArtStockQty> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkArtStockQtySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkArtStockQtySearchInput searchInput)
   {
      SingularAttribute<StkArtStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArtStockQtySearchResult findByLike(StkArtStockQtySearchInput searchInput)
   {
      SingularAttribute<StkArtStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkArtStockQty> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkArtStockQtySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkArtStockQtySearchInput searchInput)
   {
      SingularAttribute<StkArtStockQty, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<StkArtStockQty, ?>[] readSeachAttributes(
         StkArtStockQtySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkArtStockQty, ?>> result = new ArrayList<SingularAttribute<StkArtStockQty, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkArtStockQty_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkArtStockQty, ?>) field.get(null));
               }
               catch (IllegalArgumentException e)
               {
                  throw new IllegalStateException(e);
               }
               catch (IllegalAccessException e)
               {
                  throw new IllegalStateException(e);
               }
            }
         }
      }
      return result.toArray(new SingularAttribute[result.size()]);
   }

   

   private StkArtStockQty detach(StkArtStockQty entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<StkArtStockQty> detach(List<StkArtStockQty> list)
   {
      if (list == null)
         return list;
      List<StkArtStockQty> result = new ArrayList<StkArtStockQty>();
      for (StkArtStockQty entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StkArtStockQtySearchInput detach(StkArtStockQtySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}