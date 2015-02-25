package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.jpa.StkArticleLotSearchResult;
import org.adorsys.adstock.jpa.StkArticleLot_;
import org.adorsys.adstock.rest.extension.invtry.ArtLotSearchInput;
import org.adorsys.adstock.rest.extension.invtry.ArticleLotSearchResult;
import org.adorsys.adstock.rest.extension.invtry.StkArticleInvtryIntegrationEJB;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/stkarticlelots")
public class StkArticleLotEndpoint
{

   @Inject
   private StkArticleLotEJB ejb;

   /**
    * The articleInvtryIntegrationEJB field.
    */
   @Inject
   private StkArticleInvtryIntegrationEJB articleInvtryIntegrationEJB;
   
   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public StkArticleLot create(StkArticleLot entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      StkArticleLot deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArticleLot update(StkArticleLot entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      StkArticleLot found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public StkArticleLotSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<StkArticleLot> resultList = ejb.listAll(start, max);
      StkArticleLotSearchInput searchInput = new StkArticleLotSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new StkArticleLotSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }

   @Inject
   private StkArticleLot2StrgSctnEJB strgSctnEJB;
   
   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArticleLotSearchResult findBy(StkArticleLotSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkArticleLot> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      StkArticleLotSearchResult searchResult = new StkArticleLotSearchResult(count, detach(resultList),
              detach(searchInput));
      return processSearchResult(searchInput, searchResult);
   }
   
   private StkArticleLotSearchResult processSearchResult(StkArticleLotSearchInput searchInput,
		   StkArticleLotSearchResult searchResult){
	   	List<StkArticleLot> resultList = searchResult.getResultList();
	    Map<String, StkArticleLot2StrgSctn> foundCache = new HashMap<String, StkArticleLot2StrgSctn>();
		if(StringUtils.isNotBlank(searchInput.getSectionCode())){
	          for (StkArticleLot stkArticleLot : resultList) {
	        	  List<StkArticleLot2StrgSctn> sctns = strgSctnEJB.findByStrgSectionAndLotPicAndArtPic(searchInput.getSectionCode(), stkArticleLot.getLotPic(), stkArticleLot.getArtPic());
	        	  putAndCache(foundCache, sctns, stkArticleLot);
	          }
	      } else if(searchInput.isWithStrgSection()){
	          for (StkArticleLot stkArticleLot : resultList) {
	        	  List<StkArticleLot2StrgSctn> sctns = strgSctnEJB.findByArtPicAndLotPic(stkArticleLot.getArtPic(),stkArticleLot.getLotPic());
	        	  putAndCache(foundCache, sctns, stkArticleLot);
	          }
	      }
	      return searchResult;
   }
   
   private void putAndCache(Map<String, StkArticleLot2StrgSctn> foundCache, List<StkArticleLot2StrgSctn> sctns, StkArticleLot stkArticleLot){
 	  for (StkArticleLot2StrgSctn strgSctn : sctns) {
		  if(!foundCache.containsKey(strgSctn.getId())){
			  foundCache.put(strgSctn.getId(), strgSctn);
			  stkArticleLot.getStrgSctns().add(strgSctn);
		  } else {
			  if(!stkArticleLot.getStrgSctns().contains(strgSctn)){
				  stkArticleLot.getStrgSctns().add(strgSctn);
			  }
		  }
	  }
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(StkArticleLotSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArticleLotSearchResult findByLike(StkArticleLotSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<StkArticleLot> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      StkArticleLotSearchResult searchResult =  new StkArticleLotSearchResult(countLike, detach(resultList),
              detach(searchInput));
      return processSearchResult(searchInput, searchResult);
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(StkArticleLotSearchInput searchInput)
   {
      SingularAttribute<StkArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findStkByArtPic")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public ArticleLotSearchResult findArticleLotByProductscip(ArtLotSearchInput searchInput) {
	   return articleInvtryIntegrationEJB.searchArtStkArtLot(searchInput);
   }
   @SuppressWarnings("unchecked")
   private SingularAttribute<StkArticleLot, ?>[] readSeachAttributes(
         StkArticleLotSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<StkArticleLot, ?>> result = new ArrayList<SingularAttribute<StkArticleLot, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = StkArticleLot_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<StkArticleLot, ?>) field.get(null));
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

   

   private StkArticleLot detach(StkArticleLot entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<StkArticleLot> detach(List<StkArticleLot> list)
   {
      if (list == null)
         return list;
      List<StkArticleLot> result = new ArrayList<StkArticleLot>();
      for (StkArticleLot entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private StkArticleLotSearchInput detach(StkArticleLotSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}