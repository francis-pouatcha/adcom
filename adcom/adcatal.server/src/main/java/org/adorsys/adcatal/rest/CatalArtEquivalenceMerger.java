package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalArtEquivalence;
import org.adorsys.adcatal.repo.CatalArtEquivalenceRepository;

public class CatalArtEquivalenceMerger
{

   @Inject
   private CatalArtEquivalenceRepository repository;

   public CatalArtEquivalence bindComposed(CatalArtEquivalence entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalArtEquivalence bindAggregated(CatalArtEquivalence entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalArtEquivalence> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtEquivalence> oldCol = new HashSet<CatalArtEquivalence>(entities);
      entities.clear();
      for (CatalArtEquivalence entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalArtEquivalence> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArtEquivalence> oldCol = new HashSet<CatalArtEquivalence>(entities);
      entities.clear();
      for (CatalArtEquivalence entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalArtEquivalence unbind(final CatalArtEquivalence entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalArtEquivalence newEntity = new CatalArtEquivalence();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalArtEquivalence> unbind(final Set<CatalArtEquivalence> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalArtEquivalence>();
      //       HashSet<CatalArtEquivalence> cache = new HashSet<CatalArtEquivalence>(entities);
      //       entities.clear();
      //       for (CatalArtEquivalence entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
