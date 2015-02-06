package org.adorsys.adinvtry.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.jpa.InvInvtrySearchInput;
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
   

   public InvInvtry create(InvInvtry entity)
   {
	   String sequence = SequenceGenerator.getSequence(SequenceGenerator.INVENTORY_SEQUENCE_PREFIXE);
	   entity.setInvtryNbr(sequence);
	   String loginName = securityUtil.getCurrentLoginName();
	   Date currentDate = new Date();
	   if(entity.getAcsngDt() == null ) {
		entity.setAcsngDt(currentDate);
	   }
	   if(entity.getInvtryDt() == null) {
		   entity.setInvtryDt(currentDate);
	   }
	   entity.setAcsngUser(loginName);
	   
      return repository.save(attach(entity));
   }

   public InvInvtry deleteById(String id)
   {
      InvInvtry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtry update(InvInvtry entity)
   {
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
}
