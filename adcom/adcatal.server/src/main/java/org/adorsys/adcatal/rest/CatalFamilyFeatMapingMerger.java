package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.repo.CatalFamilyFeatMapingRepository;

public class CatalFamilyFeatMapingMerger
{

   @Inject
   private CatalFamilyFeatMapingRepository repository;

   public CatalFamilyFeatMaping bindComposed(CatalFamilyFeatMaping entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalFamilyFeatMaping bindAggregated(CatalFamilyFeatMaping entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalFamilyFeatMaping> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalFamilyFeatMaping> oldCol = new HashSet<CatalFamilyFeatMaping>(entities);
      entities.clear();
      for (CatalFamilyFeatMaping entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalFamilyFeatMaping> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalFamilyFeatMaping> oldCol = new HashSet<CatalFamilyFeatMaping>(entities);
      entities.clear();
      for (CatalFamilyFeatMaping entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalFamilyFeatMaping unbind(final CatalFamilyFeatMaping entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalFamilyFeatMaping newEntity = new CatalFamilyFeatMaping();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalFamilyFeatMaping> unbind(final Set<CatalFamilyFeatMaping> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalFamilyFeatMaping>();
      //       HashSet<CatalFamilyFeatMaping> cache = new HashSet<CatalFamilyFeatMaping>(entities);
      //       entities.clear();
      //       for (CatalFamilyFeatMaping entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
