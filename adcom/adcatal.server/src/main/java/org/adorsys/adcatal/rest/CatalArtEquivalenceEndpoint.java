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

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.jpa.CatalArtEquivalenceSearchInput;
import org.adorsys.adcatal.jpa.CatalArtEquivalenceSearchResult;
import org.adorsys.adcatal.jpa.CatalArtEquivalence_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartequivalences")
public class CatalArtEquivalenceEndpoint
{

   @Inject
   private CatalArtEquivalenceEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalArtEquivalence create(CatalArtEquivalence entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CatalArtEquivalence deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtEquivalence update(CatalArtEquivalence entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalArtEquivalence found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalArtEquivalenceSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalArtEquivalence> resultList = ejb.listAll(start, max);
      CatalArtEquivalenceSearchInput searchInput = new CatalArtEquivalenceSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalArtEquivalenceSearchResult((long) resultList.size(),
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
   public CatalArtEquivalenceSearchResult findBy(CatalArtEquivalenceSearchInput searchInput)
   {
      SingularAttribute<CatalArtEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalArtEquivalence> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtEquivalenceSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalArtEquivalenceSearchInput searchInput)
   {
      SingularAttribute<CatalArtEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtEquivalenceSearchResult findByLike(CatalArtEquivalenceSearchInput searchInput)
   {
      SingularAttribute<CatalArtEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalArtEquivalence> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtEquivalenceSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalArtEquivalenceSearchInput searchInput)
   {
      SingularAttribute<CatalArtEquivalence, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalArtEquivalence, ?>[] readSeachAttributes(
         CatalArtEquivalenceSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalArtEquivalence, ?>> result = new ArrayList<SingularAttribute<CatalArtEquivalence, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalArtEquivalence_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalArtEquivalence, ?>) field.get(null));
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

   

   private CatalArtEquivalence detach(CatalArtEquivalence entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalArtEquivalence> detach(List<CatalArtEquivalence> list)
   {
      if (list == null)
         return list;
      List<CatalArtEquivalence> result = new ArrayList<CatalArtEquivalence>();
      for (CatalArtEquivalence entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalArtEquivalenceSearchInput detach(CatalArtEquivalenceSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}