package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.repo.CountryRepository;

@Stateless
public class CountryEJB
{

   @Inject
   private CountryRepository repository;

   public Country create(Country entity)
   {
      return repository.save(attach(entity));
   }

   public Country deleteById(String id)
   {
      Country entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Country update(Country entity)
   {
      return repository.save(attach(entity));
   }

   public Country findById(String id)
   {
      return repository.findBy(id);
   }

   public List<Country> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Country> findBy(Country entity, int start, int max, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Country entity, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Country> findByLike(Country entity, int start, int max, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Country entity, SingularAttribute<Country, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Country attach(Country entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public Country findByIdentif(String identif, Date validOn){
	   List<Country> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }

   public Country findByIso3(String iso3, Date validOn){
	   List<Country> resultList = repository.findByIso3(iso3, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   public List<Country> findActicfCountrys(Date validOn){
	   validOn = validOn == null ? new Date():validOn;
	   
	   return repository.findActicfCountrys(validOn);
   }
}
