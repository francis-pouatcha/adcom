package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsSOHstry;
import org.adorsys.adsales.repo.SlsSOHstryRepository;

public class SlsSOHstryMerger
{

   @Inject
   private SlsSOHstryRepository repository;

   public SlsSOHstry bindComposed(SlsSOHstry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsSOHstry bindAggregated(SlsSOHstry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsSOHstry> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSOHstry> oldCol = new HashSet<SlsSOHstry>(entities);
      entities.clear();
      for (SlsSOHstry entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsSOHstry> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSOHstry> oldCol = new HashSet<SlsSOHstry>(entities);
      entities.clear();
      for (SlsSOHstry entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsSOHstry unbind(final SlsSOHstry entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsSOHstry newEntity = new SlsSOHstry();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsSOHstry> unbind(final Set<SlsSOHstry> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsSOHstry>();
      //       HashSet<SlsSOHstry> cache = new HashSet<SlsSOHstry>(entities);
      //       entities.clear();
      //       for (SlsSOHstry entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
