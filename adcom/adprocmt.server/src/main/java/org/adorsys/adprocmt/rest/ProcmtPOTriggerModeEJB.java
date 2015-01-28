package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.ProcmtPOTriggerMode;
import org.adorsys.adprocmt.repo.ProcmtPOTriggerModeRepository;

@Stateless
public class ProcmtPOTriggerModeEJB {
	@Inject
	private ProcmtPOTriggerModeRepository repository;


	public ProcmtPOTriggerMode create(ProcmtPOTriggerMode entity) {
		return repository.save(attach(entity));
	}

	public ProcmtPOTriggerMode deleteById(String id) {
		ProcmtPOTriggerMode entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public ProcmtPOTriggerMode update(ProcmtPOTriggerMode entity) {
		return repository.save(attach(entity));
	}

	public ProcmtPOTriggerMode findById(String id) {
		return repository.findBy(id);
	}

	public List<ProcmtPOTriggerMode> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<ProcmtPOTriggerMode> findBy(ProcmtPOTriggerMode entity, int start,
			int max, SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ProcmtPOTriggerMode entity,
			SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<ProcmtPOTriggerMode> findByLike(ProcmtPOTriggerMode entity, int start,
			int max, SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ProcmtPOTriggerMode entity,
			SingularAttribute<ProcmtPOTriggerMode, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private ProcmtPOTriggerMode attach(ProcmtPOTriggerMode entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public ProcmtPOTriggerMode findByIdentif(String identif) {
		return findById(identif);
	}
}
