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
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchInput;
import org.adorsys.adprocmt.jpa.PrcmtPOItemSearchResult;
import org.adorsys.adprocmt.jpa.PrcmtPOItem_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/prcmtpoitems")
public class PrcmtPOItemEndpoint
{

   @Inject
   private PrcmtPOItemEJB ejb;
   @Inject
	private PdfReportTemplate<PrcmtPOItem> pdfReportTemplate;
   @Inject
	private SecurityUtil securityUtil;


   @POST
   @Consumes({ "application/json", "application/xml" })
   @Produces({ "application/json", "application/xml" })
   public PrcmtPOItem create(PrcmtPOItem entity)
   {
      return detach(ejb.create(entity));
   }

   @DELETE
   @Path("/{id}")
   public Response deleteById(@PathParam("id") String id)
   {
      PrcmtPOItem deleted = ejb.deleteById(id);
      if (deleted == null)
         return Response.status(Status.NOT_FOUND).build();

      return Response.ok(detach(deleted)).build();
   }

   @PUT
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtPOItem update(PrcmtPOItem entity)
   {
      return detach(ejb.update(entity));
   }

   @GET
   @Path("/{id}")
   @Produces({ "application/json", "application/xml" })
   public Response findById(@PathParam("id") String id)
   {
      PrcmtPOItem found = ejb.findById(id);
      if (found == null)
         return Response.status(Status.NOT_FOUND).build();
      return Response.ok(detach(found)).build();
   }

   @GET
   @Produces({ "application/json", "application/xml" })
   public PrcmtPOItemSearchResult listAll(@QueryParam("start") int start,
         @QueryParam("max") int max)
   {
      List<PrcmtPOItem> resultList = ejb.listAll(start, max);
      PrcmtPOItemSearchInput searchInput = new PrcmtPOItemSearchInput();
      searchInput.setStart(start);
      searchInput.setMax(max);
      return new PrcmtPOItemSearchResult((long) resultList.size(),
            detach(resultList), detach(searchInput));
   }

   @GET
   @Path("/count")
   public Long count()
   {
      return ejb.count();
   }
   
   @GET
  	@Path("/orderreport.pdf/{poNbr}")
  	@Produces({ "application/json", "application/xml","application/pdf","application/octet-stream" })
  	public Response buildCshdwrreportPdfReport(@PathParam("poNbr") String poNbr,@Context HttpServletResponse response) throws AdException 
  	{	
  	   	String loginName = securityUtil.getCurrentLoginName();
  		String lang = securityUtil.getUserLange();
  		List<PrcmtPOItem> resultList = ejb.findByPoNbr(poNbr);
  		 OutputStream os = null ;
  		
  		List<String> fields = new ArrayList<String>();
		 fields.add("artPic");
		 fields.add("artName");
		 fields.add("qtyOrdered");
		 fields.add("freeQty");
		 fields.add("stkQtyPreOrder");
		 fields.add("pppuPreTax");
		 fields.add("strgSection");
		 fields.add("supplier");
  		try {
  			ByteArrayOutputStream baos = pdfReportTemplate.build(resultList, PrcmtPOItem.class, fields, loginName, lang);
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


   @POST
   @Path("/findBy")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtPOItemSearchResult findBy(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long count = ejb.countBy(searchInput.getEntity(), attributes);
      List<PrcmtPOItem> resultList = ejb.findBy(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtPOItemSearchResult(count, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countBy")
   @Consumes({ "application/json", "application/xml" })
   public Long countBy(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countBy(searchInput.getEntity(), attributes);
   }

   @POST
   @Path("/findByLike")
   @Produces({ "application/json", "application/xml" })
   @Consumes({ "application/json", "application/xml" })
   public PrcmtPOItemSearchResult findByLike(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItem, ?>[] attributes = readSeachAttributes(searchInput);
      Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
      List<PrcmtPOItem> resultList = ejb.findByLike(searchInput.getEntity(),
            searchInput.getStart(), searchInput.getMax(), attributes);
      return new PrcmtPOItemSearchResult(countLike, detach(resultList),
            detach(searchInput));
   }

   @POST
   @Path("/countByLike")
   @Consumes({ "application/json", "application/xml" })
   public Long countByLike(PrcmtPOItemSearchInput searchInput)
   {
      SingularAttribute<PrcmtPOItem, ?>[] attributes = readSeachAttributes(searchInput);
      return ejb.countByLike(searchInput.getEntity(), attributes);
   }

   @SuppressWarnings("unchecked")
   private SingularAttribute<PrcmtPOItem, ?>[] readSeachAttributes(
         PrcmtPOItemSearchInput searchInput)
   {
      List<String> fieldNames = searchInput.getFieldNames();
      List<SingularAttribute<PrcmtPOItem, ?>> result = new ArrayList<SingularAttribute<PrcmtPOItem, ?>>();
      for (String fieldName : fieldNames)
      {
         Field[] fields = PrcmtPOItem_.class.getFields();
         for (Field field : fields)
         {
            if (field.getName().equals(fieldName))
            {
               try
               {
                  result.add((SingularAttribute<PrcmtPOItem, ?>) field.get(null));
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

   

   private PrcmtPOItem detach(PrcmtPOItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   private List<PrcmtPOItem> detach(List<PrcmtPOItem> list)
   {
      if (list == null)
         return list;
      List<PrcmtPOItem> result = new ArrayList<PrcmtPOItem>();
      for (PrcmtPOItem entity : list)
      {
         result.add(detach(entity));
      }
      return result;
   }

   private PrcmtPOItemSearchInput detach(PrcmtPOItemSearchInput searchInput)
   {
      searchInput.setEntity(detach(searchInput.getEntity()));
      return searchInput;
   }
}