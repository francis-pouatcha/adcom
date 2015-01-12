package org.adorsys.adstock.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkInvtryItem;
import org.adorsys.adstock.repo.StkInvtryItemRepository;

@Stateless
public class StkInvtryItemEJB
{

   @Inject
   private StkInvtryItemRepository repository;

   public StkInvtryItem create(StkInvtryItem entity)
   {
      return repository.save(attach(entity));
   }

   public StkInvtryItem deleteById(String id)
   {
      StkInvtryItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkInvtryItem update(StkInvtryItem entity)
   {
      return repository.save(attach(entity));
   }

   public StkInvtryItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkInvtryItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkInvtryItem> findBy(StkInvtryItem entity, int start, int max, SingularAttribute<StkInvtryItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkInvtryItem entity, SingularAttribute<StkInvtryItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkInvtryItem> findByLike(StkInvtryItem entity, int start, int max, SingularAttribute<StkInvtryItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkInvtryItem entity, SingularAttribute<StkInvtryItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkInvtryItem attach(StkInvtryItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public StkInvtryItem findByIdentif(String identif, Date validOn){
	   List<StkInvtryItem> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
