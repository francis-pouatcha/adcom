package org.adorsys.adcatal.rest;

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

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.jpa.CatalProductFamilySearchInput;
import org.adorsys.adcatal.jpa.CatalProductFamilySearchResult;
import org.adorsys.adcatal.jpa.CatalProductFamily_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalproductfamilys")
public class CatalProductFamilyEndpoint
{

   @Inject
   private CatalProductFamilyEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalProductFamily create(CatalProductFamily entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      CatalProductFamily deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalProductFamily update(CatalProductFamily entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalProductFamily found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalProductFamilySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalProductFamily> resultList = ejb.listAll(start, max);
      CatalProductFamilySearchInput searchInput = new CatalProductFamilySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalProductFamilySearchResult((long) resultList.size(),
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
   public CatalProductFamilySearchResult findBy(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalProductFamily> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalProductFamilySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalProductFamilySearchResult findByLike(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalProductFamily> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalProductFamilySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalProductFamily, ?>[] readSeachAttributes(
         CatalProductFamilySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalProductFamily, ?>> result = new ArrayList<SingularAttribute<CatalProductFamily, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalProductFamily_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalProductFamily, ?>) field.get(null));
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

   

   private CatalProductFamily detach(CatalProductFamily entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalProductFamily> detach(List<CatalProductFamily> list)
   {
      if (list == null)
         return list;
      List<CatalProductFamily> result = new ArrayList<CatalProductFamily>();
      for (CatalProductFamily entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalProductFamilySearchInput detach(CatalProductFamilySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}