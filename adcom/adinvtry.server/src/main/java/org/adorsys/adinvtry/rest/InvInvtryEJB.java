package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.Contract;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.adorsys.adinvtry.jpa.InvInvtrySearchInput;
import org.adorsys.adinvtry.jpa.InvInvtryStatus;
import org.adorsys.adinvtry.jpa.InvInvtryType;
import org.adorsys.adinvtry.repo.InvInvtryRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class InvInvtryEJB
{

	@Inject
	private InvInvtryRepository repository;
//
//	@Inject
//	private SecurityUtil securityUtil;
	
	@Inject
	private InvInvtryEvtDataEJB invtryEvtDataEJB;
	
	@Inject
	private InvInvtryItemEJB itemEJB;

	@Inject
	private EntityManager em;
	
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

	private static final String FIND_CUSTOM_QUERY = "SELECT e FROM InvInvtry AS e";
	private static final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM InvInvtry AS e";
	
	private StringBuilder preprocessQuery(String findOrCount, InvInvtrySearchInput searchInput){
		InvInvtry entity = searchInput.getEntity();

		String whereClause = " WHERE ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;
		
		if(searchInput.getFieldNames().contains("invtryNbr") && StringUtils.isNotBlank(entity.getInvtryNbr())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.invtryNbr=:invtryNbr");
		}
		if(searchInput.getFieldNames().contains("acsngUser") && StringUtils.isNotBlank(entity.getAcsngUser())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.acsngUser=:acsngUser");
		}
		if(searchInput.getFieldNames().contains("invtryStatus") && entity.getInvtryStatus()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.invtryStatus=:invtryStatus");
		}
		if(searchInput.getFieldNames().contains("invtryGroup") && StringUtils.isNotBlank(entity.getInvtryGroup())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.invtryGroup) LIKE(LOWER(:invtryGroup))");
		}
		if(searchInput.getFieldNames().contains("invInvtryType") && entity.getInvInvtryType()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.invInvtryType=:invInvtryType");
		}
		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.section) LIKE(LOWER(:section))");
		}
		if(searchInput.getFieldNames().contains("rangeStart") && StringUtils.isNotBlank(entity.getRangeStart())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.rangeStart)<=LOWER(:rangeStart)");
		}
		if(searchInput.getFieldNames().contains("rangeEnd") && StringUtils.isNotBlank(entity.getRangeEnd())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.rangeEnd)<=LOWER(:rangeEnd)");
		}
		if(searchInput.getFieldNames().contains("descptn") && StringUtils.isNotBlank(entity.getDescptn())){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("LOWER(e.descptn) LIKE(LOWER(:descptn))");
		}
		if(searchInput.getInvtryDtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.invtryDt >= :invtryDtFrom");
		}
		if(searchInput.getInvtryDtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.invtryDt <= :invtryDtTo");
		}
		if(searchInput.getAcsngDtFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.acsngDt >= :acsngDtFrom");
		}
		if(searchInput.getAcsngDtTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.acsngDt <= :acsngDtTo");
		}
		if(searchInput.getGapPurchAmtHTFrom()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.gapPurchAmtHT >= :gapPurchAmtHTFrom");
		}
		if(searchInput.getGapPurchAmtHTTo()!=null){
			if(!whereSet){
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.gapPurchAmtHT <= :gapPurchAmtHTTo");
		}
		return qBuilder;
	}
	
	public void setParameters(InvInvtrySearchInput searchInput, Query query)
	{
		InvInvtry entity = searchInput.getEntity();

		if(searchInput.getFieldNames().contains("invtryNbr") && StringUtils.isNotBlank(entity.getInvtryNbr())){
			query.setParameter("invtryNbr", entity.getInvtryNbr());
		}
		if(searchInput.getFieldNames().contains("acsngUser") && StringUtils.isNotBlank(entity.getAcsngUser())){
			query.setParameter("acsngUser", entity.getAcsngUser());
		}
		if(searchInput.getFieldNames().contains("invtryStatus") && entity.getInvtryStatus()!=null){
			query.setParameter("invtryStatus", entity.getInvtryStatus());
		}
		if(searchInput.getFieldNames().contains("invtryGroup") && StringUtils.isNotBlank(entity.getInvtryGroup())){
			query.setParameter("invtryGroup", "%"+entity.getInvtryGroup()+"%");
		}
		if(searchInput.getFieldNames().contains("invInvtryType") && entity.getInvInvtryType()!=null){
			query.setParameter("invInvtryType", entity.getInvInvtryType());
		}
		if(searchInput.getFieldNames().contains("section") && StringUtils.isNotBlank(entity.getSection())){
			query.setParameter("section", "%"+entity.getSection()+"%");
		}
		if(searchInput.getFieldNames().contains("rangeStart") && StringUtils.isNotBlank(entity.getRangeStart())){
			query.setParameter("rangeStart", entity.getRangeStart());
		}
		if(searchInput.getFieldNames().contains("rangeEnd") && StringUtils.isNotBlank(entity.getRangeEnd())){
			query.setParameter("rangeEnd", entity.getRangeEnd());
		}
		if(searchInput.getFieldNames().contains("descptn") && StringUtils.isNotBlank(entity.getDescptn())){
			query.setParameter("descptn", "%"+entity.getDescptn()+"%");
		}
		if(searchInput.getInvtryDtFrom()!=null){
			query.setParameter("invtryDtFrom", searchInput.getInvtryDtFrom());
		}
		if(searchInput.getInvtryDtTo()!=null){
			query.setParameter("invtryDtTo", searchInput.getInvtryDtTo());
		}
		if(searchInput.getAcsngDtFrom()!=null){
			query.setParameter("acsngDtFrom", searchInput.getAcsngDtFrom());
		}
		if(searchInput.getAcsngDtTo()!=null){
			query.setParameter("acsngDtTo", searchInput.getAcsngDtTo());
		}
		if(searchInput.getGapPurchAmtHTFrom()!=null){
			query.setParameter("gapPurchAmtHTFrom", searchInput.getGapPurchAmtHTFrom());
		}
		if(searchInput.getGapPurchAmtHTTo()!=null){
			query.setParameter("gapPurchAmtHTTo", searchInput.getGapPurchAmtHTTo());
		}
	}	
	
	public List<InvInvtry> findCustom(InvInvtrySearchInput searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<InvInvtry> query = em.createQuery(qBuilder.toString(), InvInvtry.class);
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if(start < 0)  start = 0;
		query.setFirstResult(start);
		if(max >= 1) 
			query.setMaxResults(max);
		
		return query.getResultList();
	}

	public Long countCustom(InvInvtrySearchInput searchInput)
	{
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY, searchInput);
		TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
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

