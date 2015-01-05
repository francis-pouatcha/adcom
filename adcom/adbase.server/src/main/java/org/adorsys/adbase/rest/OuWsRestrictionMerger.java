package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuWsRestriction;
import org.adorsys.adbase.repo.OuWsRestrictionRepository;

public class OuWsRestrictionMerger
{

   @Inject
   private OuWsRestrictionRepository repository;

   public OuWsRestriction bindComposed(OuWsRestriction entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public OuWsRestriction bindAggregated(OuWsRestriction entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<OuWsRestriction> entities)
   {
      if (entities == null)
         return;
      HashSet<OuWsRestriction> oldCol = new HashSet<OuWsRestriction>(entities);
      entities.clear();
      for (OuWsRestriction entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<OuWsRestriction> entities)
   {
      if (entities == null)
         return;
      HashSet<OuWsRestriction> oldCol = new HashSet<OuWsRestriction>(entities);
      entities.clear();
      for (OuWsRestriction entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public OuWsRestriction unbind(final OuWsRestriction entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      OuWsRestriction newEntity = new OuWsRestriction();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<OuWsRestriction> unbind(final Set<OuWsRestriction> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<OuWsRestriction>();
      //       HashSet<OuWsRestriction> cache = new HashSet<OuWsRestriction>(entities);
      //       entities.clear();
      //       for (OuWsRestriction entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
