package org.adorsys.adprocmt.rest;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.exceptions.AdException;
import org.adorsys.adcore.pdfreport.PdfReportTemplate;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchInput;
import org.adorsys.adprocmt.jpa.PrcmtDeliverySearchResult;
import org.adorsys.adprocmt.jpa.PrcmtDelivery_;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtdeliverys")
public class PrcmtDeliveryEndpoint
{

   @Inject
   private PrcmtDeliveryEJB ejb;
   @Inject
 	private PdfReportTemplate<PrcmtDlvryItem> pdfReportTemplate;
    @Inject
 	private SecurityUtil securityUtil;
    @Inject
    private PrcmtDlvryItemEJB prcmtDlvryItemEJB;


   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtDelivery create(PrcmtDelivery entity)
   {
      return detach(ejb.createCustom(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PrcmtDelivery deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }
   
   @GET
	@Path("/deliveryreport.pdf/{dlvryNbr}")
	@Produces({ "application/json", "application/xml","application/pdf","application/octet-stream" })
	public Response buildCshdwrreportPdfReport(@PathParam("dlvryNbr") String dlvryNbr,@Context HttpServletResponse response) throws AdException 
	{	
	   	String loginName = securityUtil.getCurrentLoginName();
		String lang = securityUtil.getUserLange();
		List<PrcmtDlvryItem> resultList = prcmtDlvryItemEJB.findByDlvryNbr(dlvryNbr);
		 OutputStream os = null ;
		try {
			ByteArrayOutputStream baos = pdfReportTemplate.build(resultList, PrcmtDlvryItem.class,loginName, lang);
         // the contentlength
         response.setContentLength(baos.size());
         // write ByteArrayOutputStream to the ServletOutputStream
         os = response.getOutputStream();
         baos.writeTo(os);
         os.flush();
         os.close();
			  
		} catch (Exception e) {
			throw new AdException("Error printing");
		}
	
		return	Response.ok(os).
		header("Content-Disposition",
				"attachment; filename=localitiesreport.pdf")
		 .build();
	}

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtDelivery update(PrcmtDelivery entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PrcmtDelivery found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PrcmtDeliverySearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PrcmtDelivery> resultList = ejb.listAll(start, max);
      PrcmtDeliverySearchInput searchInput = new PrcmtDeliverySearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PrcmtDeliverySearchResult((long) resultList.size(),
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
   public PrcmtDeliverySearchResult findBy(PrcmtDeliverySearchInput searchInput)
   {
      SingularAttribute<PrcmtDelivery, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PrcmtDelivery> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtDeliverySearchResult(count, detach(resultList),
            detach(searchInput));
   }
   
   @POST
   @Path("/findCustom")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtDeliverySearchResult findCustom(PrcmtDeliverySearchInput searchInput)
   {
      return ejb.findCustom(searchInput);
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PrcmtDeliverySearchInput searchInput)
   {
      SingularAttribute<PrcmtDelivery, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtDeliverySearchResult findByLike(PrcmtDeliverySearchInput searchInput)
   {
      SingularAttribute<PrcmtDelivery, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PrcmtDelivery> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtDeliverySearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PrcmtDeliverySearchInput searchInput)
   {
      SingularAttribute<PrcmtDelivery, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PrcmtDelivery, ?>[] readSeachAttributes(
         PrcmtDeliverySearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PrcmtDelivery, ?>> result = new ArrayList<SingularAttribute<PrcmtDelivery, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PrcmtDelivery_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PrcmtDelivery, ?>) field.get(null));
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

   

   private PrcmtDelivery detach(PrcmtDelivery entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PrcmtDelivery> detach(List<PrcmtDelivery> list)
   {
      if (list == null)
         return list;
      List<PrcmtDelivery> result = new ArrayList<PrcmtDelivery>();
      for (PrcmtDelivery entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PrcmtDeliverySearchInput detach(PrcmtDeliverySearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}