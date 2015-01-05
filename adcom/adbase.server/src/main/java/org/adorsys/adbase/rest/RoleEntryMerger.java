package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.RoleEntry;
import org.adorsys.adbase.repo.RoleEntryRepository;

public class RoleEntryMerger
{

   @Inject
   private RoleEntryRepository repository;

   public RoleEntry bindComposed(RoleEntry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public RoleEntry bindAggregated(RoleEntry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<RoleEntry> entities)
   {
      if (entities == null)
         return;
      HashSet<RoleEntry> oldCol = new HashSet<RoleEntry>(entities);
      entities.clear();
      for (RoleEntry entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<RoleEntry> entities)
   {
      if (entities == null)
         return;
      HashSet<RoleEntry> oldCol = new HashSet<RoleEntry>(entities);
      entities.clear();
      for (RoleEntry entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public RoleEntry unbind(final RoleEntry entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      RoleEntry newEntity = new RoleEntry();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<RoleEntry> unbind(final Set<RoleEntry> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<RoleEntry>();
      //       HashSet<RoleEntry> cache = new HashSet<RoleEntry>(entities);
      //       entities.clear();
      //       for (RoleEntry entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
