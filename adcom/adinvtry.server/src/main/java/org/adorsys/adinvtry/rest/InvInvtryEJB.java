package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.repo.InvInvtryRepository;

@Stateless
public class InvInvtryEJB
{

   @Inject
   private InvInvtryRepository repository;

   public InvInvtry create(InvInvtry entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtry deleteById(String id)
   {
      InvInvtry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtry update(InvInvtry entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<InvInvtry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InvInvtry> findBy(InvInvtry entity, int start, int max, SingularAttribute<InvInvtry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(InvInvtry entity, SingularAttribute<InvInvtry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<InvInvtry> findByLike(InvInvtry entity, int start, int max, SingularAttribute<InvInvtry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(InvInvtry entity, SingularAttribute<InvInvtry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private InvInvtry attach(InvInvtry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
