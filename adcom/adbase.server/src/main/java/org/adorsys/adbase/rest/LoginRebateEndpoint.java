package org.adorsys.adbase.rest;

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

import org.adorsys.adbase.jpa.LoginRebate;
import org.adorsys.adbase.jpa.LoginRebate_;
import org.adorsys.adbase.jpa.LoginRebateSearchInput;
import org.adorsys.adbase.jpa.LoginRebateSearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/loginrebates")
public class LoginRebateEndpoint {

	@Inject
	private LoginRebateEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public LoginRebate create(LoginRebate entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		LoginRebate deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public LoginRebate update(LoginRebate entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		LoginRebate found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public LoginRebateSearchResult listAll(@QueryParam("start") int start,
			@QueryParam("max") int max) {
		List<LoginRebate> resultList = ejb.listAll(start, max);
		LoginRebateSearchInput searchInput = new LoginRebateSearchInput();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new LoginRebateSearchResult((long) resultList.size(),
				detach(resultList), detach(searchInput));
	}

	@GET
	@Path("previousLogin/{loginName}")
	@Produces({ "application/json", "application/xml" })
	public Response previousLoginRebate(@PathParam("loginName") String loginName) {
		List<LoginRebate> found;
		try {
			found = ejb.findPreviousLogin(loginName);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
	}

	@GET
	@Path("nextLogin/{loginName}")
	@Produces({ "application/json", "application/xml" })
	public Response nextLoginRebate(@PathParam("loginName") String loginName) {
		List<LoginRebate> found;
		try {
			found = ejb.findNextLogin(loginName);
		} catch (Exception e) {
			return Response.status(Status.NOT_FOUND).build();
		}
		if (found.isEmpty()) {
			return Response.status(Status.NOT_FOUND).build();
		}
		return Response.ok(detach(found.iterator().next())).build();
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
	public LoginRebateSearchResult findBy(LoginRebateSearchInput searchInput) {
		SingularAttribute<LoginRebate, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<LoginRebate> resultList = ejb.findBy(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new LoginRebateSearchResult(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(LoginRebateSearchInput searchInput) {
		SingularAttribute<LoginRebate, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public LoginRebateSearchResult findByLike(LoginRebateSearchInput searchInput) {
		SingularAttribute<LoginRebate, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<LoginRebate> resultList = ejb.findByLike(searchInput.getEntity(),
				searchInput.getStart(), searchInput.getMax(), attributes);
		return new LoginRebateSearchResult(countLike, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(LoginRebateSearchInput searchInput) {
		SingularAttribute<LoginRebate, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<LoginRebate, ?>[] readSeachAttributes(
			LoginRebateSearchInput searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<LoginRebate, ?>> result = new ArrayList<SingularAttribute<LoginRebate, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = LoginRebate_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<LoginRebate, ?>) field
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

	private LoginRebate detach(LoginRebate entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<LoginRebate> detach(List<LoginRebate> list) {
		if (list == null)
			return list;
		List<LoginRebate> result = new ArrayList<LoginRebate>();
		for (LoginRebate entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private LoginRebateSearchInput detach(LoginRebateSearchInput searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}