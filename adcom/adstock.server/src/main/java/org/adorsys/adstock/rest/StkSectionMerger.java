package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkSection;
import org.adorsys.adstock.repo.StkSectionRepository;

public class StkSectionMerger
{

   @Inject
   private StkSectionRepository repository;

   public StkSection bindComposed(StkSection entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkSection bindAggregated(StkSection entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkSection> entities)
   {
      if (entities == null)
         return;
      HashSet<StkSection> oldCol = new HashSet<StkSection>(entities);
      entities.clear();
      for (StkSection entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkSection> entities)
   {
      if (entities == null)
         return;
      HashSet<StkSection> oldCol = new HashSet<StkSection>(entities);
      entities.clear();
      for (StkSection entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkSection unbind(final StkSection entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkSection newEntity = new StkSection();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkSection> unbind(final Set<StkSection> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkSection>();
      //       HashSet<StkSection> cache = new HashSet<StkSection>(entities);
      //       entities.clear();
      //       for (StkSection entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
