package org.adorsys.adbase.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.ConnectionHistory;
import org.adorsys.adbase.repo.ConnectionHistoryRepository;

@Stateless
public class ConnectionHistoryEJB {

	@Inject
	private ConnectionHistoryRepository repository;

	public ConnectionHistory create(ConnectionHistory entity) {
		return repository.save(attach(entity));
	}

	public ConnectionHistory deleteById(String id) {
		ConnectionHistory entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public ConnectionHistory update(ConnectionHistory entity) {
		return repository.save(attach(entity));
	}

	public ConnectionHistory findById(String id) {
		return repository.findBy(id);
	}

	public List<ConnectionHistory> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<ConnectionHistory> findBy(ConnectionHistory entity, int start,
			int max, SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(ConnectionHistory entity,
			SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<ConnectionHistory> findByLike(ConnectionHistory entity,
			int start, int max,
			SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(ConnectionHistory entity,
			SingularAttribute<ConnectionHistory, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private ConnectionHistory attach(ConnectionHistory entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public ConnectionHistory findByLoginName(String loginName) {
		List<ConnectionHistory> resultList = repository.findByLoginName(loginName).orderDesc("lastLoginDate").maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
}
