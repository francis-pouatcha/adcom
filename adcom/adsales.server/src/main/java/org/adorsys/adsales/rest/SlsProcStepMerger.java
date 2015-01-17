package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsProcStep;
import org.adorsys.adsales.repo.SlsProcStepRepository;

public class SlsProcStepMerger
{

   @Inject
   private SlsProcStepRepository repository;

   public SlsProcStep bindComposed(SlsProcStep entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsProcStep bindAggregated(SlsProcStep entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsProcStep> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsProcStep> oldCol = new HashSet<SlsProcStep>(entities);
      entities.clear();
      for (SlsProcStep entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsProcStep> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsProcStep> oldCol = new HashSet<SlsProcStep>(entities);
      entities.clear();
      for (SlsProcStep entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsProcStep unbind(final SlsProcStep entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsProcStep newEntity = new SlsProcStep();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsProcStep> unbind(final Set<SlsProcStep> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsProcStep>();
      //       HashSet<ProcStep> cache = new HashSet<ProcStep>(entities);
      //       entities.clear();
      //       for (ProcStep entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
