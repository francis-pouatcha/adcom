package org.adorsys.adaptmt.rest;

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

import org.adorsys.adaptmt.jpa.AptAptmtRepportLogin;
import org.adorsys.adaptmt.jpa.AptAptmtRepportLoginSearchInput;
import org.adorsys.adaptmt.jpa.AptAptmtRepportLoginSearchResult;
import org.adorsys.adaptmt.jpa.AptAptmtRepportLogin_;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/AptAptmtRepportLogins")
public class AptAptmtRepportLoginEndpoint {
	@Inject
	private AptAptmtRepportLoginEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public AptAptmtRepportLogin create(AptAptmtRepportLogin entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		AptAptmtRepportLogin deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtRepportLogin update(AptAptmtRepportLogin entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		AptAptmtRepportLogin found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public AptAptmtRepportLoginSearchResult listAll(
			@QueryParam("start") int start, @QueryParam("max") int max) {
		List<AptAptmtRepportLogin> resultList = ejb.listAll(start, max);
		AptAptmtRepportLoginSearchInput searchInput = new AptAptmtRepportLoginSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new AptAptmtRepportLoginSearchResult((long) resultList.size(),
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
	public AptAptmtRepportLoginSearchResult findBy(
			AptAptmtRepportLoginSearchInput searchInput) {
		SingularAttribute<AptAptmtRepportLogin, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<AptAptmtRepportLogin> resultList = ejb.findBy(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new AptAptmtRepportLoginSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(AptAptmtRepportLoginSearchInput searchInput) {
		SingularAttribute<AptAptmtRepportLogin, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public AptAptmtRepportLoginSearchResult findByLike(
			AptAptmtRepportLoginSearchInput searchInput) {
		SingularAttribute<AptAptmtRepportLogin, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<AptAptmtRepportLogin> resultList = ejb.findByLike(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new AptAptmtRepportLoginSearchResult(countLike,
				detach(resultList), detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(AptAptmtRepportLoginSearchInput searchInput) {
		SingularAttribute<AptAptmtRepportLogin, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<AptAptmtRepportLogin, ?>[] readSeachAttributes(
			AptAptmtRepportLoginSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<AptAptmtRepportLogin, ?>> result = new ArrayList<SingularAttribute<AptAptmtRepportLogin, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = AptAptmtRepportLogin_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<AptAptmtRepportLogin, ?>) field
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

	private AptAptmtRepportLogin detach(AptAptmtRepportLogin entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<AptAptmtRepportLogin> detach(List<AptAptmtRepportLogin> list) {
		if (list == null)
			return list;
		List<AptAptmtRepportLogin> result = new ArrayList<AptAptmtRepportLogin>();
		for (AptAptmtRepportLogin entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private AptAptmtRepportLoginSearchInput detach(
			AptAptmtRepportLoginSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}

}
