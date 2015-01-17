package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsSttlmtOp;
import org.adorsys.adsales.repo.SlsSttlmtOpRepository;

public class SlsSttlmtOpMerger
{

   @Inject
   private SlsSttlmtOpRepository repository;

   public SlsSttlmtOp bindComposed(SlsSttlmtOp entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsSttlmtOp bindAggregated(SlsSttlmtOp entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsSttlmtOp> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSttlmtOp> oldCol = new HashSet<SlsSttlmtOp>(entities);
      entities.clear();
      for (SlsSttlmtOp entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsSttlmtOp> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsSttlmtOp> oldCol = new HashSet<SlsSttlmtOp>(entities);
      entities.clear();
      for (SlsSttlmtOp entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsSttlmtOp unbind(final SlsSttlmtOp entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsSttlmtOp newEntity = new SlsSttlmtOp();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsSttlmtOp> unbind(final Set<SlsSttlmtOp> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsSttlmtOp>();
      //       HashSet<SlsSttlmtOp> cache = new HashSet<SlsSttlmtOp>(entities);
      //       entities.clear();
      //       for (SlsSttlmtOp entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
