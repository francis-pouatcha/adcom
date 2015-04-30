package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adcshdwr.jpa.CdrDrctSales;
import org.adorsys.adcshdwr.jpa.CdrDrctSalesSearchInput;
import org.adorsys.adcshdwr.repo.CdrDrctSalesRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CdrDrctSalesEJB
{

	@Inject
	private CdrDrctSalesRepository repository;

	@Inject
	private SecurityUtil securityUtil;
	
	@Inject
	private EntityManager em;
	
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
		return entity;
	}

	public CdrDrctSales deleteById(String id)
	{
		CdrDrctSales entity = repository.findBy(id);
		if (entity != null)
		{
			repository.remove(entity);
		}
		return entity;
	}

	public CdrDrctSales update(CdrDrctSales entity)
	{
		entity = repository.save(attach(entity));

		return entity;
	}


	private static final String FIND_CUSTOM_QUERY = "SELECT e FROM CdrDrctSales AS e";
	private static final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM CdrDrctSales AS e";

	public List<CdrDrctSales> findCustom(CdrDrctSalesSearchInput searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<CdrDrctSales> query = em.createQuery(qBuilder.toString(), CdrDrctSales.class);
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
	}

	public Long countCustom(CdrDrctSalesSearchInput searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}
	

	private StringBuilder preprocessQuery(String findOrCount, CdrDrctSalesSearchInput searchInput){
		CdrDrctSales entity = searchInput.getEntity();

		String whereClause = " WHERE ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		
		if(searchInput.getFieldNames().contains("dsNbr") && StringUtils.isNotBlank(entity.getDsNbr())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.dsNbr=:dsNbr");
		}
		if(searchInput.getFieldNames().contains("cashier") && StringUtils.isNotBlank(entity.getCashier())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.cashier=:cashier");
		}
		if(searchInput.getFieldNames().contains("cdrNbr") && entity.getCdrNbr()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.cdrNbr=:cdrNbr");
		}
		if(searchInput.getFieldNames().contains("rcptNbr") && StringUtils.isNotBlank(entity.getRcptNbr())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.rcptNbr) LIKE(LOWER(:rcptNbr))");
		}
		return qBuilder;
	}

	
	public void setParameters(CdrDrctSalesSearchInput searchInput, Query query)
	{
		CdrDrctSales entity = searchInput.getEntity();

		if(searchInput.getFieldNames().contains("dsNbr") && StringUtils.isNotBlank(entity.getDsNbr())){
			query.setParameter("dsNbr", entity.getDsNbr());
		}
		if(searchInput.getFieldNames().contains("cashier") && StringUtils.isNotBlank(entity.getCashier())){
			query.setParameter("cashier", entity.getCashier());
		}
		if(searchInput.getFieldNames().contains("cdrNbr") && StringUtils.isNotBlank(entity.getCdrNbr())){
			query.setParameter("cdrNbr", entity.getCdrNbr());
		}
		if(searchInput.getFieldNames().contains("rcptNbr") && StringUtils.isNotBlank(entity.getRcptNbr())){
			query.setParameter("rcptNbr", "%"+entity.getRcptNbr()+"%");
		}
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
