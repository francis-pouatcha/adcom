package org.adorsys.adcatal.rest;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
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

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamily_;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping_;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalArticleSearchInput;
import org.adorsys.adcatal.jpa.CatalArticleSearchResult;
import org.adorsys.adcatal.jpa.CatalArticle_;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.jpa.CatalPicMapping_;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalarticles")
public class CatalArticleEndpoint
{

   @Inject
   private CatalArticleEJB ejb;
   @Inject
   private CatalPicMappingEJB picMappingEJB;
   @Inject
   private CatalArtFeatMappingEJB featMappingEJB;
   @Inject
   private CatalArt2ProductFamilyEJB art2ProductFamilyEJB;
   

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalArticle create(CatalArticle entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{pic}")
   public Response deleteById(@PathParam("pic") String pic)
   {
      ejb.deleteByPic(pic);
      return Response.ok().build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticle update(CatalArticle entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
      CatalArticle found = ejb.findByIdentif(identif);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalArticleSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalArticle> resultList = ejb.listAll(start, max);
      CatalArticleSearchInput searchInput = new CatalArticleSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalArticleSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }
   
   	@GET
	@Path("previous/{pic}")
	@Produces({ "application/json", "application/xml" })
	public Response previous(@PathParam("pic") String pic)
	{
		List<CatalArticle> found;
		try {
			found = ejb.findPrevious(pic);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}
   	
   	@GET
	@Path("next/{pic}")
	@Produces({ "application/json", "application/xml" })
	public Response next(@PathParam("pic") String pic)
	{
		List<CatalArticle> found;
		try {
			found = ejb.findNext(pic);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()){
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}


   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findBy(CatalArticleSearchInput searchInput)
   {
      SingularAttribute<CatalArticle, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalArticle> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArticleSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalArticleSearchInput searchInput)
   {
      SingularAttribute<CatalArticle, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findByLike(CatalArticleSearchInput searchInput)
   {
      SingularAttribute<CatalArticle, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalArticle> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalArticleSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findCustom(CatalArticleSearchInput searchInput)
   {
	   if(searchInput.getFieldNames().isEmpty()) return findByLike(searchInput);
	   
	   CatalArticle entity = searchInput.getEntity();
	   if(StringUtils.isNotBlank(entity.getPic())){
		   return findByPicLike(searchInput);
	   } else if (entity.getFeatures()!=null && StringUtils.isNotBlank(entity.getFeatures().getArtName())){
		   return findByNameLike(searchInput);
	   } else if (entity.getFamilyFeatures()!=null && StringUtils.isNotBlank(entity.getFamilyFeatures().getFamilyName())){
		   return findByFamNameLike(searchInput);
	   } else {
	      Long countLike = 0l;
	      List<CatalArticle> resultList = java.util.Collections.emptyList();
	      return new CatalArticleSearchResult(countLike, detach(resultList),
	            detach(searchInput));
	   }
   }
   
   @SuppressWarnings("unchecked")
   @POST
   @Path("/findByNameLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findByNameLike(CatalArticleSearchInput searchInput)
   {
	   CatalArticle entity = searchInput.getEntity();
	   if(entity.getFeatures()==null && StringUtils.isBlank(entity.getFeatures().getArtName())){
	      Long countLike = 0l;
	      List<CatalArticle> resultList = java.util.Collections.emptyList();
	      return new CatalArticleSearchResult(countLike, detach(resultList),
	            detach(searchInput));		   
	   }

	   @SuppressWarnings("rawtypes")
	   SingularAttribute[] attributes = new SingularAttribute[]{CatalArtFeatMapping_.artName};
	   CatalArtFeatMapping artFeatMapping = entity.getFeatures();
	   Long countLike = featMappingEJB.countByLike(artFeatMapping, attributes);
	   List<CatalArtFeatMapping> list = featMappingEJB.findByLike(artFeatMapping, searchInput.getStart(), searchInput.getMax(), attributes);
	   List<CatalArticle> resultList = new ArrayList<CatalArticle>();
	   Map<String, CatalArticle> resultMap = new HashMap<String, CatalArticle>();
	   for (CatalArtFeatMapping featMapping : list) {
		   CatalArticle catalArticle = resultMap.get(featMapping.getArtIdentif());
		   if(catalArticle!=null){
			   CatalArticle c = catalArticle;
			   catalArticle=new CatalArticle();
			   c.copyTo(catalArticle);
		   } else {
			   catalArticle = ejb.findByIdentif(featMapping.getArtIdentif());
			   resultMap.put(featMapping.getArtIdentif(), catalArticle);
		   }
		   catalArticle.setFeatures(featMapping);
		   resultList.add(catalArticle);
	   }
      return new CatalArticleSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }

   @POST
   @Path("/findByNameStartWith")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findByNameStartWith(CatalArticleSearchInput searchInput)
   {
	   CatalArticle entity = searchInput.getEntity();
	   if(entity.getFeatures()==null && StringUtils.isBlank(entity.getFeatures().getArtName())){
	      Long countLike = 0l;
	      List<CatalArticle> resultList = java.util.Collections.emptyList();
	      return new CatalArticleSearchResult(countLike, detach(resultList),
	            detach(searchInput));		   
	   }
	   
	   CatalArtFeatMapping artFeatMapping = entity.getFeatures();
	   Long countLike = featMappingEJB.countByArtNameStartsWith(artFeatMapping.getArtName());
	   List<CatalArtFeatMapping> list = null;
	   if(countLike==0l){
		   list = Collections.emptyList();
	   } else {
		   list = featMappingEJB.findByArtNameStartsWith(artFeatMapping.getArtName(), searchInput.getStart(), searchInput.getMax());
	   }
	   List<CatalArticle> resultList = new ArrayList<CatalArticle>();
	   Map<String, CatalArticle> resultMap = new HashMap<String, CatalArticle>();
	   for (CatalArtFeatMapping featMapping : list) {
		   CatalArticle catalArticle = resultMap.get(featMapping.getArtIdentif());
		   if(catalArticle!=null){
			   CatalArticle c = catalArticle;
			   catalArticle=new CatalArticle();
			   c.copyTo(catalArticle);
		   } else {
			   catalArticle = ejb.findByIdentif(featMapping.getArtIdentif());
			   resultMap.put(featMapping.getArtIdentif(), catalArticle);
		   }
		   catalArticle.setFeatures(featMapping);
		   resultList.add(catalArticle);
	   }
      return new CatalArticleSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }
   
   @SuppressWarnings("unchecked")
   @POST
   @Path("/findByPicLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findByPicLike(CatalArticleSearchInput searchInput)
   {
	   CatalArticle entity = searchInput.getEntity();
	   if(StringUtils.isBlank(entity.getPic())){
	      Long countLike = 0l;
	      List<CatalArticle> resultList = java.util.Collections.emptyList();
	      return new CatalArticleSearchResult(countLike, detach(resultList),
	            detach(searchInput));		   
	   }

	   CatalPicMapping picMapping = new CatalPicMapping();
	   picMapping.setCode(entity.getPic());
	   @SuppressWarnings("rawtypes")
	   SingularAttribute[] attributes = new SingularAttribute[]{CatalPicMapping_.code};
	   Long countLike = picMappingEJB.countBy(picMapping, attributes);
	   List<CatalPicMapping> list = picMappingEJB.findByLike(picMapping, searchInput.getStart(), searchInput.getMax(), attributes);
	   List<CatalArticle> resultList = new ArrayList<CatalArticle>();
	   Map<String, CatalArticle> resultMap = new HashMap<String, CatalArticle>();
	   for (CatalPicMapping catalPicMapping : list) {
		   CatalArticle catalArticle = resultMap.get(catalPicMapping.getArtIdentif());
		   if(catalArticle!=null){
			   CatalArticle c = catalArticle;
			   catalArticle=new CatalArticle();
			   c.copyTo(catalArticle);
		   } else {
			   catalArticle = ejb.findByIdentif(catalPicMapping.getArtIdentif());
			   resultMap.put(catalPicMapping.getArtIdentif(), catalArticle);
		   }
		   catalArticle.setPicMapping(catalPicMapping);
		   resultList.add(catalArticle);
	   }
      return new CatalArticleSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }
   
   @SuppressWarnings("unchecked")
   @POST
   @Path("/findByFamNameLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalArticleSearchResult findByFamNameLike(CatalArticleSearchInput searchInput)
   {
	   CatalArticle entity = searchInput.getEntity();
	   if (entity.getFamilyFeatures()==null || StringUtils.isBlank(entity.getFamilyFeatures().getFamilyName())){
	      Long countLike = 0l;
	      List<CatalArticle> resultList = java.util.Collections.emptyList();
	      return new CatalArticleSearchResult(countLike, detach(resultList),
	            detach(searchInput));		   
	   }

	   @SuppressWarnings("rawtypes")
	   SingularAttribute[] attributes = new SingularAttribute[]{CatalArt2ProductFamily_.familyName};
	   CatalArt2ProductFamily familyFeatures = entity.getFamilyFeatures();
	   Long countLike = art2ProductFamilyEJB.countBy(familyFeatures, attributes);
	   List<CatalArt2ProductFamily> list = art2ProductFamilyEJB.findByLike(familyFeatures, searchInput.getStart(), searchInput.getMax(), attributes);
	   List<CatalArticle> resultList = new ArrayList<CatalArticle>();
	   Map<String, CatalArticle> resultMap = new HashMap<String, CatalArticle>();
	   for (CatalArt2ProductFamily featMapping : list) {
		   CatalArticle catalArticle = resultMap.get(featMapping.getArtPic());
		   if(catalArticle!=null){
			   CatalArticle c = catalArticle;
			   catalArticle=new CatalArticle();
			   c.copyTo(catalArticle);
		   } else {
			   catalArticle = ejb.findByIdentif(featMapping.getArtPic());
			   resultMap.put(featMapping.getArtPic(), catalArticle);
		   }
		   catalArticle.setFamilyFeatures(featMapping);
		   resultList.add(catalArticle);
	   }
      return new CatalArticleSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }
   
   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalArticleSearchInput searchInput)
   {
      SingularAttribute<CatalArticle, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }
   
   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalArticle, ?>[] readSeachAttributes(
         CatalArticleSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalArticle, ?>> result = new ArrayList<SingularAttribute<CatalArticle, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalArticle_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalArticle, ?>) field.get(null));
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

   

   private CatalArticle detach(CatalArticle entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<CatalArticle> detach(List<CatalArticle> list)
   {
      if (list == null)
         return list;
      List<CatalArticle> result = new ArrayList<CatalArticle>();
      for (CatalArticle entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalArticleSearchInput detach(CatalArticleSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}