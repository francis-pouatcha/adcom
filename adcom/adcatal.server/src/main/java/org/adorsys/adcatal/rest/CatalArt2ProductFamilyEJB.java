package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.repo.CatalArt2ProductFamilyRepository;

@Stateless
public class CatalArt2ProductFamilyEJB {

	@Inject
	private CatalArt2ProductFamilyRepository repository;

	public CatalArt2ProductFamily create(CatalArt2ProductFamily entity) {
		return repository.save(attach(entity));
	}

	public CatalArt2ProductFamily deleteById(String id) {
		CatalArt2ProductFamily entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CatalArt2ProductFamily update(CatalArt2ProductFamily entity) {
		return repository.save(attach(entity));
	}

	public CatalArt2ProductFamily findById(String id) {
		return repository.findBy(id);
	}

	public List<CatalArt2ProductFamily> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalArt2ProductFamily> findBy(CatalArt2ProductFamily entity,
			int start, int max,
			SingularAttribute<CatalArt2ProductFamily, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CatalArt2ProductFamily entity,
			SingularAttribute<CatalArt2ProductFamily, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalArt2ProductFamily> findByLike(
			CatalArt2ProductFamily entity, int start, int max,
			SingularAttribute<CatalArt2ProductFamily, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CatalArt2ProductFamily entity,
			SingularAttribute<CatalArt2ProductFamily, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalArt2ProductFamily attach(CatalArt2ProductFamily entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalArt2ProductFamily findByIdentif(String identif) {
		return findById(identif);
	}
}
