package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.adorsys.adcshdwr.repo.CdrDsArtItemRepository;

@Stateless
public class CdrDsArtItemEJB
{

	@Inject
	private CdrDsArtItemRepository repository;
//
//	@Inject
//	private CdrDsArtItemEvtEJB cdrDsArtItemEvtEJB;
	
	public CdrDsArtItem create(CdrDsArtItem entity)
	{
//		CdrDsArtItemEvt cdrDsArtItemEvt = new CdrDsArtItemEvt();
		entity = repository.save(attach(entity));
//		entity.copyTo(cdrDsArtItemEvt);
//		cdrDsArtItemEvt.setId(entity.getId());
//		cdrDsArtItemEvt.setIdentif(entity.getIdentif());
//		cdrDsArtItemEvtEJB.create(cdrDsArtItemEvt);
		
		return entity;
	}

	public CdrDsArtItem deleteById(String id)
	{
		CdrDsArtItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
//			cdrDsArtItemEvtEJB.deleteById(id);
		}
		
		return entity;
	}

	public CdrDsArtItem update(CdrDsArtItem entity)
	{
//		CdrDsArtItemEvt cdrDsArtItemEvt = cdrDsArtItemEvtEJB.findById(entity.getId());
		entity = repository.save(attach(entity));
//		if(cdrDsArtItemEvt != null) {
//			entity.copyTo(cdrDsArtItemEvt);
//			cdrDsArtItemEvtEJB.update(cdrDsArtItemEvt);
//		}
		return entity;
	}

	public CdrDsArtItem findById(String id)
	{
		return repository.findBy(id);
	}

	public List<CdrDsArtItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CdrDsArtItem> findBy(CdrDsArtItem entity, int start, int max, SingularAttribute<CdrDsArtItem, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrDsArtItem entity, SingularAttribute<CdrDsArtItem, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CdrDsArtItem> findByLike(CdrDsArtItem entity, int start, int max, SingularAttribute<CdrDsArtItem, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrDsArtItem entity, SingularAttribute<CdrDsArtItem, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CdrDsArtItem attach(CdrDsArtItem entity)
	{
		if (entity == null)
			return null;

		return entity;
	}

	public Long countByDsNbr(String poNbr){
		return repository.findByDsNbr(poNbr).count();
	}
	public List<CdrDsArtItem> findByDsNbr(String dsNbr, int start, int max){
		return repository.findByDsNbr(dsNbr).firstResult(start).maxResults(max).getResultList();
	}

	public List<CdrDsArtItem> findByDsNbr(String dsNbr){
		return repository.findByDsNbr(dsNbr).getResultList();
	}
}
