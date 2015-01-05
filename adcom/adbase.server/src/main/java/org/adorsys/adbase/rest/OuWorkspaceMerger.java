package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.repo.OuWorkspaceRepository;

public class OuWorkspaceMerger
{

   @Inject
   private OuWorkspaceRepository repository;

   public OuWorkspace bindComposed(OuWorkspace entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public OuWorkspace bindAggregated(OuWorkspace entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<OuWorkspace> entities)
   {
      if (entities == null)
         return;
      HashSet<OuWorkspace> oldCol = new HashSet<OuWorkspace>(entities);
      entities.clear();
      for (OuWorkspace entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<OuWorkspace> entities)
   {
      if (entities == null)
         return;
      HashSet<OuWorkspace> oldCol = new HashSet<OuWorkspace>(entities);
      entities.clear();
      for (OuWorkspace entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public OuWorkspace unbind(final OuWorkspace entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      OuWorkspace newEntity = new OuWorkspace();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<OuWorkspace> unbind(final Set<OuWorkspace> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<OuWorkspace>();
      //       HashSet<OuWorkspace> cache = new HashSet<OuWorkspace>(entities);
      //       entities.clear();
      //       for (OuWorkspace entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
