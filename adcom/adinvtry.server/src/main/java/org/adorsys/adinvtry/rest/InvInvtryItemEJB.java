package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

@Stateless
public class InvInvtryItemEJB
{

   @Inject
   private InvInvtryItemRepository repository;

   public InvInvtryItem create(InvInvtryItem entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryItem deleteById(String id)
   {
      InvInvtryItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtryItem update(InvInvtryItem entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<InvInvtryItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InvInvtryItem> findBy(InvInvtryItem entity, int start, int max, SingularAttribute<InvInvtryItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(InvInvtryItem entity, SingularAttribute<InvInvtryItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<InvInvtryItem> findByLike(InvInvtryItem entity, int start, int max, SingularAttribute<InvInvtryItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(InvInvtryItem entity, SingularAttribute<InvInvtryItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private InvInvtryItem attach(InvInvtryItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
