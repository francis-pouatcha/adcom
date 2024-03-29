package org.adorsys.adcost.rest;

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

import org.adorsys.adcost.jpa.CstActivityCenter;
import org.adorsys.adcost.jpa.CstActivityCenterSearchInput;
import org.adorsys.adcost.jpa.CstActivityCenterSearchResult;
import org.adorsys.adcost.jpa.CstActivityCenter_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/CstActivityCenters")
public class CstActivityCenterEndpoint {

	@Inject
	private CstActivityCenterEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public CstActivityCenter create(CstActivityCenter entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		CstActivityCenter deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CstActivityCenter update(CstActivityCenter entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		CstActivityCenter found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public CstActivityCenterSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<CstActivityCenter> resultList = ejb.listAll(start, max);
		CstActivityCenterSearchInput searchInput = new CstActivityCenterSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new CstActivityCenterSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("/count")
	public Long count() {
		return ejb.count();
	}

	@POST
	@Path("/findBy")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CstActivityCenterSearchResult findBy(CstActivityCenterSearchInput searchInput) {
		SingularAttribute<CstActivityCenter, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<CstActivityCenter> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CstActivityCenterSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(CstActivityCenterSearchInput searchInput) {
		SingularAttribute<CstActivityCenter, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public CstActivityCenterSearchResult findByLike(CstActivityCenterSearchInput searchInput) {
		SingularAttribute<CstActivityCenter, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<CstActivityCenter> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new CstActivityCenterSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(CstActivityCenterSearchInput searchInput) {
		SingularAttribute<CstActivityCenter, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<CstActivityCenter, ?>[] readSeachAttributes(
			CstActivityCenterSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<CstActivityCenter, ?>> result = new ArrayList<SingularAttribute<CstActivityCenter, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = CstActivityCenter_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<CstActivityCenter, ?>) field
								.get(null));
					} catch (IllegalArgumentException e) {
						throw new IllegalStateException(e);
					} catch (IllegalAccessException e) {
						throw new IllegalStateException(e);
					}
				}
			}
		}
		return result.toArray(new SingularAttribute[result.size()]);
	}

	private CstActivityCenter detach(CstActivityCenter entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<CstActivityCenter> detach(List<CstActivityCenter> list) {
		if (list == null)
			return list;
		List<CstActivityCenter> result = new ArrayList<CstActivityCenter>();
		for (CstActivityCenter entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private CstActivityCenterSearchInput detach(CstActivityCenterSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}