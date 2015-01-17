package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsSalesOrder;
import org.adorsys.adsales.repo.SlsSalesOrderRepository;

public class SlsSalesOrderMerger
{

   @Inject
   private SlsSalesOrderRepository repository;

   public SlsSalesOrder bindComposed(SlsSalesOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsSalesOrder bindAggregated(SlsSalesOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsSalesOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSalesOrder> oldCol = new HashSet<SlsSalesOrder>(entities);
      entities.clear();
      for (SlsSalesOrder entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsSalesOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSalesOrder> oldCol = new HashSet<SlsSalesOrder>(entities);
      entities.clear();
      for (SlsSalesOrder entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsSalesOrder unbind(final SlsSalesOrder entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsSalesOrder newEntity = new SlsSalesOrder();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsSalesOrder> unbind(final Set<SlsSalesOrder> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsSalesOrder>();
      //       HashSet<SlsSalesOrder> cache = new HashSet<SlsSalesOrder>(entities);
      //       entities.clear();
      //       for (SlsSalesOrder entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
