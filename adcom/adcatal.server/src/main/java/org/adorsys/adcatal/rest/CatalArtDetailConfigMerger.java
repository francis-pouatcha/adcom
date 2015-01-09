package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.repo.CatalArtDetailConfigRepository;

public class CatalArtDetailConfigMerger
{

   @Inject
   private CatalArtDetailConfigRepository repository;

   public CatalArtDetailConfig bindComposed(CatalArtDetailConfig entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalArtDetailConfig bindAggregated(CatalArtDetailConfig entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalArtDetailConfig> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtDetailConfig> oldCol = new HashSet<CatalArtDetailConfig>(entities);
      entities.clear();
      for (CatalArtDetailConfig entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalArtDetailConfig> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtDetailConfig> oldCol = new HashSet<CatalArtDetailConfig>(entities);
      entities.clear();
      for (CatalArtDetailConfig entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalArtDetailConfig unbind(final CatalArtDetailConfig entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalArtDetailConfig newEntity = new CatalArtDetailConfig();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalArtDetailConfig> unbind(final Set<CatalArtDetailConfig> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalArtDetailConfig>();
      //       HashSet<CatalArtDetailConfig> cache = new HashSet<CatalArtDetailConfig>(entities);
      //       entities.clear();
      //       for (CatalArtDetailConfig entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
