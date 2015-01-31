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

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.jpa.CatalArtFeatMappingSearchInput;
import org.adorsys.adcatal.jpa.CatalArtFeatMappingSearchResult;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartfeatmappings")
public class CatalArtFeatMappingEndpoint
{

   @Inject
   private CatalArtFeatMappingEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalArtFeatMapping create(CatalArtFeatMapping entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{pic}")
   public Response deleteById(@PathParam("pic") String pic)
   {
      ejb.deleteByPic(pic);
      return Response.ok().build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtFeatMapping update(CatalArtFeatMapping entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalArtFeatMapping found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalArtFeatMappingSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalArtFeatMapping> resultList = ejb.listAll(start, max);
      CatalArtFeatMappingSearchInput searchInput = new CatalArtFeatMappingSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalArtFeatMappingSearchResult((long) resultList.size(),
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
   public CatalArtFeatMappingSearchResult findBy(CatalArtFeatMappingSearchInput searchInput)
   {
      SingularAttribute<CatalArtFeatMapping, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalArtFeatMapping> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtFeatMappingSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalArtFeatMappingSearchInput searchInput)
   {
      SingularAttribute<CatalArtFeatMapping, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtFeatMappingSearchResult findByLike(CatalArtFeatMappingSearchInput searchInput)
   {
      SingularAttribute<CatalArtFeatMapping, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalArtFeatMapping> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtFeatMappingSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalArtFeatMappingSearchInput searchInput)
   {
      SingularAttribute<CatalArtFeatMapping, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalArtFeatMapping, ?>[] readSeachAttributes(
         CatalArtFeatMappingSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalArtFeatMapping, ?>> result = new ArrayList<SingularAttribute<CatalArtFeatMapping, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalArtFeatMapping_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalArtFeatMapping, ?>) field.get(null));
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

   

   private CatalArtFeatMapping detach(CatalArtFeatMapping entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalArtFeatMapping> detach(List<CatalArtFeatMapping> list)
   {
      if (list == null)
         return list;
      List<CatalArtFeatMapping> result = new ArrayList<CatalArtFeatMapping>();
      for (CatalArtFeatMapping entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalArtFeatMappingSearchInput detach(CatalArtFeatMappingSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}