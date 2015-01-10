package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.repo.CatalArtManufSuppRepository;

@Stateless
public class CatalArtManufSuppEJB
{

   @Inject
   private CatalArtManufSuppRepository repository;

   public CatalArtManufSupp create(CatalArtManufSupp entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtManufSupp deleteById(String id)
   {
      CatalArtManufSupp entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalArtManufSupp update(CatalArtManufSupp entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtManufSupp findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalArtManufSupp> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArtManufSupp> findBy(CatalArtManufSupp entity, int start, int max, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalArtManufSupp entity, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArtManufSupp> findByLike(CatalArtManufSupp entity, int start, int max, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalArtManufSupp entity, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArtManufSupp attach(CatalArtManufSupp entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalArtManufSupp findByIdentif(String identif, Date validOn){
	   List<CatalArtManufSupp> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
