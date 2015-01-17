package org.adorsys.adsales.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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
import org.adorsys.adsales.jpa.SlsInvoiceType;
import org.adorsys.adsales.jpa.SlsInvoiceType_;
import org.adorsys.adsales.jpa.SlsInvoiceTypeSearchInput;
import org.adorsys.adsales.jpa.SlsInvoiceTypeSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsinvoicetypes")
public class SlsInvoiceTypeEndpoint
{

   @Inject
   private SlsInvoiceTypeEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceType create(SlsInvoiceType entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsInvoiceType deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsInvoiceType update(SlsInvoiceType entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsInvoiceType found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsInvoiceTypeSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsInvoiceType> resultList = ejb.listAll(start, max);
      SlsInvoiceTypeSearchInput searchInput = new SlsInvoiceTypeSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsInvoiceTypeSearchResult((long) resultList.size(),
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
   public SlsInvoiceTypeSearchResult findBy(SlsInvoiceTypeSearchInput searchInput)
   {
      SingularAttribute<SlsInvoiceType, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsInvoiceType> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsInvoiceTypeSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsInvoiceTypeSearchInput searchInput)
   {
      SingularAttribute<SlsInvoiceType, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsInvoiceTypeSearchResult findByLike(SlsInvoiceTypeSearchInput searchInput)
   {
      SingularAttribute<SlsInvoiceType, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsInvoiceType> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsInvoiceTypeSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsInvoiceTypeSearchInput searchInput)
   {
      SingularAttribute<SlsInvoiceType, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsInvoiceType, ?>[] readSeachAttributes(
         SlsInvoiceTypeSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsInvoiceType, ?>> result = new ArrayList<SingularAttribute<SlsInvoiceType, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsInvoiceType_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsInvoiceType, ?>) field.get(null));
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

   private static final List<String> emptyList = Collections.emptyList();

   private SlsInvoiceType detach(SlsInvoiceType entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsInvoiceType> detach(List<SlsInvoiceType> list)
   {
      if (list == null)
         return list;
      List<SlsInvoiceType> result = new ArrayList<SlsInvoiceType>();
      for (SlsInvoiceType entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsInvoiceTypeSearchInput detach(SlsInvoiceTypeSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}