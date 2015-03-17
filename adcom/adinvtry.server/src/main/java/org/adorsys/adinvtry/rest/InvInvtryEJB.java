package org.adorsys.adinvtry.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.Contract;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.adorsys.adinvtry.jpa.InvInvtrySearchInput;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.jpa.InvInvtryType;
import org.adorsys.adinvtry.repo.InvInvtryRepository;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class InvInvtryEJB
{

	@Inject
	private InvInvtryRepository repository;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private InvInvtryEvtDataEJB invtryEvtDataEJB;
	
	@Inject
	private InvInvtryItemEJB itemEJB;
	
	public InvInvtry create(InvInvtry entity)
	{
		String sequence = SequenceGenerator.getSequence(SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE);
		entity.setInvtryNbr(sequence);

		if(entity.getInvtryStatus()==null)
			entity.setInvtryStatus(InvInvtryStatus.ONGOING);
		
		if(entity.getInvInvtryType()==null)
			entity.setInvInvtryType(InvInvtryType.FREE_INV);

		InvInvtry save = repository.save(attach(entity));
		InvInvtryEvtData invtryEvtData = new InvInvtryEvtData();
		save.copyTo(invtryEvtData);
		invtryEvtData.setId(save.getId());
		invtryEvtData.setIdentif(save.getIdentif());
		invtryEvtDataEJB.create(invtryEvtData);
		return save;
	}

	public InvInvtry deleteById(String id)
	{
		InvInvtry entity = repository.findBy(id);
		if (entity != null)
		{
			removeIntryItems(entity);
			invtryEvtDataEJB.deleteById(id);
			repository.remove(entity);
		}
		return entity;
	}

	/**
	 * removeIntryItems.
	 *
	 * @param entity
	 */
	private void removeIntryItems(InvInvtry entity) {
		Contract.checkIllegalArgumentException(entity);
		itemEJB.removeByInvtryNbr(entity.getInvtryNbr());
	}

	public InvInvtry update(InvInvtry entity)
	{
		InvInvtryEvtData invtryEvtData = invtryEvtDataEJB.findByIdentif(entity.getIdentif());
		if(invtryEvtData != null) {
			entity.copyTo(invtryEvtData);
			invtryEvtDataEJB.update(invtryEvtData);
			
		}
		return repository.save(attach(entity));
	}

	public InvInvtry findById(String id)
	{
		return repository.findBy(id);
	}

	public List<InvInvtry> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<InvInvtry> findBy(InvInvtry entity, int start, int max, SingularAttribute<InvInvtry, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtry entity, SingularAttribute<InvInvtry, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<InvInvtry> findByLike(InvInvtry entity, int start, int max, SingularAttribute<InvInvtry, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtry entity, SingularAttribute<InvInvtry, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	public InvInvtry findByIdentif(String identif) {
		QueryResult<InvInvtry> queryResult = repository.findByIdentif(identif);
		List<InvInvtry> results = queryResult.maxResults(1).getResultList();
		InvInvtry invInvtry= null;
		if(!results.isEmpty()) {
			invInvtry = results.iterator().next();
		}
		return invInvtry;
	}

	public List<InvInvtry> findByDateBtw(Date from, Date to) {
		List<InvInvtry> invtryDtBtw = repository.findByInvtryDtBtw(from, to).getResultList();
		return invtryDtBtw;
	}

	public Long countByDateBtw(Date from, Date to) {
		Long count = repository.countByInvtryDtBtw(from, to);
		return count;
	}

	public List<InvInvtry> findInvInvtrys(InvInvtrySearchInput searchInput,Date now) {
		Date from = searchInput.getFrom();
		Date to = searchInput.getTo();
		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(from == null) {
			from = DateUtils.addYears(now, -1);
		}
		if(to == null) {
			to = new Date();
		}
		QueryResult<InvInvtry> queryResult = repository.findByInvtryDtBtw(from, to);
		if(start < 0 ) {
			start = 0;
		}
		queryResult.firstResult(start);
		if(max > 0) {
			queryResult.maxResults(max);
		}
		return queryResult.getResultList();
	}

	public Long countInvInvtrys(InvInvtrySearchInput searchInput,Date now) {
		Date from = searchInput.getFrom();
		Date to = searchInput.getTo();

		if(from == null) {
			from = DateUtils.addYears(now, -1);
		}
		if(to == null) {
			to = new Date();
		}
		Long count = countByDateBtw(from, to);
		return count;
	}
	
	private InvInvtry attach(InvInvtry entity)
	{
		if (entity == null)
			return null;

		return entity;
	}

	public List<InvInvtry> findOpenInvtrys() {
		return repository.findOpenInvtrys().getResultList();
	}
}
