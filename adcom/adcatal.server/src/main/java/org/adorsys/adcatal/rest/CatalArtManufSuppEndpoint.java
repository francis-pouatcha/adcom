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

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchInput;
import org.adorsys.adcatal.jpa.CatalArtManufSuppSearchResult;
import org.adorsys.adcatal.jpa.CatalArtManufSupp_;
import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.adorsys.adcatal.jpa.CatalPicMappingSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalartmanufsupps")
public class CatalArtManufSuppEndpoint
{

   @Inject
   private CatalArtManufSuppEJB ejb;
   @Inject
   private CatalCipOrigineEJB cipOrigineEJB;
   
   @Inject
   private SecurityUtil securityUtil;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalArtManufSupp create(CatalArtManufSupp entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      CatalArtManufSupp deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtManufSupp update(CatalArtManufSupp entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      CatalArtManufSupp found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalArtManufSuppSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalArtManufSupp> resultList = ejb.listAll(start, max);
      CatalArtManufSuppSearchInput searchInput = new CatalArtManufSuppSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalArtManufSuppSearchResult((long) resultList.size(),
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
   public CatalArtManufSuppSearchResult findBy(CatalArtManufSuppSearchInput searchInput)
   {
      SingularAttribute<CatalArtManufSupp, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalArtManufSupp> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArtManufSuppSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalArtManufSuppSearchInput searchInput)
   {
      SingularAttribute<CatalArtManufSupp, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArtManufSuppSearchResult findByLike(CatalArtManufSuppSearchInput searchInput)
   {
      SingularAttribute<CatalArtManufSupp, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalArtManufSupp> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return addEnums(new CatalArtManufSuppSearchResult(countLike, detach(resultList),
            detach(searchInput)));
   }
   
   private CatalArtManufSuppSearchResult addEnums(CatalArtManufSuppSearchResult searchResult){
	      String userLange = securityUtil.getUserLange();
	      List<CatalCipOrigine> cipOrigines = cipOrigineEJB.findByLangIso2(userLange);
	      searchResult.getCipOrigines().clear();
	      searchResult.getCipOrigines().addAll(cipOrigines);
	      return searchResult;
}

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalArtManufSuppSearchInput searchInput)
   {
      SingularAttribute<CatalArtManufSupp, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalArtManufSupp, ?>[] readSeachAttributes(
         CatalArtManufSuppSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalArtManufSupp, ?>> result = new ArrayList<SingularAttribute<CatalArtManufSupp, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalArtManufSupp_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalArtManufSupp, ?>) field.get(null));
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

   

   private CatalArtManufSupp detach(CatalArtManufSupp entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalArtManufSupp> detach(List<CatalArtManufSupp> list)
   {
      if (list == null)
         return list;
      List<CatalArtManufSupp> result = new ArrayList<CatalArtManufSupp>();
      for (CatalArtManufSupp entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalArtManufSuppSearchInput detach(CatalArtManufSuppSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}