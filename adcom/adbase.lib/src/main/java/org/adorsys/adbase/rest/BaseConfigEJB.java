package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.BaseConfig;
import org.adorsys.adbase.jpa.LoginConfiguration;
import org.adorsys.adbase.repo.BaseConfigRepository;

@Stateless
public class BaseConfigEJB {

	@Inject
	private BaseConfigRepository repository;

	public BaseConfig create(BaseConfig entity) {
		return repository.save(attach(entity));
	}

	public BaseConfig deleteById(String id) {
		BaseConfig entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public BaseConfig update(BaseConfig entity) {
		return repository.save(attach(entity));
	}

	public BaseConfig findById(String id) {
		return repository.findBy(id);
	}

	public List<BaseConfig> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<BaseConfig> findBy(BaseConfig entity, int start, int max,
			SingularAttribute<BaseConfig, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(BaseConfig entity,
			SingularAttribute<BaseConfig, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<BaseConfig> findByLike(BaseConfig entity, int start, int max,
			SingularAttribute<BaseConfig, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(BaseConfig entity,
			SingularAttribute<BaseConfig, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private BaseConfig attach(BaseConfig entity) {
		if (entity == null)
			return null;

		return entity;
	}

}
