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

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamilySearchInput;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamilySearchResult;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamily_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalart2productfamilys")
public class CatalArt2ProductFamilyEndPoint
{

   @Inject
   private CatalArt2ProductFamilyEJB ejb;
   
   @Inject
   private CatalArt2ProductFamilyEJBCustom ejbCustom;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalArt2ProductFamily create(CatalArt2ProductFamily entity)
   {
      return detach(ejb.create(entity));
   }
   
   @POST
   @Path("/createCustom/{artPic}")
   @Consumes({ "application/json", "application/xml" })
   public Response createCustom(@PathParam("artPic")String arcPic,CatalArt2ProductFamily entity)
   {
	   ejbCustom.createCustom(arcPic, entity);
	   return Response.noContent().build();
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
	   CatalArt2ProductFamily entity = ejb.deleteById(id);
	   if(entity == null ) {
		   return Response.status(Status.NOT_FOUND).build();
	   }
      return Response.ok(entity).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArt2ProductFamily update(CatalArt2ProductFamily entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
      CatalArt2ProductFamily found = ejb.findByIdentif(identif);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }
   
   @GET
   @Path("/findByArtPic/{artPic}")
   @Produces({ "application/json", "application/xml" })
   public Response findByArtPic(@PathParam("artPic") String artPic)
   {
	   List<CatalArt2ProductFamily> result = ejb.findByArtPic(artPic);
	   return Response.ok(detach(result)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalArt2ProductFamilySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalArt2ProductFamily> resultList = ejb.listAll(start, max);
      CatalArt2ProductFamilySearchInput searchInput = new CatalArt2ProductFamilySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalArt2ProductFamilySearchResult((long) resultList.size(),
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
   public CatalArt2ProductFamilySearchResult findBy(CatalArt2ProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalArt2ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalArt2ProductFamily> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArt2ProductFamilySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalArt2ProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalArt2ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArt2ProductFamilySearchResult findByLike(CatalArt2ProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalArt2ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalArt2ProductFamily> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArt2ProductFamilySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }
   

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalArt2ProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalArt2ProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalArt2ProductFamily, ?>[] readSeachAttributes(
         CatalArt2ProductFamilySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalArt2ProductFamily, ?>> result = new ArrayList<SingularAttribute<CatalArt2ProductFamily, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalArt2ProductFamily_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalArt2ProductFamily, ?>) field.get(null));
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

   private CatalArt2ProductFamily detach(CatalArt2ProductFamily entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalArt2ProductFamily> detach(List<CatalArt2ProductFamily> list)
   {
      if (list == null)
         return list;
      List<CatalArt2ProductFamily> result = new ArrayList<CatalArt2ProductFamily>();
      for (CatalArt2ProductFamily entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalArt2ProductFamilySearchInput detach(CatalArt2ProductFamilySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}