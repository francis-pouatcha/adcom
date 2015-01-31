package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.adorsys.adcatal.repo.CatalPkgModeRepository;

@Stateless
public class CatalPkgModeEJB {

	@Inject
	private CatalPkgModeRepository repository;

	public CatalPkgMode create(CatalPkgMode entity) {
		return repository.save(attach(entity));
	}

	public CatalPkgMode deleteById(String id) {
		CatalPkgMode entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CatalPkgMode deleteCustomById(String id) {
		CatalPkgMode entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CatalPkgMode update(CatalPkgMode entity) {
		return repository.save(attach(entity));
	}

	public CatalPkgMode findById(String id) {
		return repository.findBy(id);
	}

	public List<CatalPkgMode> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalPkgMode> findBy(CatalPkgMode entity, int start, int max,
			SingularAttribute<CatalPkgMode, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CatalPkgMode entity,
			SingularAttribute<CatalPkgMode, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalPkgMode> findByLike(CatalPkgMode entity, int start,
			int max, SingularAttribute<CatalPkgMode, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CatalPkgMode entity,
			SingularAttribute<CatalPkgMode, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalPkgMode attach(CatalPkgMode entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalPkgMode findByIdentif(String identif) {
		return findById(identif);
	}
}
