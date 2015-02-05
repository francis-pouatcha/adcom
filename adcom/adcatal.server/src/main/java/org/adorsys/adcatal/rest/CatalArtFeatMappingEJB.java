package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.repo.CatalArtFeatMappingRepository;

@Stateless
public class CatalArtFeatMappingEJB {

	@Inject
	private CatalArtFeatMappingRepository repository;
   @Inject
   private EntityManager em;

	public CatalArtFeatMapping create(CatalArtFeatMapping entity) {
		return repository.save(attach(entity));
	}

	public List<CatalArtFeatMapping> deleteByPic(String pic) {
		List<CatalArtFeatMapping> entities = repository.findByArtIdentif(pic);
		for (CatalArtFeatMapping featMapping : entities) {
			repository.remove(featMapping);
		}
		return entities;
	}

	public CatalArtFeatMapping update(CatalArtFeatMapping entity) {
		return repository.save(attach(entity));
	}

	public CatalArtFeatMapping findById(String id) {
		return repository.findBy(id);
	}

	public List<CatalArtFeatMapping> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
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

	public List<CatalArtFeatMapping> findByLike(CatalArtFeatMapping entity,
			int start, int max,
			SingularAttribute<CatalArtFeatMapping, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public List<CatalArtFeatMapping> findByArtNameStartsWith(String artName,
			int start, int max) {
		String queryStr = "SELECT e FROM CatalArtFeatMapping AS e WHERE LOWER(e.artName) LIKE (LOWER(:artName))";
		TypedQuery<CatalArtFeatMapping> query = em.createQuery(queryStr, CatalArtFeatMapping.class);
		query.setParameter("artName", artName+"%");
		return query.setFirstResult(start).setMaxResults(max).getResultList();
	}
	public Long countByArtNameStartsWith(String artName) {
		String queryStr = "SELECT count(e) FROM CatalArtFeatMapping AS e WHERE LOWER(e.artName) LIKE (LOWER(:artName))";
		TypedQuery<Long> query = em.createQuery(queryStr, Long.class);
		query.setParameter("artName", artName+"%");
		return query.getSingleResult();
	}
	
	public Long countByLike(CatalArtFeatMapping entity,
			SingularAttribute<CatalArtFeatMapping, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalArtFeatMapping attach(CatalArtFeatMapping entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalArtFeatMapping findByIdentif(String identif) {
		List<CatalArtFeatMapping> resultList = repository
				.findByIdentif(identif).maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}
}
