package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrDsArtItemEvt;
import org.adorsys.adcshdwr.repo.CdrDsArtItemEvtRepository;

@Stateless
public class CdrDsArtItemEvtEJB
{

	@Inject
	private CdrDsArtItemEvtRepository repository;

	public CdrDsArtItemEvt create(CdrDsArtItemEvt entity)
	{
		return repository.save(attach(entity));
	}

	public CdrDsArtItemEvt deleteById(String id)
	{
		CdrDsArtItemEvt entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public CdrDsArtItemEvt update(CdrDsArtItemEvt entity)
	{
		return repository.save(attach(entity));
	}

	public CdrDsArtItemEvt findById(String id)
	{
		return repository.findBy(id);
	}

	public List<CdrDsArtItemEvt> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CdrDsArtItemEvt> findBy(CdrDsArtItemEvt entity, int start, int max, SingularAttribute<CdrDsArtItemEvt, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrDsArtItemEvt entity, SingularAttribute<CdrDsArtItemEvt, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CdrDsArtItemEvt> findByLike(CdrDsArtItemEvt entity, int start, int max, SingularAttribute<CdrDsArtItemEvt, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrDsArtItemEvt entity, SingularAttribute<CdrDsArtItemEvt, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CdrDsArtItemEvt attach(CdrDsArtItemEvt entity)
	{
		if (entity == null)
			return null;

		return entity;
	}

	public Long countByDsNbr(String poNbr){
		return repository.findByDsNbr(poNbr).count();
	}
	public List<CdrDsArtItemEvt> findByDsNbr(String dsNbr, int start, int max){
		return repository.findByDsNbr(dsNbr).firstResult(start).maxResults(max).getResultList();
	}
}
