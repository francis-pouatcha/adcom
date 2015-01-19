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

import org.adorsys.adstock.jpa.StkArtLotSection;
import org.adorsys.adstock.jpa.StkArtLotSectionSearchInput;
import org.adorsys.adstock.jpa.StkArtLotSectionSearchResult;
import org.adorsys.adstock.jpa.StkArtLotSection_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkartlotsections")
public class StkArtLotSectionEndpoint
{

   @Inject
   private StkArtLotSectionEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkArtLotSection create(StkArtLotSection entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkArtLotSection deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArtLotSection update(StkArtLotSection entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkArtLotSection found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StkArtLotSectionSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkArtLotSection> resultList = ejb.listAll(start, max);
      StkArtLotSectionSearchInput searchInput = new StkArtLotSectionSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkArtLotSectionSearchResult((long) resultList.size(),
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
   public StkArtLotSectionSearchResult findBy(StkArtLotSectionSearchInput searchInput)
   {
      SingularAttribute<StkArtLotSection, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkArtLotSection> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkArtLotSectionSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkArtLotSectionSearchInput searchInput)
   {
      SingularAttribute<StkArtLotSection, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArtLotSectionSearchResult findByLike(StkArtLotSectionSearchInput searchInput)
   {
      SingularAttribute<StkArtLotSection, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkArtLotSection> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new StkArtLotSectionSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkArtLotSectionSearchInput searchInput)
   {
      SingularAttribute<StkArtLotSection, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<StkArtLotSection, ?>[] readSeachAttributes(
         StkArtLotSectionSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkArtLotSection, ?>> result = new ArrayList<SingularAttribute<StkArtLotSection, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkArtLotSection_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkArtLotSection, ?>) field.get(null));
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

   

   private StkArtLotSection detach(StkArtLotSection entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<StkArtLotSection> detach(List<StkArtLotSection> list)
   {
      if (list == null)
         return list;
      List<StkArtLotSection> result = new ArrayList<StkArtLotSection>();
      for (StkArtLotSection entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StkArtLotSectionSearchInput detach(StkArtLotSectionSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}