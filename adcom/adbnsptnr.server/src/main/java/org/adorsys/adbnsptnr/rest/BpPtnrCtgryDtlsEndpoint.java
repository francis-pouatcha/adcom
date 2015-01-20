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

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtlsSearchInput;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtlsSearchResult;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/bpptnrctgrydtlss")
public class BpPtnrCtgryDtlsEndpoint
{

   @Inject
   private BpPtnrCtgryDtlsEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public BpPtnrCtgryDtls create(BpPtnrCtgryDtls entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      BpPtnrCtgryDtls deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrCtgryDtls update(BpPtnrCtgryDtls entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      BpPtnrCtgryDtls found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public BpPtnrCtgryDtlsSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<BpPtnrCtgryDtls> resultList = ejb.listAll(start, max);
      BpPtnrCtgryDtlsSearchInput searchInput = new BpPtnrCtgryDtlsSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new BpPtnrCtgryDtlsSearchResult((long) resultList.size(),
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
   public BpPtnrCtgryDtlsSearchResult findBy(BpPtnrCtgryDtlsSearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<BpPtnrCtgryDtls> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpPtnrCtgryDtlsSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(BpPtnrCtgryDtlsSearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public BpPtnrCtgryDtlsSearchResult findByLike(BpPtnrCtgryDtlsSearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<BpPtnrCtgryDtls> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new BpPtnrCtgryDtlsSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(BpPtnrCtgryDtlsSearchInput searchInput)
   {
      SingularAttribute<BpPtnrCtgryDtls, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<BpPtnrCtgryDtls, ?>[] readSeachAttributes(
         BpPtnrCtgryDtlsSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<BpPtnrCtgryDtls, ?>> result = new ArrayList<SingularAttribute<BpPtnrCtgryDtls, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = BpPtnrCtgryDtls_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<BpPtnrCtgryDtls, ?>) field.get(null));
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

   

   private BpPtnrCtgryDtls detach(BpPtnrCtgryDtls entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<BpPtnrCtgryDtls> detach(List<BpPtnrCtgryDtls> list)
   {
      if (list == null)
         return list;
      List<BpPtnrCtgryDtls> result = new ArrayList<BpPtnrCtgryDtls>();
      for (BpPtnrCtgryDtls entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private BpPtnrCtgryDtlsSearchInput detach(BpPtnrCtgryDtlsSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}