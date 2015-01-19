package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.repo.CatalPicMappingRepository;

@Stateless
public class CatalPicMappingEJB 
{

   @Inject
   private CatalPicMappingRepository repository;

   public CatalPicMapping create(CatalPicMapping entity)
   {
      return repository.save(attach(entity));
   }

   public CatalPicMapping deleteById(String id)
   {
      CatalPicMapping entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalPicMapping update(CatalPicMapping entity)
   {
      return repository.save(attach(entity));
   }

   public CatalPicMapping findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalPicMapping> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalPicMapping> findBy(CatalPicMapping entity, int start, int max, SingularAttribute<CatalPicMapping, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalPicMapping entity, SingularAttribute<CatalPicMapping, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalPicMapping> findByLike(CatalPicMapping entity, int start, int max, SingularAttribute<CatalPicMapping, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalPicMapping entity, SingularAttribute<CatalPicMapping, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalPicMapping attach(CatalPicMapping entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalPicMapping findByIdentif(String identif, Date validOn){
		List<CatalPicMapping> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
