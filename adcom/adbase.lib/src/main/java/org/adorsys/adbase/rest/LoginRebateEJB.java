package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.LoginRebate;
import org.adorsys.adbase.repo.LoginRebateRepository;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class LoginRebateEJB {

	@Inject
	private LoginRebateRepository repository;

	public LoginRebate create(LoginRebate entity) {

		entity.setIdentif(entity.getLoginName());
		entity.setId(entity.getLoginName());
		return repository.save(attach(entity));
	}

	public LoginRebate deleteById(String id) {
		LoginRebate entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public LoginRebate update(LoginRebate entity) {
		return repository.save(attach(entity));
	}

	public LoginRebate findById(String id) {
		return repository.findBy(id);
	}

	public List<LoginRebate> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<LoginRebate> findBy(LoginRebate entity, int start, int max,
			SingularAttribute<LoginRebate, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(LoginRebate entity,
			SingularAttribute<LoginRebate, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<LoginRebate> findByLike(LoginRebate entity, int start, int max,
			SingularAttribute<LoginRebate, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(LoginRebate entity,
			SingularAttribute<LoginRebate, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private LoginRebate attach(LoginRebate entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public LoginRebate findByIdentif(String identif, Date validOn) {
		List<LoginRebate> resultList = repository.findByIdentif(identif)
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<LoginRebate> findPreviousLogin(String identif) {
		return repository.findPreviousLoginRebate(identif).maxResults(2)
				.getResultList();
	}

	public List<LoginRebate> findNextLogin(String identif) {
		return repository.findNextLoginRebate(identif).maxResults(2)
				.getResultList();
	}
	
	public List<LoginRebate> findByLogin(String loginName){
		return repository.findByLogin(loginName).getResultList();
	}

}
