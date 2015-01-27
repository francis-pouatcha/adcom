package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.repo.StkArtStockQtyRepository;

@Stateless
public class StkArtStockQtyEJB
{

   @Inject
   private StkArtStockQtyRepository repository;

   public StkArtStockQty create(StkArtStockQty entity)
   {
      return repository.save(attach(entity));
   }

   public StkArtStockQty deleteById(String id)
   {
      StkArtStockQty entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkArtStockQty update(StkArtStockQty entity)
   {
      return repository.save(attach(entity));
   }

   public StkArtStockQty findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkArtStockQty> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkArtStockQty> findBy(StkArtStockQty entity, int start, int max, SingularAttribute<StkArtStockQty, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkArtStockQty entity, SingularAttribute<StkArtStockQty, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkArtStockQty> findByLike(StkArtStockQty entity, int start, int max, SingularAttribute<StkArtStockQty, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkArtStockQty entity, SingularAttribute<StkArtStockQty, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkArtStockQty attach(StkArtStockQty entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
