package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.repo.CatalArtManufSuppRepository;

public class CatalArtManufSuppMerger
{

   @Inject
   private CatalArtManufSuppRepository repository;

   public CatalArtManufSupp bindComposed(CatalArtManufSupp entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalArtManufSupp bindAggregated(CatalArtManufSupp entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalArtManufSupp> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtManufSupp> oldCol = new HashSet<CatalArtManufSupp>(entities);
      entities.clear();
      for (CatalArtManufSupp entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalArtManufSupp> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtManufSupp> oldCol = new HashSet<CatalArtManufSupp>(entities);
      entities.clear();
      for (CatalArtManufSupp entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalArtManufSupp unbind(final CatalArtManufSupp entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalArtManufSupp newEntity = new CatalArtManufSupp();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalArtManufSupp> unbind(final Set<CatalArtManufSupp> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalArtManufSupp>();
      //       HashSet<CatalArtManufSupp> cache = new HashSet<CatalArtManufSupp>(entities);
      //       entities.clear();
      //       for (CatalArtManufSupp entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
