package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsInvceStatus;
import org.adorsys.adsales.repo.SlsInvceStatusRepository;

public class SlsInvceStatusMerger
{

   @Inject
   private SlsInvceStatusRepository repository;

   public SlsInvceStatus bindComposed(SlsInvceStatus entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsInvceStatus bindAggregated(SlsInvceStatus entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsInvceStatus> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvceStatus> oldCol = new HashSet<SlsInvceStatus>(entities);
      entities.clear();
      for (SlsInvceStatus entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsInvceStatus> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvceStatus> oldCol = new HashSet<SlsInvceStatus>(entities);
      entities.clear();
      for (SlsInvceStatus entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsInvceStatus unbind(final SlsInvceStatus entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsInvceStatus newEntity = new SlsInvceStatus();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsInvceStatus> unbind(final Set<SlsInvceStatus> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsInvceStatus>();
      //       HashSet<SlsInvceStatus> cache = new HashSet<SlsInvceStatus>(entities);
      //       entities.clear();
      //       for (SlsInvceStatus entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
