package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkInvtry;
import org.adorsys.adstock.repo.StkInvtryRepository;

public class StkInvtryMerger
{

   @Inject
   private StkInvtryRepository repository;

   public StkInvtry bindComposed(StkInvtry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkInvtry bindAggregated(StkInvtry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkInvtry> entities)
   {
      if (entities == null)
         return;
      HashSet<StkInvtry> oldCol = new HashSet<StkInvtry>(entities);
      entities.clear();
      for (StkInvtry entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkInvtry> entities)
   {
      if (entities == null)
         return;
      HashSet<StkInvtry> oldCol = new HashSet<StkInvtry>(entities);
      entities.clear();
      for (StkInvtry entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkInvtry unbind(final StkInvtry entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkInvtry newEntity = new StkInvtry();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkInvtry> unbind(final Set<StkInvtry> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkInvtry>();
      //       HashSet<StkInvtry> cache = new HashSet<StkInvtry>(entities);
      //       entities.clear();
      //       for (StkInvtry entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
