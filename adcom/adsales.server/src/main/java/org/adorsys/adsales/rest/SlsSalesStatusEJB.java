package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsSalesStatus;
import org.adorsys.adsales.repo.SlsSalesStatusRepository;

@Stateless
public class SlsSalesStatusEJB
{

   @Inject
   private SlsSalesStatusRepository repository;

   public SlsSalesStatus create(SlsSalesStatus entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSalesStatus deleteById(String id)
   {
      SlsSalesStatus entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSalesStatus update(SlsSalesStatus entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSalesStatus findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSalesStatus> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSalesStatus> findBy(SlsSalesStatus entity, int start, int max, SingularAttribute<SlsSalesStatus, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSalesStatus entity, SingularAttribute<SlsSalesStatus, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSalesStatus> findByLike(SlsSalesStatus entity, int start, int max, SingularAttribute<SlsSalesStatus, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSalesStatus entity, SingularAttribute<SlsSalesStatus, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsSalesStatus attach(SlsSalesStatus entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
