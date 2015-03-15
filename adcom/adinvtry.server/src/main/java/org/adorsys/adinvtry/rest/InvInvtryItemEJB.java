package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemEJB
{

	@Inject
	private InvInvtryItemRepository repository;

	@Inject
	private InvInvtryItemEvtDataEJB itemEvtDataEJB;
	
	public InvInvtryItem create(InvInvtryItem entity)
	{
		InvInvtryItem invtryItem = repository.save(attach(entity));
		InvInvtryItemEvtData itemEvtData = new InvInvtryItemEvtData();
		invtryItem.copyTo(itemEvtData);
		itemEvtData.setIdentif(invtryItem.getIdentif());
		itemEvtDataEJB.create(itemEvtData);
		return invtryItem;
	}

	public InvInvtryItem deleteById(String id)
	{
		InvInvtryItem entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
			itemEvtDataEJB.deleteById(id);
		}
		return entity;
	}

	public InvInvtryItem update(InvInvtryItem entity)
	{
		InvInvtryItem invtryItem = repository.save(attach(entity));
		InvInvtryItemEvtData itemEvtData = itemEvtDataEJB.findById(invtryItem.getId());
		invtryItem.copyTo(itemEvtData);
		itemEvtDataEJB.update(itemEvtData);
		return invtryItem;
	}

	public InvInvtryItem findById(String id)
	{
		return repository.findBy(id);
	}

	public List<InvInvtryItem> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<InvInvtryItem> findBy(InvInvtryItem entity, int start, int max, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtryItem entity, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<InvInvtryItem> findByLike(InvInvtryItem entity, int start, int max, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtryItem entity, SingularAttribute<InvInvtryItem, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private InvInvtryItem attach(InvInvtryItem entity)
	{
		if (entity == null)
			return null;

		return entity;
	}
	public List<InvInvtryItem> findByInvtryNbr(String invtryNbr, int start, int max) {
		return repository.findByInvtryNbr(invtryNbr).firstResult(start).maxResults(max).getResultList();
	}

	public Long countByInvtryNbr(String invtryNbr) {
		return repository.countByInvtryNbr(invtryNbr);

	}
	
	public void removeByInvtryNbr(String invtryNbr) {
		repository.removeByInvtryNbr(invtryNbr);
	}
	
	public List<InvInvtryItem> findByInvtryNbrSorted(String invtryNbr, int start, int max) {
		return repository.findByInvtryNbr(invtryNbr).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
	}

	public List<InvInvtryItem> findByInvtryNbrAndSectionSorted(
			String invtryNbr, String section, int start, int max) {
		return repository.findByInvtryNbrAndSection(invtryNbr, section).orderAsc("artName").firstResult(start).maxResults(max).getResultList();
	}

	public Long countByInvtryNbrAndSection(String invtryNbr, String section) {
		return repository.countByInvtryNbrAndSection(invtryNbr, section);
	}

	
}
