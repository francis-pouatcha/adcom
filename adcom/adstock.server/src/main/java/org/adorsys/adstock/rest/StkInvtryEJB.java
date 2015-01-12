package org.adorsys.adstock.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkInvtry;
import org.adorsys.adstock.repo.StkInvtryRepository;

@Stateless
public class StkInvtryEJB
{

   @Inject
   private StkInvtryRepository repository;

   public StkInvtry create(StkInvtry entity)
   {
      return repository.save(attach(entity));
   }

   public StkInvtry deleteById(String id)
   {
      StkInvtry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkInvtry update(StkInvtry entity)
   {
      return repository.save(attach(entity));
   }

   public StkInvtry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkInvtry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkInvtry> findBy(StkInvtry entity, int start, int max, SingularAttribute<StkInvtry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkInvtry entity, SingularAttribute<StkInvtry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkInvtry> findByLike(StkInvtry entity, int start, int max, SingularAttribute<StkInvtry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkInvtry entity, SingularAttribute<StkInvtry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkInvtry attach(StkInvtry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public StkInvtry findByIdentif(String identif, Date validOn){
	   List<StkInvtry> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
