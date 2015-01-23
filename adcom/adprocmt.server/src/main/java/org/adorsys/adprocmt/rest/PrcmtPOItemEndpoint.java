package org.adorsys.adprocmt.rest;

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

import org.adorsys.adprocmt.jpa.PrcmtPOItemEvtData;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtPOItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtpoitems")
public class PrcmtPOItemEndpoint
{

   @Inject
   private PrcmtPOItemEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtPOItemEvtData create(PrcmtPOItemEvtData entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PrcmtPOItemEvtData deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtPOItemEvtData update(PrcmtPOItemEvtData entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PrcmtPOItemEvtData found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PrcmtPOItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PrcmtPOItemEvtData> resultList = ejb.listAll(start, max);
      PrcmtPOItemSearchInput searchInput = new PrcmtPOItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PrcmtPOItemSearchResult((long) resultList.size(),
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
   public PrcmtPOItemSearchResult findBy(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PrcmtPOItemEvtData> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtPOItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtPOItemSearchResult findByLike(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PrcmtPOItemEvtData> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtPOItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PrcmtPOItemEvtData, ?>[] readSeachAttributes(
         PrcmtPOItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PrcmtPOItemEvtData, ?>> result = new ArrayList<SingularAttribute<PrcmtPOItemEvtData, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PrcmtPOItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PrcmtPOItemEvtData, ?>) field.get(null));
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

   

   private PrcmtPOItemEvtData detach(PrcmtPOItemEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PrcmtPOItemEvtData> detach(List<PrcmtPOItemEvtData> list)
   {
      if (list == null)
         return list;
      List<PrcmtPOItemEvtData> result = new ArrayList<PrcmtPOItemEvtData>();
      for (PrcmtPOItemEvtData entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PrcmtPOItemSearchInput detach(PrcmtPOItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}