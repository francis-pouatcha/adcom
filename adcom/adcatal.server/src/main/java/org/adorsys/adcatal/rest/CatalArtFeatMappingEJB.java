package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.repo.CatalArtFeatMappingRepository;

@Stateless
public class CatalArtFeatMappingEJB 
{

   @Inject
   private CatalArtFeatMappingRepository repository;

   public CatalArtFeatMapping create(CatalArtFeatMapping entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtFeatMapping deleteById(String id)
   {
      CatalArtFeatMapping entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalArtFeatMapping update(CatalArtFeatMapping entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtFeatMapping findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalArtFeatMapping> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArtFeatMapping> findBy(CatalArtFeatMapping entity, int start, int max, SingularAttribute<CatalArtFeatMapping, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalArtFeatMapping entity, SingularAttribute<CatalArtFeatMapping, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArtFeatMapping> findByLike(CatalArtFeatMapping entity, int start, int max, SingularAttribute<CatalArtFeatMapping, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalArtFeatMapping entity, SingularAttribute<CatalArtFeatMapping, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArtFeatMapping attach(CatalArtFeatMapping entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   public CatalArtFeatMapping findByIdentif(String identif, Date validOn){
	   List<CatalArtFeatMapping> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
