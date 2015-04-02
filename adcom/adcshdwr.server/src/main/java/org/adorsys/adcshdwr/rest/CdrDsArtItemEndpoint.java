package org.adorsys.adcshdwr.rest;

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

import org.adorsys.adcshdwr.jpa.CdrDsArtItemEvt;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchInput;
import org.adorsys.adcshdwr.jpa.CdrDsArtItemSearchResult;
import org.adorsys.adcshdwr.jpa.CdrDsArtItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrdsartitems")
public class CdrDsArtItemEndpoint
{

   @Inject
   private CdrDsArtItemEvtEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrDsArtItemEvt create(CdrDsArtItemEvt entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrDsArtItemEvt deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrDsArtItemEvt update(CdrDsArtItemEvt entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrDsArtItemEvt found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrDsArtItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrDsArtItemEvt> resultList = ejb.listAll(start, max);
      CdrDsArtItemSearchInput searchInput = new CdrDsArtItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrDsArtItemSearchResult((long) resultList.size(),
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
   public CdrDsArtItemSearchResult findBy(CdrDsArtItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsArtItemEvt, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrDsArtItemEvt> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrDsArtItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrDsArtItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsArtItemEvt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrDsArtItemSearchResult findByLike(CdrDsArtItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsArtItemEvt, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrDsArtItemEvt> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrDsArtItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrDsArtItemSearchInput searchInput)
   {
      SingularAttribute<CdrDsArtItemEvt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrDsArtItemEvt, ?>[] readSeachAttributes(
         CdrDsArtItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrDsArtItemEvt, ?>> result = new ArrayList<SingularAttribute<CdrDsArtItemEvt, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrDsArtItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrDsArtItemEvt, ?>) field.get(null));
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

   

   private CdrDsArtItemEvt detach(CdrDsArtItemEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrDsArtItemEvt> detach(List<CdrDsArtItemEvt> list)
   {
      if (list == null)
         return list;
      List<CdrDsArtItemEvt> result = new ArrayList<CdrDsArtItemEvt>();
      for (CdrDsArtItemEvt entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrDsArtItemSearchInput detach(CdrDsArtItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}