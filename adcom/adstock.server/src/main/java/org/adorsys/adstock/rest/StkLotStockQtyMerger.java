package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkLotStockQty;
import org.adorsys.adstock.repo.StkLotStockQtyRepository;

public class StkLotStockQtyMerger
{

   @Inject
   private StkLotStockQtyRepository repository;

   public StkLotStockQty bindComposed(StkLotStockQty entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkLotStockQty bindAggregated(StkLotStockQty entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkLotStockQty> entities)
   {
      if (entities == null)
         return;
      HashSet<StkLotStockQty> oldCol = new HashSet<StkLotStockQty>(entities);
      entities.clear();
      for (StkLotStockQty entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkLotStockQty> entities)
   {
      if (entities == null)
         return;
      HashSet<StkLotStockQty> oldCol = new HashSet<StkLotStockQty>(entities);
      entities.clear();
      for (StkLotStockQty entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkLotStockQty unbind(final StkLotStockQty entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkLotStockQty newEntity = new StkLotStockQty();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkLotStockQty> unbind(final Set<StkLotStockQty> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkLotStockQty>();
      //       HashSet<StkLotStockQty> cache = new HashSet<StkLotStockQty>(entities);
      //       entities.clear();
      //       for (StkLotStockQty entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
