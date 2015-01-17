package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsHistoryType;
import org.adorsys.adsales.repo.SlsHistoryTypeRepository;

public class SlsHistoryTypeMerger
{

   @Inject
   private SlsHistoryTypeRepository repository;

   public SlsHistoryType bindComposed(SlsHistoryType entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsHistoryType bindAggregated(SlsHistoryType entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsHistoryType> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsHistoryType> oldCol = new HashSet<SlsHistoryType>(entities);
      entities.clear();
      for (SlsHistoryType entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsHistoryType> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsHistoryType> oldCol = new HashSet<SlsHistoryType>(entities);
      entities.clear();
      for (SlsHistoryType entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsHistoryType unbind(final SlsHistoryType entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsHistoryType newEntity = new SlsHistoryType();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsHistoryType> unbind(final Set<SlsHistoryType> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsHistoryType>();
      //       HashSet<HistoryType> cache = new HashSet<HistoryType>(entities);
      //       entities.clear();
      //       for (HistoryType entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
