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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMapingSearchInput;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMapingSearchResult;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalfamilyfeatmapings")
public class CatalFamilyFeatMapingEndpoint
{

   @Inject
   private CatalFamilyFeatMapingEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalFamilyFeatMaping create(CatalFamilyFeatMaping entity)
   {
      return detach(ejb.create(entity));
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalFamilyFeatMaping update(CatalFamilyFeatMaping entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalFamilyFeatMaping found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalFamilyFeatMapingSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalFamilyFeatMaping> resultList = ejb.listAll(start, max);
      CatalFamilyFeatMapingSearchInput searchInput = new CatalFamilyFeatMapingSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalFamilyFeatMapingSearchResult((long) resultList.size(),
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
   public CatalFamilyFeatMapingSearchResult findBy(CatalFamilyFeatMapingSearchInput searchInput)
   {
      SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalFamilyFeatMaping> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalFamilyFeatMapingSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalFamilyFeatMapingSearchInput searchInput)
   {
      SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalFamilyFeatMapingSearchResult findByLike(CatalFamilyFeatMapingSearchInput searchInput)
   {
      SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalFamilyFeatMaping> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalFamilyFeatMapingSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalFamilyFeatMapingSearchInput searchInput)
   {
      SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalFamilyFeatMaping, ?>[] readSeachAttributes(
         CatalFamilyFeatMapingSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalFamilyFeatMaping, ?>> result = new ArrayList<SingularAttribute<CatalFamilyFeatMaping, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalFamilyFeatMaping_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalFamilyFeatMaping, ?>) field.get(null));
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

   

   private CatalFamilyFeatMaping detach(CatalFamilyFeatMaping entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalFamilyFeatMaping> detach(List<CatalFamilyFeatMaping> list)
   {
      if (list == null)
         return list;
      List<CatalFamilyFeatMaping> result = new ArrayList<CatalFamilyFeatMaping>();
      for (CatalFamilyFeatMaping entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalFamilyFeatMapingSearchInput detach(CatalFamilyFeatMapingSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}