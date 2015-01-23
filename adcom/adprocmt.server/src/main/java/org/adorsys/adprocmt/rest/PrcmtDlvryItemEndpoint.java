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

import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdlvryitems")
public class PrcmtDlvryItemEndpoint
{

   @Inject
   private PrcmtDlvryItemEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtDlvryItemEvtData create(PrcmtDlvryItemEvtData entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PrcmtDlvryItemEvtData deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtDlvryItemEvtData update(PrcmtDlvryItemEvtData entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PrcmtDlvryItemEvtData found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PrcmtDlvryItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PrcmtDlvryItemEvtData> resultList = ejb.listAll(start, max);
      PrcmtDlvryItemSearchInput searchInput = new PrcmtDlvryItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PrcmtDlvryItemSearchResult((long) resultList.size(),
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
   public PrcmtDlvryItemSearchResult findBy(PrcmtDlvryItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PrcmtDlvryItemEvtData> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtDlvryItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PrcmtDlvryItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtDlvryItemSearchResult findByLike(PrcmtDlvryItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PrcmtDlvryItemEvtData> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtDlvryItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PrcmtDlvryItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PrcmtDlvryItemEvtData, ?>[] readSeachAttributes(
         PrcmtDlvryItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PrcmtDlvryItemEvtData, ?>> result = new ArrayList<SingularAttribute<PrcmtDlvryItemEvtData, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PrcmtDlvryItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PrcmtDlvryItemEvtData, ?>) field.get(null));
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

   

   private PrcmtDlvryItemEvtData detach(PrcmtDlvryItemEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PrcmtDlvryItemEvtData> detach(List<PrcmtDlvryItemEvtData> list)
   {
      if (list == null)
         return list;
      List<PrcmtDlvryItemEvtData> result = new ArrayList<PrcmtDlvryItemEvtData>();
      for (PrcmtDlvryItemEvtData entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PrcmtDlvryItemSearchInput detach(PrcmtDlvryItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}