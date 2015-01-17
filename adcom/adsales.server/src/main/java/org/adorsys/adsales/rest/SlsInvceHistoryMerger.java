package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsInvceHistory;
import org.adorsys.adsales.repo.SlsInvceHistoryRepository;

public class SlsInvceHistoryMerger
{

   @Inject
   private SlsInvceHistoryRepository repository;

   public SlsInvceHistory bindComposed(SlsInvceHistory entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsInvceHistory bindAggregated(SlsInvceHistory entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsInvceHistory> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvceHistory> oldCol = new HashSet<SlsInvceHistory>(entities);
      entities.clear();
      for (SlsInvceHistory entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsInvceHistory> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvceHistory> oldCol = new HashSet<SlsInvceHistory>(entities);
      entities.clear();
      for (SlsInvceHistory entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsInvceHistory unbind(final SlsInvceHistory entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsInvceHistory newEntity = new SlsInvceHistory();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsInvceHistory> unbind(final Set<SlsInvceHistory> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsInvceHistory>();
      //       HashSet<SlsInvceHistory> cache = new HashSet<SlsInvceHistory>(entities);
      //       entities.clear();
      //       for (SlsInvceHistory entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
