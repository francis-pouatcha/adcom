package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.repo.PricingCurrRateRepository;

@Stateless
public class PricingCurrRateEJB 
{

   @Inject
   private PricingCurrRateRepository repository;

   public PricingCurrRate create(PricingCurrRate entity)
   {
      return repository.save(attach(entity));
   }

   public PricingCurrRate deleteById(String id)
   {
      PricingCurrRate entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PricingCurrRate update(PricingCurrRate entity)
   {
      return repository.save(attach(entity));
   }

   public PricingCurrRate findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PricingCurrRate> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PricingCurrRate> findBy(PricingCurrRate entity, int start, int max, SingularAttribute<PricingCurrRate, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PricingCurrRate entity, SingularAttribute<PricingCurrRate, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PricingCurrRate> findByLike(PricingCurrRate entity, int start, int max, SingularAttribute<PricingCurrRate, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PricingCurrRate entity, SingularAttribute<PricingCurrRate, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PricingCurrRate attach(PricingCurrRate entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public PricingCurrRate findByIdentif(String identif, Date validOn){
	   List<PricingCurrRate> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
