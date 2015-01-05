package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWorkspace;
import org.adorsys.adbase.repo.UserWorkspaceRepository;

public class UserWorkspaceMerger
{

   @Inject
   private UserWorkspaceRepository repository;

   public UserWorkspace bindComposed(UserWorkspace entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public UserWorkspace bindAggregated(UserWorkspace entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<UserWorkspace> entities)
   {
      if (entities == null)
         return;
      HashSet<UserWorkspace> oldCol = new HashSet<UserWorkspace>(entities);
      entities.clear();
      for (UserWorkspace entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<UserWorkspace> entities)
   {
      if (entities == null)
         return;
      HashSet<UserWorkspace> oldCol = new HashSet<UserWorkspace>(entities);
      entities.clear();
      for (UserWorkspace entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public UserWorkspace unbind(final UserWorkspace entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      UserWorkspace newEntity = new UserWorkspace();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<UserWorkspace> unbind(final Set<UserWorkspace> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<UserWorkspace>();
      //       HashSet<UserWorkspace> cache = new HashSet<UserWorkspace>(entities);
      //       entities.clear();
      //       for (UserWorkspace entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
