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

import org.adorsys.adbase.jpa.BaseConfig;
import org.adorsys.adbase.jpa.BaseConfig_;
import org.adorsys.adbase.jpa.LoginConfiguration;
import org.adorsys.adbase.jpa.LoginConfigurationSearchInput;
import org.adorsys.adbase.jpa.LoginConfigurationSearchResult;
import org.adorsys.adcore.jpa.SearchInput;
import org.adorsys.adcore.jpa.SearchResult;

/**
 * 
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
@Path("/baseconfigs")
public class BaseConfigEndpoint {

	@Inject
	private BaseConfigEJB ejb;

	@POST
	@Consumes({ "application/json", "application/xml" })
	@Produces({ "application/json", "application/xml" })
	public BaseConfig create(BaseConfig entity) {
		return detach(ejb.create(entity));
	}

	@DELETE
	@Path("/{id}")
	public Response deleteById(@PathParam("id") String id) {
		BaseConfig deleted = ejb.deleteById(id);
		if (deleted == null)
			return Response.status(Status.NOT_FOUND).build();

		return Response.ok(detach(deleted)).build();
	}

	@PUT
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public BaseConfig update(BaseConfig entity) {
		return detach(ejb.update(entity));
	}

	@GET
	@Path("/{id}")
	@Produces({ "application/json", "application/xml" })
	public Response findById(@PathParam("id") String id) {
		BaseConfig found = ejb.findById(id);
		if (found == null)
			return Response.status(Status.NOT_FOUND).build();
		return Response.ok(detach(found)).build();
	}

	@GET
	@Produces({ "application/json", "application/xml" })
	public SearchResult<BaseConfig> listAll(
			@QueryParam("start") int start, @QueryParam("max") int max) {
		List<BaseConfig> resultList = ejb.listAll(start, max);
		SearchInput<BaseConfig> searchInput = new SearchInput<BaseConfig>();
		searchInput.setStart(start);
		searchInput.setMax(max);
		return new SearchResult<BaseConfig>((long) resultList.size(),
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
	public SearchResult<BaseConfig> findBy(
			SearchInput<BaseConfig> searchInput) {
		SingularAttribute<BaseConfig, ?>[] attributes = readSeachAttributes(searchInput);
		Long count = ejb.countBy(searchInput.getEntity(), attributes);
		List<BaseConfig> resultList = ejb.findBy(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new SearchResult<BaseConfig>(count, detach(resultList),
				detach(searchInput));
	}

	@POST
	@Path("/countBy")
	@Consumes({ "application/json", "application/xml" })
	public Long countBy(SearchInput<BaseConfig> searchInput) {
		SingularAttribute<BaseConfig, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countBy(searchInput.getEntity(), attributes);
	}

	@POST
	@Path("/findByLike")
	@Produces({ "application/json", "application/xml" })
	@Consumes({ "application/json", "application/xml" })
	public SearchResult<BaseConfig> findByLike(
			SearchInput<BaseConfig> searchInput) {
		SingularAttribute<BaseConfig, ?>[] attributes = readSeachAttributes(searchInput);
		Long countLike = ejb.countByLike(searchInput.getEntity(), attributes);
		List<BaseConfig> resultList = ejb.findByLike(
				searchInput.getEntity(), searchInput.getStart(),
				searchInput.getMax(), attributes);
		return new SearchResult<BaseConfig>(countLike,
				detach(resultList), detach(searchInput));
	}

	@POST
	@Path("/countByLike")
	@Consumes({ "application/json", "application/xml" })
	public Long countByLike(SearchInput<BaseConfig> searchInput) {
		SingularAttribute<BaseConfig, ?>[] attributes = readSeachAttributes(searchInput);
		return ejb.countByLike(searchInput.getEntity(), attributes);
	}

	@SuppressWarnings("unchecked")
	private SingularAttribute<BaseConfig, ?>[] readSeachAttributes(
			SearchInput<BaseConfig> searchInput) {
		List<String> fieldNames = searchInput.getFieldNames();
		List<SingularAttribute<BaseConfig, ?>> result = new ArrayList<SingularAttribute<BaseConfig, ?>>();
		for (String fieldName : fieldNames) {
			Field[] fields = BaseConfig_.class.getFields();
			for (Field field : fields) {
				if (field.getName().equals(fieldName)) {
					try {
						result.add((SingularAttribute<BaseConfig, ?>) field
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

	private BaseConfig detach(BaseConfig entity) {
		if (entity == null)
			return null;

		return entity;
	}

	private List<BaseConfig> detach(List<BaseConfig> list) {
		if (list == null)
			return list;
		List<BaseConfig> result = new ArrayList<BaseConfig>();
		for (BaseConfig entity : list) {
			result.add(detach(entity));
		}
		return result;
	}

	private SearchInput<BaseConfig> detach(
			SearchInput<BaseConfig> searchInput) {
		searchInput.setEntity(detach(searchInput.getEntity()));
		return searchInput;
	}
}