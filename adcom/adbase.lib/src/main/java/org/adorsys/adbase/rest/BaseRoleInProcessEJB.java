package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseRoleInProcess;
import org.adorsys.adbase.repo.BaseRoleInProcessRepository;

@Stateless
public class BaseRoleInProcessEJB {
	@Inject
	private BaseRoleInProcessRepository repository;


	public BaseRoleInProcess create(BaseRoleInProcess entity) {
		return repository.save(attach(entity));
	}

	public BaseRoleInProcess deleteById(String id) {
		BaseRoleInProcess entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseRoleInProcess update(BaseRoleInProcess entity) {
		return repository.save(attach(entity));
	}

	public BaseRoleInProcess findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseRoleInProcess> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseRoleInProcess> findBy(BaseRoleInProcess entity, int start,
			int max, SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseRoleInProcess entity,
			SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseRoleInProcess> findByLike(BaseRoleInProcess entity, int start,
			int max, SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseRoleInProcess entity,
			SingularAttribute<BaseRoleInProcess, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseRoleInProcess attach(BaseRoleInProcess entity) {
		if (entity == null)
			return null;

		return entity;
	}
	
	public BaseRoleInProcess findByIdentif(String identif) {
		return findById(identif);
	}
}
