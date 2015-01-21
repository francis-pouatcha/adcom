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

import org.adorsys.adcshdwr.jpa.CdrDsHstry;
import org.adorsys.adcshdwr.jpa.CdrDsHstrySearchInput;
import org.adorsys.adcshdwr.jpa.CdrDsHstrySearchResult;
import org.adorsys.adcshdwr.jpa.CdrDsHstry_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrdshstrys")
public class CdrDsHstryEndpoint
{

   @Inject
   private CdrDsHstryEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrDsHstry create(CdrDsHstry entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrDsHstry deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrDsHstry update(CdrDsHstry entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrDsHstry found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrDsHstrySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrDsHstry> resultList = ejb.listAll(start, max);
      CdrDsHstrySearchInput searchInput = new CdrDsHstrySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrDsHstrySearchResult((long) resultList.size(),
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
   public CdrDsHstrySearchResult findBy(CdrDsHstrySearchInput searchInput)
   {
      SingularAttribute<CdrDsHstry, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrDsHstry> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrDsHstrySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrDsHstrySearchInput searchInput)
   {
      SingularAttribute<CdrDsHstry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrDsHstrySearchResult findByLike(CdrDsHstrySearchInput searchInput)
   {
      SingularAttribute<CdrDsHstry, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrDsHstry> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrDsHstrySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrDsHstrySearchInput searchInput)
   {
      SingularAttribute<CdrDsHstry, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrDsHstry, ?>[] readSeachAttributes(
         CdrDsHstrySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrDsHstry, ?>> result = new ArrayList<SingularAttribute<CdrDsHstry, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrDsHstry_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrDsHstry, ?>) field.get(null));
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

   

   private CdrDsHstry detach(CdrDsHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrDsHstry> detach(List<CdrDsHstry> list)
   {
      if (list == null)
         return list;
      List<CdrDsHstry> result = new ArrayList<CdrDsHstry>();
      for (CdrDsHstry entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrDsHstrySearchInput detach(CdrDsHstrySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}