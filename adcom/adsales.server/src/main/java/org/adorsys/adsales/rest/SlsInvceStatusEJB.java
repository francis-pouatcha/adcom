package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsInvceStatus;
import org.adorsys.adsales.repo.SlsInvceStatusRepository;

@Stateless
public class SlsInvceStatusEJB
{

   @Inject
   private SlsInvceStatusRepository repository;

   public SlsInvceStatus create(SlsInvceStatus entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceStatus deleteById(String id)
   {
      SlsInvceStatus entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvceStatus update(SlsInvceStatus entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvceStatus findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvceStatus> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvceStatus> findBy(SlsInvceStatus entity, int start, int max, SingularAttribute<SlsInvceStatus, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvceStatus entity, SingularAttribute<SlsInvceStatus, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvceStatus> findByLike(SlsInvceStatus entity, int start, int max, SingularAttribute<SlsInvceStatus, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsInvceStatus entity, SingularAttribute<SlsInvceStatus, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsInvceStatus attach(SlsInvceStatus entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
