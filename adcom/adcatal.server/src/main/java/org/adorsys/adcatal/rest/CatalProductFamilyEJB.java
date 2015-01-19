package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.repo.CatalProductFamilyRepository;

@Stateless
public class CatalProductFamilyEJB 
{

   @Inject
   private CatalProductFamilyRepository repository;

   public CatalProductFamily create(CatalProductFamily entity)
   {
      return repository.save(attach(entity));
   }

   public CatalProductFamily deleteById(String id)
   {
      CatalProductFamily entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalProductFamily update(CatalProductFamily entity)
   {
      return repository.save(attach(entity));
   }

   public CatalProductFamily findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalProductFamily> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalProductFamily> findBy(CatalProductFamily entity, int start, int max, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalProductFamily entity, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalProductFamily> findByLike(CatalProductFamily entity, int start, int max, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalProductFamily entity, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalProductFamily attach(CatalProductFamily entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalProductFamily findByIdentif(String identif, Date validOn){
	   List<CatalProductFamily> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
