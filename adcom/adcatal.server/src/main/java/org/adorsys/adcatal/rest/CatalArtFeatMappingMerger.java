package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.repo.CatalArtFeatMappingRepository;

public class CatalArtFeatMappingMerger
{

   @Inject
   private CatalArtFeatMappingRepository repository;

   public CatalArtFeatMapping bindComposed(CatalArtFeatMapping entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalArtFeatMapping bindAggregated(CatalArtFeatMapping entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalArtFeatMapping> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtFeatMapping> oldCol = new HashSet<CatalArtFeatMapping>(entities);
      entities.clear();
      for (CatalArtFeatMapping entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalArtFeatMapping> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtFeatMapping> oldCol = new HashSet<CatalArtFeatMapping>(entities);
      entities.clear();
      for (CatalArtFeatMapping entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalArtFeatMapping unbind(final CatalArtFeatMapping entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalArtFeatMapping newEntity = new CatalArtFeatMapping();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalArtFeatMapping> unbind(final Set<CatalArtFeatMapping> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalArtFeatMapping>();
      //       HashSet<CatalArtFeatMapping> cache = new HashSet<CatalArtFeatMapping>(entities);
      //       entities.clear();
      //       for (CatalArtFeatMapping entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
