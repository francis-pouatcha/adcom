package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.repo.CatalFamilyFeatMapingRepository;

@Stateless
public class CatalFamilyFeatMapingEJB {

	@Inject
	private CatalFamilyFeatMapingRepository repository;

	@Inject
	private CatalArt2ProductFamilyEJB art2ProductFamilyEJB;
	
	public CatalFamilyFeatMaping create(CatalFamilyFeatMaping entity) {
		CatalFamilyFeatMaping featMaping = repository.save(attach(entity));
		List<CatalArt2ProductFamily> list = art2ProductFamilyEJB.findByFamCodeAndLangIso2(featMaping.getPfIdentif(), featMaping.getLangIso2());
		for (CatalArt2ProductFamily a2p : list) {
			a2p.setFamilyName(entity.getFamilyName());
			a2p.setFamPath(entity.getFamPath());
			art2ProductFamilyEJB.update(a2p);
		}
		return featMaping;
	}

	public List<CatalFamilyFeatMaping> deleteByPfIdentif(String identif) {
		List<CatalFamilyFeatMaping> entities = repository
				.findByPfIdentif(identif);
		for (CatalFamilyFeatMaping entity : entities) {
			repository.remove(entity);
			List<CatalArt2ProductFamily> list = art2ProductFamilyEJB.findByFamCodeAndLangIso2(entity.getPfIdentif(), entity.getLangIso2());
			for (CatalArt2ProductFamily a2p : list) {
				art2ProductFamilyEJB.deleteById(a2p.getId());
			}
		}
		return entities;
	}

	public CatalFamilyFeatMaping update(CatalFamilyFeatMaping entity) {
		CatalFamilyFeatMaping featMaping = repository.save(attach(entity));
		List<CatalArt2ProductFamily> list = art2ProductFamilyEJB.findByFamCodeAndLangIso2(featMaping.getPfIdentif(), featMaping.getLangIso2());
		for (CatalArt2ProductFamily a2p : list) {
			a2p.setFamilyName(entity.getFamilyName());
			a2p.setFamPath(entity.getFamPath());
			art2ProductFamilyEJB.update(a2p);
		}
		return featMaping;
	}

	public CatalFamilyFeatMaping findById(String id) {
		return repository.findBy(id);
	}

	public List<CatalFamilyFeatMaping> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalFamilyFeatMaping> findBy(CatalFamilyFeatMaping entity,
			int start, int max,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CatalFamilyFeatMaping entity,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalFamilyFeatMaping> findByLike(CatalFamilyFeatMaping entity,
			int start, int max,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CatalFamilyFeatMaping entity,
			SingularAttribute<CatalFamilyFeatMaping, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalFamilyFeatMaping attach(CatalFamilyFeatMaping entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalFamilyFeatMaping findByIdentif(String identif) {
		List<CatalFamilyFeatMaping> resultList = repository
				.findByIdentif(identif).maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	public List<CatalFamilyFeatMaping> findByPfIdentif(String pfIdentif) {
		return repository.findByPfIdentif(pfIdentif);
	}

}
