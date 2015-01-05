package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.UserWsRestriction;
import org.adorsys.adbase.repo.UserWsRestrictionRepository;

public class UserWsRestrictionMerger
{

   @Inject
   private UserWsRestrictionRepository repository;

   public UserWsRestriction bindComposed(UserWsRestriction entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public UserWsRestriction bindAggregated(UserWsRestriction entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<UserWsRestriction> entities)
   {
      if (entities == null)
         return;
      HashSet<UserWsRestriction> oldCol = new HashSet<UserWsRestriction>(entities);
      entities.clear();
      for (UserWsRestriction entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<UserWsRestriction> entities)
   {
      if (entities == null)
         return;
      HashSet<UserWsRestriction> oldCol = new HashSet<UserWsRestriction>(entities);
      entities.clear();
      for (UserWsRestriction entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public UserWsRestriction unbind(final UserWsRestriction entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      UserWsRestriction newEntity = new UserWsRestriction();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<UserWsRestriction> unbind(final Set<UserWsRestriction> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<UserWsRestriction>();
      //       HashSet<UserWsRestriction> cache = new HashSet<UserWsRestriction>(entities);
      //       entities.clear();
      //       for (UserWsRestriction entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
