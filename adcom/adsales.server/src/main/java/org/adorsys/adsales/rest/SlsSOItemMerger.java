package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.repo.SlsSOItemRepository;

public class SlsSOItemMerger
{

   @Inject
   private SlsSOItemRepository repository;

   public SlsSOItem bindComposed(SlsSOItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsSOItem bindAggregated(SlsSOItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsSOItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSOItem> oldCol = new HashSet<SlsSOItem>(entities);
      entities.clear();
      for (SlsSOItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsSOItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSOItem> oldCol = new HashSet<SlsSOItem>(entities);
      entities.clear();
      for (SlsSOItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsSOItem unbind(final SlsSOItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsSOItem newEntity = new SlsSOItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsSOItem> unbind(final Set<SlsSOItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsSOItem>();
      //       HashSet<SlsSOItem> cache = new HashSet<SlsSOItem>(entities);
      //       entities.clear();
      //       for (SlsSOItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
