package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.repo.SlsSalesOrderRepository;

@Stateless
public class SlsSalesOrderEJB
{

   @Inject
   private SlsSalesOrderRepository repository;

   public SlsSalesOrder create(SlsSalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSalesOrder deleteById(String id)
   {
      SlsSalesOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSalesOrder update(SlsSalesOrder entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSalesOrder findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSalesOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSalesOrder> findBy(SlsSalesOrder entity, int start, int max, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSalesOrder entity, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSalesOrder> findByLike(SlsSalesOrder entity, int start, int max, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSalesOrder entity, SingularAttribute<SlsSalesOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsSalesOrder attach(SlsSalesOrder entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
