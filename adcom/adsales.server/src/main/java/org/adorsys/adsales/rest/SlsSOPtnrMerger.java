package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsSOPtnr;
import org.adorsys.adsales.repo.SlsSOPtnrRepository;

public class SlsSOPtnrMerger
{

   @Inject
   private SlsSOPtnrRepository repository;

   public SlsSOPtnr bindComposed(SlsSOPtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsSOPtnr bindAggregated(SlsSOPtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsSOPtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSOPtnr> oldCol = new HashSet<SlsSOPtnr>(entities);
      entities.clear();
      for (SlsSOPtnr entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsSOPtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSOPtnr> oldCol = new HashSet<SlsSOPtnr>(entities);
      entities.clear();
      for (SlsSOPtnr entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsSOPtnr unbind(final SlsSOPtnr entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsSOPtnr newEntity = new SlsSOPtnr();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsSOPtnr> unbind(final Set<SlsSOPtnr> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsSOPtnr>();
      //       HashSet<SlsSOPtnr> cache = new HashSet<SlsSOPtnr>(entities);
      //       entities.clear();
      //       for (SlsSOPtnr entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
