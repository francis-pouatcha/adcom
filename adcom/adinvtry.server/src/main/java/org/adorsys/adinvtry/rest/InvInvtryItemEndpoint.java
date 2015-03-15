package org.adorsys.adinvtry.rest;

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

import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItem_;
import org.adorsys.adinvtry.jpa.InvInvtryItemSearchInput;
import org.adorsys.adinvtry.jpa.InvInvtryItemSearchResult;
import org.apache.commons.lang3.StringUtils;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/invinvtryitems")
public class InvInvtryItemEndpoint
{

   @Inject
   private InvInvtryItemEJB ejb;

   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public InvInvtryItem create(InvInvtryItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      InvInvtryItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InvInvtryItem update(InvInvtryItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      InvInvtryItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public InvInvtryItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<InvInvtryItem> resultList = ejb.listAll(start, max);
      InvInvtryItemSearchInput searchInput = new InvInvtryItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new InvInvtryItemSearchResult((long) resultList.size(),
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
   public InvInvtryItemSearchResult findBy(InvInvtryItemSearchInput searchInput)
   {
      SingularAttribute<InvInvtryItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<InvInvtryItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InvInvtryItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(InvInvtryItemSearchInput searchInput)
   {
      SingularAttribute<InvInvtryItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public InvInvtryItemSearchResult findByLike(InvInvtryItemSearchInput searchInput)
   {
	   if(StringUtils.isNotBlank(searchInput.getEntity().getInvtryNbr())){
		   if(StringUtils.isNotBlank(searchInput.getRangeStart()) || StringUtils.isNotBlank(searchInput.getRangeEnd())){
			   if(Boolean.TRUE.equals(searchInput.getA2z())){
				   if(StringUtils.isNotBlank(searchInput.getEntity().getSection())){
					   return findByInvtryNbrAndSectionInRangeSorted(searchInput);
				   }
				   return findByInvtryNbrInRangeSorted(searchInput);
			   } else {
				   if(StringUtils.isNotBlank(searchInput.getEntity().getSection())){
					   return findByInvtryNbrAndSectionInRange(searchInput);
				   }
				   return findByInvtryNbrInRange(searchInput);
			   }
		   }
		   if(Boolean.TRUE.equals(searchInput.getA2z())){
			   if(StringUtils.isNotBlank(searchInput.getEntity().getSection())){
				   return findByInvtryNbrAndSectionSorted(searchInput);
			   }
			   return findByInvtryNbrSorted(searchInput);
		   }
	   }
      SingularAttribute<InvInvtryItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<InvInvtryItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new InvInvtryItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   private InvInvtryItemSearchResult findByInvtryNbrInRange(
		InvInvtryItemSearchInput searchInput) {
	   List<InvInvtryItem> resultList = ejb.findByInvtryNbrInRange(
			   searchInput.getEntity().getInvtryNbr(),
			   searchInput.getRangeStart(), searchInput.getRangeEnd(),
			   searchInput.getStart(), searchInput.getMax(), false);
	   Long countLike = ejb.countByInvtryNbrInRange(searchInput.getEntity().getInvtryNbr(), 
			   searchInput.getRangeStart(), searchInput.getRangeEnd());
       return new InvInvtryItemSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }

	private InvInvtryItemSearchResult findByInvtryNbrAndSectionInRange(
			InvInvtryItemSearchInput searchInput) {
		   List<InvInvtryItem> resultList = ejb.findByInvtryNbrAndSectionInRange(
				   searchInput.getEntity().getInvtryNbr(),searchInput.getEntity().getSection(),
				   searchInput.getRangeStart(), searchInput.getRangeEnd(),
				   searchInput.getStart(), searchInput.getMax(), false);
		   Long countLike = ejb.countByInvtryNbrAndSectionInRange(searchInput.getEntity().getInvtryNbr(),
				   searchInput.getEntity().getSection(),
				   searchInput.getRangeStart(), searchInput.getRangeEnd());
	       return new InvInvtryItemSearchResult(countLike, detach(resultList),
	              detach(searchInput));
	}

	private InvInvtryItemSearchResult findByInvtryNbrInRangeSorted(
			InvInvtryItemSearchInput searchInput) {
		   List<InvInvtryItem> resultList = ejb.findByInvtryNbrInRange(
				   searchInput.getEntity().getInvtryNbr(),
				   searchInput.getRangeStart(), searchInput.getRangeEnd(),
				   searchInput.getStart(), searchInput.getMax(), true);
		   Long countLike = ejb.countByInvtryNbrInRange(searchInput.getEntity().getInvtryNbr(), 
				   searchInput.getRangeStart(), searchInput.getRangeEnd());
	       return new InvInvtryItemSearchResult(countLike, detach(resultList),
	              detach(searchInput));
	}
	
	private InvInvtryItemSearchResult findByInvtryNbrAndSectionInRangeSorted(
			InvInvtryItemSearchInput searchInput) {
		   List<InvInvtryItem> resultList = ejb.findByInvtryNbrAndSectionInRange(
				   searchInput.getEntity().getInvtryNbr(),searchInput.getEntity().getSection(),
				   searchInput.getRangeStart(), searchInput.getRangeEnd(),
				   searchInput.getStart(), searchInput.getMax(), true);
		   Long countLike = ejb.countByInvtryNbrAndSectionInRange(searchInput.getEntity().getInvtryNbr(),
				   searchInput.getEntity().getSection(),
				   searchInput.getRangeStart(), searchInput.getRangeEnd());
	       return new InvInvtryItemSearchResult(countLike, detach(resultList),
	              detach(searchInput));
	}

	private InvInvtryItemSearchResult findByInvtryNbrSorted(InvInvtryItemSearchInput searchInput){
	   List<InvInvtryItem> resultList = ejb.findByInvtryNbrSorted(searchInput.getEntity().getInvtryNbr(), searchInput.getStart(), searchInput.getMax());
	   Long countLike = ejb.countByInvtryNbr(searchInput.getEntity().getInvtryNbr());
       return new InvInvtryItemSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }

   private InvInvtryItemSearchResult findByInvtryNbrAndSectionSorted(InvInvtryItemSearchInput searchInput){
	   List<InvInvtryItem> resultList = ejb.findByInvtryNbrAndSectionSorted(searchInput.getEntity().getInvtryNbr(), searchInput.getEntity().getSection(), searchInput.getStart(), searchInput.getMax());
	   Long countLike = ejb.countByInvtryNbrAndSection(searchInput.getEntity().getInvtryNbr(), searchInput.getEntity().getSection());
       return new InvInvtryItemSearchResult(countLike, detach(resultList),
              detach(searchInput));
   }
   
   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(InvInvtryItemSearchInput searchInput)
   {
      SingularAttribute<InvInvtryItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<InvInvtryItem, ?>[] readSeachAttributes(
         InvInvtryItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<InvInvtryItem, ?>> result = new ArrayList<SingularAttribute<InvInvtryItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = InvInvtryItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<InvInvtryItem, ?>) field.get(null));
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

   

   private InvInvtryItem detach(InvInvtryItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<InvInvtryItem> detach(List<InvInvtryItem> list)
   {
      if (list == null)
         return list;
      List<InvInvtryItem> result = new ArrayList<InvInvtryItem>();
      for (InvInvtryItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private InvInvtryItemSearchInput detach(InvInvtryItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}