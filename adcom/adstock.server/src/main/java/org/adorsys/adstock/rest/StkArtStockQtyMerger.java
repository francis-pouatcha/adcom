package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkArtStockQty;
import org.adorsys.adstock.repo.StkArtStockQtyRepository;

public class StkArtStockQtyMerger
{

   @Inject
   private StkArtStockQtyRepository repository;

   public StkArtStockQty bindComposed(StkArtStockQty entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkArtStockQty bindAggregated(StkArtStockQty entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkArtStockQty> entities)
   {
      if (entities == null)
         return;
      HashSet<StkArtStockQty> oldCol = new HashSet<StkArtStockQty>(entities);
      entities.clear();
      for (StkArtStockQty entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkArtStockQty> entities)
   {
      if (entities == null)
         return;
      HashSet<StkArtStockQty> oldCol = new HashSet<StkArtStockQty>(entities);
      entities.clear();
      for (StkArtStockQty entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkArtStockQty unbind(final StkArtStockQty entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkArtStockQty newEntity = new StkArtStockQty();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkArtStockQty> unbind(final Set<StkArtStockQty> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkArtStockQty>();
      //       HashSet<StkArtStockQty> cache = new HashSet<StkArtStockQty>(entities);
      //       entities.clear();
      //       for (StkArtStockQty entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
