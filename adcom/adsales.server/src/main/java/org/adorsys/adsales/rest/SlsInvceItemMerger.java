package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsInvceItem;
import org.adorsys.adsales.repo.SlsInvceItemRepository;

public class SlsInvceItemMerger
{

   @Inject
   private SlsInvceItemRepository repository;

   public SlsInvceItem bindComposed(SlsInvceItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsInvceItem bindAggregated(SlsInvceItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsInvceItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvceItem> oldCol = new HashSet<SlsInvceItem>(entities);
      entities.clear();
      for (SlsInvceItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsInvceItem> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvceItem> oldCol = new HashSet<SlsInvceItem>(entities);
      entities.clear();
      for (SlsInvceItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsInvceItem unbind(final SlsInvceItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsInvceItem newEntity = new SlsInvceItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsInvceItem> unbind(final Set<SlsInvceItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsInvceItem>();
      //       HashSet<SlsInvceItem> cache = new HashSet<SlsInvceItem>(entities);
      //       entities.clear();
      //       for (SlsInvceItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
