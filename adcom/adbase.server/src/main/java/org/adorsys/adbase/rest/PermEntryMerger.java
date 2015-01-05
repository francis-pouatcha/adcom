package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.PermEntry;
import org.adorsys.adbase.repo.PermEntryRepository;

public class PermEntryMerger
{

   @Inject
   private PermEntryRepository repository;

   public PermEntry bindComposed(PermEntry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PermEntry bindAggregated(PermEntry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PermEntry> entities)
   {
      if (entities == null)
         return;
      HashSet<PermEntry> oldCol = new HashSet<PermEntry>(entities);
      entities.clear();
      for (PermEntry entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PermEntry> entities)
   {
      if (entities == null)
         return;
      HashSet<PermEntry> oldCol = new HashSet<PermEntry>(entities);
      entities.clear();
      for (PermEntry entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PermEntry unbind(final PermEntry entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PermEntry newEntity = new PermEntry();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PermEntry> unbind(final Set<PermEntry> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PermEntry>();
      //       HashSet<PermEntry> cache = new HashSet<PermEntry>(entities);
      //       entities.clear();
      //       for (PermEntry entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
