package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsInvcePtnr;
import org.adorsys.adsales.repo.SlsInvcePtnrRepository;

public class SlsInvcePtnrMerger
{

   @Inject
   private SlsInvcePtnrRepository repository;

   public SlsInvcePtnr bindComposed(SlsInvcePtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsInvcePtnr bindAggregated(SlsInvcePtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsInvcePtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvcePtnr> oldCol = new HashSet<SlsInvcePtnr>(entities);
      entities.clear();
      for (SlsInvcePtnr entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsInvcePtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvcePtnr> oldCol = new HashSet<SlsInvcePtnr>(entities);
      entities.clear();
      for (SlsInvcePtnr entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsInvcePtnr unbind(final SlsInvcePtnr entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsInvcePtnr newEntity = new SlsInvcePtnr();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsInvcePtnr> unbind(final Set<SlsInvcePtnr> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsInvcePtnr>();
      //       HashSet<SlsInvcePtnr> cache = new HashSet<SlsInvcePtnr>(entities);
      //       entities.clear();
      //       for (SlsInvcePtnr entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
