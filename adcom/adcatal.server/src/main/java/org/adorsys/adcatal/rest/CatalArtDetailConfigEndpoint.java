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

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArtDetailConfigSearchInput;
import org.adorsys.adcatal.jpa.CatalArtDetailConfigSearchResult;
import org.adorsys.adcatal.jpa.CatalArtDetailConfig_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartdetailconfigs")
public class CatalArtDetailConfigEndpoint
{

   @Inject
   private CatalArtDetailConfigEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalArtDetailConfig create(CatalArtDetailConfig entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      CatalArtDetailConfig deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtDetailConfig update(CatalArtDetailConfig entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalArtDetailConfig found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalArtDetailConfigSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalArtDetailConfig> resultList = ejb.listAll(start, max);
      CatalArtDetailConfigSearchInput searchInput = new CatalArtDetailConfigSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalArtDetailConfigSearchResult((long) resultList.size(),
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
   public CatalArtDetailConfigSearchResult findBy(CatalArtDetailConfigSearchInput searchInput)
   {
      SingularAttribute<CatalArtDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalArtDetailConfig> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtDetailConfigSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalArtDetailConfigSearchInput searchInput)
   {
      SingularAttribute<CatalArtDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtDetailConfigSearchResult findByLike(CatalArtDetailConfigSearchInput searchInput)
   {
      SingularAttribute<CatalArtDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalArtDetailConfig> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtDetailConfigSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalArtDetailConfigSearchInput searchInput)
   {
      SingularAttribute<CatalArtDetailConfig, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalArtDetailConfig, ?>[] readSeachAttributes(
         CatalArtDetailConfigSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalArtDetailConfig, ?>> result = new ArrayList<SingularAttribute<CatalArtDetailConfig, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalArtDetailConfig_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalArtDetailConfig, ?>) field.get(null));
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

   

   private CatalArtDetailConfig detach(CatalArtDetailConfig entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalArtDetailConfig> detach(List<CatalArtDetailConfig> list)
   {
      if (list == null)
         return list;
      List<CatalArtDetailConfig> result = new ArrayList<CatalArtDetailConfig>();
      for (CatalArtDetailConfig entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalArtDetailConfigSearchInput detach(CatalArtDetailConfigSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}