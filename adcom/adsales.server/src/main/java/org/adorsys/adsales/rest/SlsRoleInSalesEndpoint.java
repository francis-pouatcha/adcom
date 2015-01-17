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
import org.adorsys.adsales.jpa.SlsRoleInSales;
import org.adorsys.adsales.jpa.SlsRoleInSales_;
import org.adorsys.adsales.jpa.SlsRoleInSalesSearchInput;
import org.adorsys.adsales.jpa.SlsRoleInSalesSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/slsroleinsaless")
public class SlsRoleInSalesEndpoint
{

   @Inject
   private SlsRoleInSalesEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public SlsRoleInSales create(SlsRoleInSales entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      SlsRoleInSales deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsRoleInSales update(SlsRoleInSales entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      SlsRoleInSales found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public SlsRoleInSalesSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<SlsRoleInSales> resultList = ejb.listAll(start, max);
      SlsRoleInSalesSearchInput searchInput = new SlsRoleInSalesSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new SlsRoleInSalesSearchResult((long) resultList.size(),
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
   public SlsRoleInSalesSearchResult findBy(SlsRoleInSalesSearchInput searchInput)
   {
      SingularAttribute<SlsRoleInSales, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<SlsRoleInSales> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsRoleInSalesSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(SlsRoleInSalesSearchInput searchInput)
   {
      SingularAttribute<SlsRoleInSales, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public SlsRoleInSalesSearchResult findByLike(SlsRoleInSalesSearchInput searchInput)
   {
      SingularAttribute<SlsRoleInSales, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<SlsRoleInSales> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new SlsRoleInSalesSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(SlsRoleInSalesSearchInput searchInput)
   {
      SingularAttribute<SlsRoleInSales, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<SlsRoleInSales, ?>[] readSeachAttributes(
         SlsRoleInSalesSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<SlsRoleInSales, ?>> result = new ArrayList<SingularAttribute<SlsRoleInSales, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = SlsRoleInSales_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<SlsRoleInSales, ?>) field.get(null));
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

   private SlsRoleInSales detach(SlsRoleInSales entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<SlsRoleInSales> detach(List<SlsRoleInSales> list)
   {
      if (list == null)
         return list;
      List<SlsRoleInSales> result = new ArrayList<SlsRoleInSales>();
      for (SlsRoleInSales entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private SlsRoleInSalesSearchInput detach(SlsRoleInSalesSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}