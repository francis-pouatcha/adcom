package org.adorsys.adaptmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmtRepportLogin;
import org.adorsys.adaptmt.repo.AptAptmtRepportLoginRepository;
import org.adorsys.adcore.utils.SequenceGenerator;

@Stateless
public class AptAptmtRepportLoginEJB {

	@Inject
	private AptAptmtRepportLoginRepository repository;

	public AptAptmtRepportLogin create(AptAptmtRepportLogin entity) {
		entity.setIdentif(SequenceGenerator
				.getSequence(SequenceGenerator.APPOINTMENTLOGIN_NUMBER_SEQUENCE_PREFIXE));

		return repository.save(attach(entity));
	}

	public AptAptmtRepportLogin deleteById(String id) {
		AptAptmtRepportLogin entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public AptAptmtRepportLogin update(AptAptmtRepportLogin entity) {
		return repository.save(attach(entity));
	}

	public AptAptmtRepportLogin findById(String id) {
		return repository.findBy(id);
	}

	public List<AptAptmtRepportLogin> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<AptAptmtRepportLogin> findBy(AptAptmtRepportLogin entity,
			int start, int max,
			SingularAttribute<AptAptmtRepportLogin, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(AptAptmtRepportLogin entity,
			SingularAttribute<AptAptmtRepportLogin, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<AptAptmtRepportLogin> findByLike(AptAptmtRepportLogin entity,
			int start, int max,
			SingularAttribute<AptAptmtRepportLogin, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(AptAptmtRepportLogin entity,
			SingularAttribute<AptAptmtRepportLogin, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private AptAptmtRepportLogin attach(AptAptmtRepportLogin entity) {
		if (entity == null)
			return null;

		return entity;
	}

}
