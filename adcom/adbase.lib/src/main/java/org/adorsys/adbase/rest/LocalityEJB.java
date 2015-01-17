package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.jpa.LocalitySearchInput;
import org.adorsys.adbase.jpa.LocalitySearchResult;
import org.adorsys.adbase.jpa.SecTerminal;
import org.adorsys.adbase.repo.LocalityRepository;

@Stateless
public class LocalityEJB
{

   @Inject
   private LocalityRepository repository;

   public Locality create(Locality entity)
   {
      return repository.save(attach(entity));
   }

   public Locality deleteById(String id)
   {
      Locality entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public Locality update(Locality entity)
   {
      return repository.save(attach(entity));
   }

   public Locality findById(String id)
   {
      return repository.findBy(id);
   }

   public List<Locality> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<Locality> findBy(Locality entity, int start, int max, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(Locality entity, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<Locality> findByLike(Locality entity, int start, int max, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(Locality entity, SingularAttribute<Locality, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private Locality attach(Locality entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public Locality findByIdentif(String identif, Date validOn){
	   List<Locality> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   public LocalitySearchResult  findAllActiveLocality(LocalitySearchInput searchInput){
	   Date date = new Date();
	   	   Long count = repository.countActiveLocality(date);
	   List<Locality> resultList = repository.findActiveLocality(date).firstResult(searchInput.getStart()).maxResults(searchInput.getMax()).getResultList();
	   LocalitySearchResult searchResult = new LocalitySearchResult();
	   searchResult.setCount(count);
	   searchResult.setResultList(resultList);
	   searchResult.setSearchInput(searchInput);
	   return searchResult ;
	   
   }
}
