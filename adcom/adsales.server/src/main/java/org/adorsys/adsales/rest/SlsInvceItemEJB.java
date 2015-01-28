package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.repo.SlsInvceItemRepository;

@Stateless
public class SlsInvceItemEJB
{

   @Inject
   private SlsInvceItemRepository repository;

   public SlsInvceItem create(SlsInvceItem entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceItem deleteById(String id)
   {
      SlsInvceItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvceItem update(SlsInvceItem entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvceItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvceItem> findBy(SlsInvceItem entity, int start, int max, SingularAttribute<SlsInvceItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvceItem entity, SingularAttribute<SlsInvceItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvceItem> findByLike(SlsInvceItem entity, int start, int max, SingularAttribute<SlsInvceItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsInvceItem entity, SingularAttribute<SlsInvceItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsInvceItem attach(SlsInvceItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
