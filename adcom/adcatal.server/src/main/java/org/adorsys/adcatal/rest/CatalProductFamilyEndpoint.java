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

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping_;
import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.jpa.CatalProductFamilySearchInput;
import org.adorsys.adcatal.jpa.CatalProductFamilySearchResult;
import org.adorsys.adcatal.jpa.CatalProductFamily_;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/catalproductfamilys")
public class CatalProductFamilyEndpoint
{

   @Inject
   private CatalProductFamilyEJB ejb;
   
   @Inject
   private CatalFamilyFeatMapingEJB familyFeatMapingEJB;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public CatalProductFamily create(CatalProductFamily entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{identif}")
   public Response deleteById(@PathParam("identif") String identif)
   {
      CatalProductFamily deleted = ejb.deleteByFamCode(identif);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalProductFamily update(CatalProductFamily entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{identif}")
   @Produces({ "application/json", "application/xml" })
   public Response findByIdentif(@PathParam("identif") String identif)
   {
      CatalProductFamily found = ejb.findByIdentif(identif);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public CatalProductFamilySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<CatalProductFamily> resultList = ejb.listAll(start, max);
      CatalProductFamilySearchInput searchInput = new CatalProductFamilySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new CatalProductFamilySearchResult((long) resultList.size(),
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
   public CatalProductFamilySearchResult findBy(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<CatalProductFamily> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalProductFamilySearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalProductFamilySearchResult findByLike(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<CatalProductFamily> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new CatalProductFamilySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }
   
   @SuppressWarnings("unchecked")
   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public CatalProductFamilySearchResult findCustom(CatalProductFamilySearchInput searchInput)
   {
	   if(searchInput.getFieldNames().isEmpty()) return findByLike(searchInput);
	   
	   CatalProductFamily entity = searchInput.getEntity();
	   if(StringUtils.isNotBlank(entity.getFamCode())){
		   @SuppressWarnings("rawtypes")
		   SingularAttribute[] attributes = new SingularAttribute[]{CatalProductFamily_.famCode};
		   Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		   List<CatalProductFamily> resultList = ejb.findByLike(searchInput.getEntity(),
	          searchInput.getStart(), searchInput.getMax(), attributes);
	      return new CatalProductFamilySearchResult(countLike, detach(resultList),detach(searchInput));
	   } else if (entity.getFeatures()!=null && 
			   (StringUtils.isNotBlank(entity.getFeatures().getFamilyName()) ||
					   StringUtils.isNotBlank(entity.getFeatures().getPurpose()))){
		   @SuppressWarnings("rawtypes")
		   SingularAttribute[] attributes = null;
		   if(StringUtils.isNotBlank(entity.getFeatures().getFamilyName())){
			   attributes = new SingularAttribute[]{CatalFamilyFeatMaping_.familyName};
		   } else if (StringUtils.isNotBlank(entity.getFeatures().getPurpose())){
			   attributes = new SingularAttribute[]{CatalFamilyFeatMaping_.purpose};
		   }
		   
		   CatalFamilyFeatMaping features = entity.getFeatures();
		   Long countLike = familyFeatMapingEJB.countByLike(features, attributes);
		   List<CatalFamilyFeatMaping> featuresList = Collections.emptyList(); 
		   if(countLike>0l){
			   featuresList = familyFeatMapingEJB.findByLike(features, searchInput.getStart(), searchInput.getMax(), attributes);
		   }
		   
		   // Filter result
		   List<CatalProductFamily> resultList = new ArrayList<CatalProductFamily>();
		   Map<String, CatalProductFamily> resultMap = new HashMap<String, CatalProductFamily>();
		   for (CatalFamilyFeatMaping featMapping : featuresList) {
			   CatalProductFamily productFamily = resultMap.get(featMapping.getPfIdentif());
			   if(productFamily!=null){
				   CatalProductFamily p = productFamily;
				   productFamily=new CatalProductFamily();
				   p.copyTo(productFamily);
			   } else {
				   productFamily = ejb.findByIdentif(featMapping.getPfIdentif());
				   resultMap.put(featMapping.getPfIdentif(), productFamily);
			   }
			   productFamily.setFeatures(featMapping);
			   resultList.add(productFamily);
		   }
	      return new CatalProductFamilySearchResult(countLike, detach(resultList),
	              detach(searchInput));
	   } else {
	      Long countLike = 0l;
	      List<CatalProductFamily> resultList = java.util.Collections.emptyList();
	      return new CatalProductFamilySearchResult(countLike, detach(resultList),
	            detach(searchInput));
	   }
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(CatalProductFamilySearchInput searchInput)
   {
      SingularAttribute<CatalProductFamily, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<CatalProductFamily, ?>[] readSeachAttributes(
         CatalProductFamilySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<CatalProductFamily, ?>> result = new ArrayList<SingularAttribute<CatalProductFamily, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = CatalProductFamily_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<CatalProductFamily, ?>) field.get(null));
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

   private CatalProductFamily detach(CatalProductFamily entity)
   {
      if (entity == null)
         return null;

      if(StringUtils.isNotBlank(entity.getParentIdentif()) && entity.getParent()==null){
    	  if(!StringUtils.equals(entity.getParentIdentif(), entity.getIdentif())){
    		  CatalProductFamily parent = ejb.findByIdentif(entity.getParentIdentif());
    		  entity.setParent(parent);
    	  }
      }
      return entity;
   }

   private List<CatalProductFamily> detach(List<CatalProductFamily> list)
   {
      if (list == null)
         return list;
      List<CatalProductFamily> result = new ArrayList<CatalProductFamily>();
      for (CatalProductFamily entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private CatalProductFamilySearchInput detach(CatalProductFamilySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}