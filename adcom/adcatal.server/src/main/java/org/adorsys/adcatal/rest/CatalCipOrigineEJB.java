package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.adorsys.adcatal.repo.CatalCipOrigineRepository;

@Stateless
public class CatalCipOrigineEJB {

	@Inject
	private CatalCipOrigineRepository repository;

	public CatalCipOrigine create(CatalCipOrigine entity) {
		return repository.save(attach(entity));
	}

	public CatalCipOrigine deleteById(String id) {
		CatalCipOrigine entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CatalCipOrigine deleteCustomById(String id) {
		CatalCipOrigine entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CatalCipOrigine update(CatalCipOrigine entity) {
		return repository.save(attach(entity));
	}

	public CatalCipOrigine findById(String id) {
		return repository.findBy(id);
	}

	public List<CatalCipOrigine> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalCipOrigine> findBy(CatalCipOrigine entity, int start, int max,
			SingularAttribute<CatalCipOrigine, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CatalCipOrigine entity,
			SingularAttribute<CatalCipOrigine, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalCipOrigine> findByLike(CatalCipOrigine entity, int start,
			int max, SingularAttribute<CatalCipOrigine, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CatalCipOrigine entity,
			SingularAttribute<CatalCipOrigine, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalCipOrigine attach(CatalCipOrigine entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalCipOrigine findByIdentif(String identif) {
		return findById(identif);
	}
	
	public List<CatalCipOrigine> findByLangIso2(String langIso2){
		return repository.findByLangIso2(langIso2);
	}
}
