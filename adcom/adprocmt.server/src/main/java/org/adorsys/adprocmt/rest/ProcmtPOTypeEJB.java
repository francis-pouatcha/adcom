package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.ProcmtPOType;
import org.adorsys.adprocmt.repo.ProcmtPOTypeRepository;

@Stateless
public class ProcmtPOTypeEJB {
	@Inject
	private ProcmtPOTypeRepository repository;


	public ProcmtPOType create(ProcmtPOType entity) {
		return repository.save(attach(entity));
	}

	public ProcmtPOType deleteById(String id) {
		ProcmtPOType entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public ProcmtPOType update(ProcmtPOType entity) {
		return repository.save(attach(entity));
	}

	public ProcmtPOType findById(String id) {
		return repository.findBy(id);
	}

	public List<ProcmtPOType> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<ProcmtPOType> findBy(ProcmtPOType entity, int start,
			int max, SingularAttribute<ProcmtPOType, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ProcmtPOType entity,
			SingularAttribute<ProcmtPOType, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<ProcmtPOType> findByLike(ProcmtPOType entity, int start,
			int max, SingularAttribute<ProcmtPOType, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ProcmtPOType entity,
			SingularAttribute<ProcmtPOType, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private ProcmtPOType attach(ProcmtPOType entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public ProcmtPOType findByIdentif(String identif) {
		return findById(identif);
	}
}
