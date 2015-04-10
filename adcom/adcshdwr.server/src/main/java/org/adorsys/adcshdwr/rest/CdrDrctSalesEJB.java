package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.repo.CdrDrctSalesRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrDrctSalesEJB
{

	@Inject
	private CdrDrctSalesRepository repository;

	@Inject
	private CdrDrctSalesEvtEJB cdrDrctSalesEvtEJB;

	@Inject
	private SecurityUtil securityUtil;
	
	public CdrDrctSales create(CdrDrctSales entity)
	{
		if (StringUtils.isBlank(entity.getDsNbr())) {
			entity.setDsNbr(SequenceGenerator
					.getSequence(SequenceGenerator.DIRECT_SALES_SEQUENCE_PREFIXE));
		}
		entity.setCashier(securityUtil.getCurrentLoginName());
		entity.setId(entity.getDsNbr());
		entity.setIdentif(entity.getDsNbr());
		entity = repository.save(attach(entity));
		repository.flush();
		CdrDrctSalesEvt cdrDrctSalesEvt = new CdrDrctSalesEvt();
		entity.copyTo(cdrDrctSalesEvt);
		cdrDrctSalesEvt.setId(entity.getId());
		cdrDrctSalesEvt.setIdentif(entity.getIdentif());
		cdrDrctSalesEvtEJB.create(cdrDrctSalesEvt);
		return entity;
	}

	public CdrDrctSales deleteById(String id)
	{
		CdrDrctSales entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
			cdrDrctSalesEvtEJB.deleteById(id);
		}
		return entity;
	}

	public CdrDrctSales update(CdrDrctSales entity)
	{
		CdrDrctSalesEvt cdrDrctSalesEvt = cdrDrctSalesEvtEJB.findById(entity.getId());
		entity = repository.save(attach(entity));
		if (cdrDrctSalesEvt != null) {
			entity.copyTo(cdrDrctSalesEvt);
			cdrDrctSalesEvtEJB.update(cdrDrctSalesEvt);
		}
		return entity;
	}

	public CdrDrctSales findById(String id)
	{
		return repository.findBy(id);
	}

	public List<CdrDrctSales> listAll(int start, int max)
	{
		return repository.findAll(start, max);
	}

	public Long count()
	{
		return repository.count();
	}

	public List<CdrDrctSales> findBy(CdrDrctSales entity, int start, int max, SingularAttribute<CdrDrctSales, ?>[] attributes)
	{
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrDrctSales entity, SingularAttribute<CdrDrctSales, ?>[] attributes)
	{
		return repository.count(entity, attributes);
	}

	public List<CdrDrctSales> findByLike(CdrDrctSales entity, int start, int max, SingularAttribute<CdrDrctSales, ?>[] attributes)
	{
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrDrctSales entity, SingularAttribute<CdrDrctSales, ?>[] attributes)
	{
		return repository.countLike(entity, attributes);
	}

	private CdrDrctSales attach(CdrDrctSales entity)
	{
		if (entity == null)
			return null;

		return entity;
	}
}
