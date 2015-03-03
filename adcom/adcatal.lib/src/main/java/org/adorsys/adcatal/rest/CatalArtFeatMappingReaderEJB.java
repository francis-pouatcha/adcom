package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.repository.CatalArtFeatMappingRepository;

/**
 * This feature mapping reader can be used to seach for article by their names. This
 * can allo each module to directly expose the search of the product catalogue.
 * 
 * @author francis
 *
 */
@Stateless
public class CatalArtFeatMappingReaderEJB {

	@Inject
	private CatalArtFeatMappingRepository repository;
	@Inject
	private EntityManager em;

	public CatalArtFeatMapping findByIdentif(String identif) {
		List<CatalArtFeatMapping> resultList = repository
				.findByIdentif(identif).maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<CatalArtFeatMapping> findBy(CatalArtFeatMapping entity,
			int start, int max,
			SingularAttribute<CatalArtFeatMapping, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CatalArtFeatMapping entity,
			SingularAttribute<CatalArtFeatMapping, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public Long countByLike(CatalArtFeatMapping entity,
			SingularAttribute<CatalArtFeatMapping, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}
	public List<CatalArtFeatMapping> findByLike(CatalArtFeatMapping entity,
			int start, int max,
			SingularAttribute<CatalArtFeatMapping, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public List<CatalArtFeatMapping> findByArtNameStartsWith(String artName,
			int start, int max) {
		String queryStr = "SELECT e FROM CatalArtFeatMapping AS e WHERE LOWER(e.artName) LIKE (LOWER(:artName))";
		TypedQuery<CatalArtFeatMapping> query = em.createQuery(queryStr,
				CatalArtFeatMapping.class);
		query.setParameter("artName", artName + "%");
		return query.setFirstResult(start).setMaxResults(max).getResultList();
	}

	public Long countByArtNameStartsWith(String artName) {
		String queryStr = "SELECT count(e) FROM CatalArtFeatMapping AS e WHERE LOWER(e.artName) LIKE (LOWER(:artName))";
		TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
		query.setParameter("artName", artName + "%");
		return query.getSingleResult();
	}
}
