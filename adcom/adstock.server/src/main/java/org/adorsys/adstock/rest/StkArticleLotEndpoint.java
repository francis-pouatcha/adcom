package org.adorsys.adstock.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
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
   
   @Inject
   private StkArticleLotDetachHelper detachHelper;

   @Inject
	private StkArticleLot2StrgSctnLookup strgSctnEJB;
   
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
   @Path("/findByIdentif/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
	   StkArticleLot found = ejb.findByIdentif(identif);
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

   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public StkArticleLotSearchResult findBy(StkArticleLotSearchInput searchInput)
   {
	   if(searchInput.isFindByNameRange()) {
		   return findArticleLotProducts(searchInput);
	   }
      SingularAttribute<StkArticleLot, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<StkArticleLot> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      StkArticleLotSearchResult searchResult = new StkArticleLotSearchResult(count, detach(resultList),
              detach(searchInput));
      return processSearchResult(searchInput, searchResult);
   }
	
	private StkArticleLotSearchResult findArticleLotProducts(StkArticleLotSearchInput searchInput) {
		List<String> artPics = searchInput.getArtPics();
		   List<StkArticleLotSearchResult> searchResults = new ArrayList<StkArticleLotSearchResult>(artPics.size());
		   for (String artPic : artPics) {
			   StkArticleLotSearchInput tempSearchInput = new StkArticleLotSearchInput();
			   StkArticleLot entity = new StkArticleLot();
			   entity.setArtPic(artPic);
			   tempSearchInput.setEntity(entity);
			   List<String> fieldNames = new ArrayList<String>();
			   fieldNames.add("artPic");
			   tempSearchInput.setFieldNames(fieldNames);
			   SingularAttribute<StkArticleLot,?>[] attributes = readSeachAttributes(tempSearchInput);
			   Long count = ejb.countBy(tempSearchInput.getEntity(), attributes);
			   List<StkArticleLot> resultList = ejb.findBy(tempSearchInput.getEntity(),
					   tempSearchInput.getStart(), tempSearchInput.getMax(), attributes);
			   StkArticleLotSearchResult sr = new StkArticleLotSearchResult(count, detach(resultList),
					   detach(tempSearchInput));
			   StkArticleLotSearchResult tempSearchResult = processSearchResult(tempSearchInput, sr);
			   searchResults.add(tempSearchResult);
		   }
		   StkArticleLotSearchResult searchResult = new StkArticleLotSearchResult();
		   Long total = 0l;
		   List<StkArticleLot> resultList = new ArrayList<StkArticleLot>();
		   for (StkArticleLotSearchResult sr : searchResults) {
			   total = total + sr.getCount();
			   resultList.addAll(sr.getResultList());
		   }
		   searchResult.setCount(total);
		   searchResult.setResultList(resultList);
		   return searchResult;
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
      return detachHelper.detach(entity);
   }

   private List<StkArticleLot> detach(List<StkArticleLot> list)
   {
      return detachHelper.detach(list);
   }

   private StkArticleLotSearchInput detach(StkArticleLotSearchInput searchInput)
   {
	   return detachHelper.detach(searchInput);
   }

   private StkArticleLotSearchResult processSearchResult(StkArticleLotSearchInput searchInput,
		   StkArticleLotSearchResult searchResult){
		List<StkArticleLot> resultList = searchResult.getResultList();
		Map<String, StkArticleLot2StrgSctn> foundCache = new HashMap<String, StkArticleLot2StrgSctn>();
		if (StringUtils.isNotBlank(searchInput.getSectionCode())) {
			for (StkArticleLot stkArticleLot : resultList) {
				StkArticleLot2StrgSctn sctn = strgSctnEJB.findByStrgSectionAndLotPicAndArtPic(
						searchInput.getSectionCode(), stkArticleLot.getLotPic(),stkArticleLot.getArtPic());
				if(sctn != null)
					putAndCache(foundCache, Arrays.asList(sctn), stkArticleLot);
			}
		} else if (searchInput.isWithStrgSection()) {
			for (StkArticleLot stkArticleLot : resultList) {
				List<StkArticleLot2StrgSctn> sctns = strgSctnEJB
						.findByArtPicAndLotPic(stkArticleLot.getArtPic(),
								stkArticleLot.getLotPic());
				putAndCache(foundCache, sctns, stkArticleLot);
			}
		}
		return searchResult;
	}
   
	public void putAndCache(Map<String, StkArticleLot2StrgSctn> foundCache,
			List<StkArticleLot2StrgSctn> sctns, StkArticleLot stkArticleLot) {
		for (StkArticleLot2StrgSctn strgSctn : sctns) {
			if (!foundCache.containsKey(strgSctn.getId())) {
				foundCache.put(strgSctn.getId(), strgSctn);
				stkArticleLot.getStrgSctns().add(strgSctn);
			} else {
				if (!stkArticleLot.getStrgSctns().contains(strgSctn)) {
					stkArticleLot.getStrgSctns().add(strgSctn);
				}
			}
		}
	}
}