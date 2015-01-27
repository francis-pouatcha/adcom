package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

@Stateless
public class StkLotStockQtyEJB
{

   @Inject
   private StkLotStockQtyRepository repository;

   public StkLotStockQty create(StkLotStockQty entity)
   {
      return repository.save(attach(entity));
   }

   public StkLotStockQty deleteById(String id)
   {
      StkLotStockQty entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkLotStockQty update(StkLotStockQty entity)
   {
      return repository.save(attach(entity));
   }

   public StkLotStockQty findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkLotStockQty> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkLotStockQty> findBy(StkLotStockQty entity, int start, int max, SingularAttribute<StkLotStockQty, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkLotStockQty entity, SingularAttribute<StkLotStockQty, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkLotStockQty> findByLike(StkLotStockQty entity, int start, int max, SingularAttribute<StkLotStockQty, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkLotStockQty entity, SingularAttribute<StkLotStockQty, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkLotStockQty attach(StkLotStockQty entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
