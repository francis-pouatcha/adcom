package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.WorkspaceRestriction;
import org.adorsys.adbase.repo.WorkspaceRestrictionRepository;

public class WorkspaceRestrictionMerger
{

   @Inject
   private WorkspaceRestrictionRepository repository;

   public WorkspaceRestriction bindComposed(WorkspaceRestriction entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public WorkspaceRestriction bindAggregated(WorkspaceRestriction entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<WorkspaceRestriction> entities)
   {
      if (entities == null)
         return;
      HashSet<WorkspaceRestriction> oldCol = new HashSet<WorkspaceRestriction>(entities);
      entities.clear();
      for (WorkspaceRestriction entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<WorkspaceRestriction> entities)
   {
      if (entities == null)
         return;
      HashSet<WorkspaceRestriction> oldCol = new HashSet<WorkspaceRestriction>(entities);
      entities.clear();
      for (WorkspaceRestriction entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public WorkspaceRestriction unbind(final WorkspaceRestriction entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      WorkspaceRestriction newEntity = new WorkspaceRestriction();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<WorkspaceRestriction> unbind(final Set<WorkspaceRestriction> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<WorkspaceRestriction>();
      //       HashSet<WorkspaceRestriction> cache = new HashSet<WorkspaceRestriction>(entities);
      //       entities.clear();
      //       for (WorkspaceRestriction entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
