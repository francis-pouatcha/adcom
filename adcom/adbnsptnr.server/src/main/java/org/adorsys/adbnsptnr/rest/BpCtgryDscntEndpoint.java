package org.adorsys.adbnsptnr.rest;

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

import org.adorsys.adbnsptnr.jpa.BpCtgryDscnt;
import org.adorsys.adbnsptnr.jpa.BpCtgryDscntSearchInput;
import org.adorsys.adbnsptnr.jpa.BpCtgryDscntSearchResult;
import org.adorsys.adbnsptnr.jpa.BpCtgryDscnt_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpctgrydscnts")
public class BpCtgryDscntEndpoint
{

   @Inject
   private BpCtgryDscntEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpCtgryDscnt create(BpCtgryDscnt entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpCtgryDscnt deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpCtgryDscnt update(BpCtgryDscnt entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpCtgryDscnt found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpCtgryDscntSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpCtgryDscnt> resultList = ejb.listAll(start, max);
      BpCtgryDscntSearchInput searchInput = new BpCtgryDscntSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpCtgryDscntSearchResult((long) resultList.size(),
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
   public BpCtgryDscntSearchResult findBy(BpCtgryDscntSearchInput searchInput)
   {
      SingularAttribute<BpCtgryDscnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpCtgryDscnt> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpCtgryDscntSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpCtgryDscntSearchInput searchInput)
   {
      SingularAttribute<BpCtgryDscnt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpCtgryDscntSearchResult findByLike(BpCtgryDscntSearchInput searchInput)
   {
      SingularAttribute<BpCtgryDscnt, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpCtgryDscnt> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpCtgryDscntSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpCtgryDscntSearchInput searchInput)
   {
      SingularAttribute<BpCtgryDscnt, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpCtgryDscnt, ?>[] readSeachAttributes(
         BpCtgryDscntSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpCtgryDscnt, ?>> result = new ArrayList<SingularAttribute<BpCtgryDscnt, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpCtgryDscnt_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpCtgryDscnt, ?>) field.get(null));
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

   

   private BpCtgryDscnt detach(BpCtgryDscnt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpCtgryDscnt> detach(List<BpCtgryDscnt> list)
   {
      if (list == null)
         return list;
      List<BpCtgryDscnt> result = new ArrayList<BpCtgryDscnt>();
      for (BpCtgryDscnt entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpCtgryDscntSearchInput detach(BpCtgryDscntSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}