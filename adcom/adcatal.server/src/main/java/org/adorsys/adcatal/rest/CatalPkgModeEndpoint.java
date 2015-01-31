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

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.adorsys.adcatal.jpa.CatalPkgModeSearchInput;
import org.adorsys.adcatal.jpa.CatalPkgModeSearchResult;
import org.adorsys.adcatal.jpa.CatalPkgMode_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalpkgmodes")
public class CatalPkgModeEndpoint
{

   @Inject
   private CatalPkgModeEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalPkgMode create(CatalPkgMode entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CatalPkgMode deleted = ejb.deleteCustomById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalPkgMode update(CatalPkgMode entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalPkgMode found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }
   
   @GET
   @Path("/findByIdentif/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
	   CatalPkgMode found = ejb.findByIdentif(identif);
	   if (found == null)
		   return Response.status(Status.NOT_FOUND).build();
	   return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalPkgModeSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalPkgMode> resultList = ejb.listAll(start, max);
      CatalPkgModeSearchInput searchInput = new CatalPkgModeSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalPkgModeSearchResult((long) resultList.size(),
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
   public CatalPkgModeSearchResult findBy(CatalPkgModeSearchInput searchInput)
   {
      SingularAttribute<CatalPkgMode, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalPkgMode> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalPkgModeSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalPkgModeSearchInput searchInput)
   {
      SingularAttribute<CatalPkgMode, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalPkgModeSearchResult findByLike(CatalPkgModeSearchInput searchInput)
   {
      SingularAttribute<CatalPkgMode, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalPkgMode> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalPkgModeSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/searchCatalPkgModes")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalPkgModeSearchResult searchCatalPkgModes(CatalPkgModeSearchInput searchInput)
   {
	   return findByLike(searchInput);
//	   if(searchInput.getStart()<0) searchInput.setStart(0);
//	   if(searchInput.getMax()<0) searchInput.setMax(max);
//	   List<CatalPkgMode> pkgModes = ejb.searchCatalPkgModes(searchInput);
//	   Long count = ejb.countCatalPkgModes(searchInput);
//	   CatalPkgModeSearchResult searchResult = new CatalPkgModeSearchResult(count, detach(pkgModes), detach(searchInput));
//	   return searchResult;
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalPkgModeSearchInput searchInput)
   {
      SingularAttribute<CatalPkgMode, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalPkgMode, ?>[] readSeachAttributes(
         CatalPkgModeSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalPkgMode, ?>> result = new ArrayList<SingularAttribute<CatalPkgMode, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalPkgMode_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalPkgMode, ?>) field.get(null));
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

   

   private CatalPkgMode detach(CatalPkgMode entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalPkgMode> detach(List<CatalPkgMode> list)
   {
      if (list == null)
         return list;
      List<CatalPkgMode> result = new ArrayList<CatalPkgMode>();
      for (CatalPkgMode entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalPkgModeSearchInput detach(CatalPkgModeSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}