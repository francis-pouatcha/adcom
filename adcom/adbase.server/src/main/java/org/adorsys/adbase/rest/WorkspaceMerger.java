package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.Workspace;
import org.adorsys.adbase.repo.WorkspaceRepository;

public class WorkspaceMerger
{

   @Inject
   private WorkspaceRepository repository;

   public Workspace bindComposed(Workspace entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Workspace bindAggregated(Workspace entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Workspace> entities)
   {
      if (entities == null)
         return;
      HashSet<Workspace> oldCol = new HashSet<Workspace>(entities);
      entities.clear();
      for (Workspace entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Workspace> entities)
   {
      if (entities == null)
         return;
      HashSet<Workspace> oldCol = new HashSet<Workspace>(entities);
      entities.clear();
      for (Workspace entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Workspace unbind(final Workspace entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Workspace newEntity = new Workspace();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Workspace> unbind(final Set<Workspace> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Workspace>();
      //       HashSet<Workspace> cache = new HashSet<Workspace>(entities);
      //       entities.clear();
      //       for (Workspace entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
