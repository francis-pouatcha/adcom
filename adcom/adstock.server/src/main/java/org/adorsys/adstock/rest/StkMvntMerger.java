package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkMvnt;
import org.adorsys.adstock.repo.StkMvntRepository;

public class StkMvntMerger
{

   @Inject
   private StkMvntRepository repository;

   public StkMvnt bindComposed(StkMvnt entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkMvnt bindAggregated(StkMvnt entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkMvnt> entities)
   {
      if (entities == null)
         return;
      HashSet<StkMvnt> oldCol = new HashSet<StkMvnt>(entities);
      entities.clear();
      for (StkMvnt entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkMvnt> entities)
   {
      if (entities == null)
         return;
      HashSet<StkMvnt> oldCol = new HashSet<StkMvnt>(entities);
      entities.clear();
      for (StkMvnt entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkMvnt unbind(final StkMvnt entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkMvnt newEntity = new StkMvnt();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkMvnt> unbind(final Set<StkMvnt> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkMvnt>();
      //       HashSet<StkMvnt> cache = new HashSet<StkMvnt>(entities);
      //       entities.clear();
      //       for (StkMvnt entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
