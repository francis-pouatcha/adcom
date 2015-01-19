package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.repo.CatalArtEquivalenceRepository;

@Stateless
public class CatalArtEquivalenceEJB 
{

   @Inject
   private CatalArtEquivalenceRepository repository;

   public CatalArtEquivalence create(CatalArtEquivalence entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtEquivalence deleteById(String id)
   {
      CatalArtEquivalence entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalArtEquivalence update(CatalArtEquivalence entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtEquivalence findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalArtEquivalence> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArtEquivalence> findBy(CatalArtEquivalence entity, int start, int max, SingularAttribute<CatalArtEquivalence, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalArtEquivalence entity, SingularAttribute<CatalArtEquivalence, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArtEquivalence> findByLike(CatalArtEquivalence entity, int start, int max, SingularAttribute<CatalArtEquivalence, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalArtEquivalence entity, SingularAttribute<CatalArtEquivalence, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArtEquivalence attach(CatalArtEquivalence entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalArtEquivalence findByIdentif(String identif, Date validOn){
	   List<CatalArtEquivalence> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
