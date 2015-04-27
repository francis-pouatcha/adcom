package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.repo.CdrDrctSalesRepository;

@Stateless
public class CdrDrctSalesLookup
{

	@Inject
	private CdrDrctSalesRepository repository;

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
}
