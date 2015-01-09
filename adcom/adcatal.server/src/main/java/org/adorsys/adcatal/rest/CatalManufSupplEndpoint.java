package org.adorsys.adcatal.rest;

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
import org.adorsys.adcatal.jpa.CatalManufSuppl;
import org.adorsys.adcatal.jpa.CatalManufSuppl_;
import org.adorsys.adcatal.jpa.CatalManufSupplSearchInput;
import org.adorsys.adcatal.jpa.CatalManufSupplSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalmanufsuppls")
public class CatalManufSupplEndpoint
{

   @Inject
   private CatalManufSupplEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalManufSuppl create(CatalManufSuppl entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      CatalManufSuppl deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalManufSuppl update(CatalManufSuppl entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalManufSuppl found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalManufSupplSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalManufSuppl> resultList = ejb.listAll(start, max);
      CatalManufSupplSearchInput searchInput = new CatalManufSupplSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalManufSupplSearchResult((long) resultList.size(),
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
   public CatalManufSupplSearchResult findBy(CatalManufSupplSearchInput searchInput)
   {
      SingularAttribute<CatalManufSuppl, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalManufSuppl> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalManufSupplSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalManufSupplSearchInput searchInput)
   {
      SingularAttribute<CatalManufSuppl, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalManufSupplSearchResult findByLike(CatalManufSupplSearchInput searchInput)
   {
      SingularAttribute<CatalManufSuppl, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalManufSuppl> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalManufSupplSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalManufSupplSearchInput searchInput)
   {
      SingularAttribute<CatalManufSuppl, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalManufSuppl, ?>[] readSeachAttributes(
         CatalManufSupplSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalManufSuppl, ?>> result = new ArrayList<SingularAttribute<CatalManufSuppl, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalManufSuppl_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalManufSuppl, ?>) field.get(null));
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

   private CatalManufSuppl detach(CatalManufSuppl entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalManufSuppl> detach(List<CatalManufSuppl> list)
   {
      if (list == null)
         return list;
      List<CatalManufSuppl> result = new ArrayList<CatalManufSuppl>();
      for (CatalManufSuppl entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalManufSupplSearchInput detach(CatalManufSupplSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}