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

import org.adorsys.adcshdwr.jpa.CdrVchrHstry;
import org.adorsys.adcshdwr.jpa.CdrVchrHstrySearchInput;
import org.adorsys.adcshdwr.jpa.CdrVchrHstrySearchResult;
import org.adorsys.adcshdwr.jpa.CdrVchrHstry_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrvchrhstrys")
public class CdrVchrHstryEndpoint
{

   @Inject
   private CdrVchrHstryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrVchrHstry create(CdrVchrHstry entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id:[0-9][0-9]*}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrVchrHstry deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrVchrHstry update(CdrVchrHstry entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id:[0-9][0-9]*}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrVchrHstry found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrVchrHstrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrVchrHstry> resultList = ejb.listAll(start, max);
      CdrVchrHstrySearchInput searchInput = new CdrVchrHstrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrVchrHstrySearchResult((long) resultList.size(),
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
   public CdrVchrHstrySearchResult findBy(CdrVchrHstrySearchInput searchInput)
   {
      SingularAttribute<CdrVchrHstry, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrVchrHstry> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrVchrHstrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrVchrHstrySearchInput searchInput)
   {
      SingularAttribute<CdrVchrHstry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrVchrHstrySearchResult findByLike(CdrVchrHstrySearchInput searchInput)
   {
      SingularAttribute<CdrVchrHstry, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrVchrHstry> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrVchrHstrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrVchrHstrySearchInput searchInput)
   {
      SingularAttribute<CdrVchrHstry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrVchrHstry, ?>[] readSeachAttributes(
         CdrVchrHstrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrVchrHstry, ?>> result = new ArrayList<SingularAttribute<CdrVchrHstry, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrVchrHstry_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrVchrHstry, ?>) field.get(null));
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

   

   private CdrVchrHstry detach(CdrVchrHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrVchrHstry> detach(List<CdrVchrHstry> list)
   {
      if (list == null)
         return list;
      List<CdrVchrHstry> result = new ArrayList<CdrVchrHstry>();
      for (CdrVchrHstry entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrVchrHstrySearchInput detach(CdrVchrHstrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}