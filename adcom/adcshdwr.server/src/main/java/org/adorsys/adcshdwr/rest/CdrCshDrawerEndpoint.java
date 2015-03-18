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

import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.jpa.CdrCshDrawerSearchInput;
import org.adorsys.adcshdwr.jpa.CdrCshDrawerSearchResult;
import org.adorsys.adcshdwr.jpa.CdrCshDrawer_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/cdrcshdrawers")
public class CdrCshDrawerEndpoint
{

   @Inject
   private CdrCshDrawerEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrCshDrawer create(CdrCshDrawer entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CdrCshDrawer deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrCshDrawer update(CdrCshDrawer entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CdrCshDrawer found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CdrCshDrawerSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CdrCshDrawer> resultList = ejb.listAll(start, max);
      CdrCshDrawerSearchInput searchInput = new CdrCshDrawerSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CdrCshDrawerSearchResult((long) resultList.size(),
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
   public CdrCshDrawerSearchResult findBy(CdrCshDrawerSearchInput searchInput)
   {
      SingularAttribute<CdrCshDrawer, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CdrCshDrawer> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrCshDrawerSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CdrCshDrawerSearchInput searchInput)
   {
      SingularAttribute<CdrCshDrawer, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CdrCshDrawerSearchResult findByLike(CdrCshDrawerSearchInput searchInput)
   {
      SingularAttribute<CdrCshDrawer, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CdrCshDrawer> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CdrCshDrawerSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CdrCshDrawerSearchInput searchInput)
   {
      SingularAttribute<CdrCshDrawer, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }
   

   @POST
   @Path("/openCshDrawer")
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CdrCshDrawer openCshDrawer(CdrCshDrawer cshDrawer)
   {
      cshDrawer = ejb.openCshDrawer(cshDrawer);
      return cshDrawer;
   }
   
   @GET
   @Path("/getActive")
   @Produces({ "application/json", "application/xml" })
   public CdrCshDrawer getActive() {
	   CdrCshDrawer cdrCshDrawer = ejb.getActiveCshDrawer();
	   return cdrCshDrawer;
   }
   
   @GET
   @Path("/findPreviousCdrCshDrawer")
   @Produces({ "application/json", "application/xml" })
   public List<CdrCshDrawer> findPreviousCdrCshDrawer() {
	   List<CdrCshDrawer> cdrCshDrawers = ejb.findPreviousCdrCshDrawer();
	   return cdrCshDrawers;
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CdrCshDrawer, ?>[] readSeachAttributes(
         CdrCshDrawerSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CdrCshDrawer, ?>> result = new ArrayList<SingularAttribute<CdrCshDrawer, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CdrCshDrawer_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CdrCshDrawer, ?>) field.get(null));
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

   

   private CdrCshDrawer detach(CdrCshDrawer entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CdrCshDrawer> detach(List<CdrCshDrawer> list)
   {
      if (list == null)
         return list;
      List<CdrCshDrawer> result = new ArrayList<CdrCshDrawer>();
      for (CdrCshDrawer entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CdrCshDrawerSearchInput detach(CdrCshDrawerSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}