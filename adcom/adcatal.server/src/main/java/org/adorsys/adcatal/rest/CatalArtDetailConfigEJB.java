package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.repo.CatalArtDetailConfigRepository;

@Stateless
public class CatalArtDetailConfigEJB
{

   @Inject
   private CatalArtDetailConfigRepository repository;

   public CatalArtDetailConfig create(CatalArtDetailConfig entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtDetailConfig deleteById(String id)
   {
      CatalArtDetailConfig entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalArtDetailConfig update(CatalArtDetailConfig entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtDetailConfig findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalArtDetailConfig> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArtDetailConfig> findBy(CatalArtDetailConfig entity, int start, int max, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalArtDetailConfig entity, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArtDetailConfig> findByLike(CatalArtDetailConfig entity, int start, int max, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalArtDetailConfig entity, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArtDetailConfig attach(CatalArtDetailConfig entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   public CatalArtDetailConfig findByIdentif(String identif, Date validOn){
	   List<CatalArtDetailConfig> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