//	public List<InvInvtry> findByDateBtw(Date from, Date to) {
//		List<InvInvtry> invtryDtBtw = repository.findByInvtryDtBtw(from, to).getResultList();
//		return invtryDtBtw;
//	}
//
//	public Long countByDateBtw(Date from, Date to) {
//		Long count = repository.countByInvtryDtBtw(from, to);
//		return count;
//	}
//
//	public List<InvInvtry> findInvInvtrys(InvInvtrySearchInput searchInput,Date now) {
//		Date from = searchInput.getFrom();
//		Date to = searchInput.getTo();
//		int start = searchInput.getStart();
//		int max = searchInput.getMax();
//
//		if(from == null) {
//			from = DateUtils.addYears(now, -1);
//		}
//		if(to == null) {
//			to = new Date();
//		}
//		QueryResult<InvInvtry> queryResult = repository.findByInvtryDtBtw(from, to);
//		if(start < 0 ) {
//			start = 0;
//		}
//		queryResult.firstResult(start);
//		if(max > 0) {
//			queryResult.maxResults(max);
//		}
//		return queryResult.getResultList();
//	}
//
//	public Long countInvInvtrys(InvInvtrySearchInput searchInput,Date now) {
//		Date from = searchInput.getFrom();
//		Date to = searchInput.getTo();
//
//		if(from == null) {
//			from = DateUtils.addYears(now, -1);
//		}
//		if(to == null) {
//			to = new Date();
//		}
//		Long count = countByDateBtw(from, to);
//		return count;
//	}
	
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
