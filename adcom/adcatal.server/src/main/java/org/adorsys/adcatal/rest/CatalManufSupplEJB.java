package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalManufSuppl;
import org.adorsys.adcatal.repo.CatalManufSupplRepository;

@Stateless
public class CatalManufSupplEJB
{

   @Inject
   private CatalManufSupplRepository repository;

   public CatalManufSuppl create(CatalManufSuppl entity)
   {
      return repository.save(attach(entity));
   }

   public CatalManufSuppl deleteById(String id)
   {
      CatalManufSuppl entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalManufSuppl update(CatalManufSuppl entity)
   {
      return repository.save(attach(entity));
   }

   public CatalManufSuppl findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalManufSuppl> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalManufSuppl> findBy(CatalManufSuppl entity, int start, int max, SingularAttribute<CatalManufSuppl, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalManufSuppl entity, SingularAttribute<CatalManufSuppl, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalManufSuppl> findByLike(CatalManufSuppl entity, int start, int max, SingularAttribute<CatalManufSuppl, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalManufSuppl entity, SingularAttribute<CatalManufSuppl, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalManufSuppl attach(CatalManufSuppl entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalManufSuppl findByIdentif(String identif, Date validOn){
	   List<CatalManufSuppl> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
