package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.repo.CatalFamilyFeatMapingRepository;

@Stateless
public class CatalFamilyFeatMapingEJB {

	@Inject
	private CatalFamilyFeatMapingRepository repository;

	public CatalFamilyFeatMaping create(CatalFamilyFeatMaping entity) {
		return repository.save(attach(entity));
	}

	public List<CatalFamilyFeatMaping> deleteByPfIdentif(String identif) {
		List<CatalFamilyFeatMaping> entities = repository
				.findByPfIdentif(identif);
		for (CatalFamilyFeatMaping entity : entities) {
			repository.remove(entity);
		}
		return entities;
	}

	public CatalFamilyFeatMaping update(CatalFamilyFeatMaping entity) {
		return repository.save(attach(entity));
	}

	public CatalFamilyFeatMaping findById(String id) {
		return repository.findBy(id);
	}

	public List<CatalFamilyFeatMaping> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalFamilyFeatMaping> findBy(CatalFamilyFeatMaping entity,
			int start, int max,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CatalFamilyFeatMaping entity,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalFamilyFeatMaping> findByLike(CatalFamilyFeatMaping entity,
			int start, int max,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CatalFamilyFeatMaping entity,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalFamilyFeatMaping attach(CatalFamilyFeatMaping entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalFamilyFeatMaping findByIdentif(String identif, Date validOn) {
		List<CatalFamilyFeatMaping> resultList = repository
				.findByIdentif(identif).maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

}
