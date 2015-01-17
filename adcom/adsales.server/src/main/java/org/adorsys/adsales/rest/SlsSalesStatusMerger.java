package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsSalesStatus;
import org.adorsys.adsales.repo.SlsSalesStatusRepository;

public class SlsSalesStatusMerger
{

   @Inject
   private SlsSalesStatusRepository repository;

   public SlsSalesStatus bindComposed(SlsSalesStatus entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsSalesStatus bindAggregated(SlsSalesStatus entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsSalesStatus> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSalesStatus> oldCol = new HashSet<SlsSalesStatus>(entities);
      entities.clear();
      for (SlsSalesStatus entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsSalesStatus> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSalesStatus> oldCol = new HashSet<SlsSalesStatus>(entities);
      entities.clear();
      for (SlsSalesStatus entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsSalesStatus unbind(final SlsSalesStatus entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsSalesStatus newEntity = new SlsSalesStatus();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsSalesStatus> unbind(final Set<SlsSalesStatus> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsSalesStatus>();
      //       HashSet<SlsSalesStatus> cache = new HashSet<SlsSalesStatus>(entities);
      //       entities.clear();
      //       for (SlsSalesStatus entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
