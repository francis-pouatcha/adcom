package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.repo.CatalPicMappingRepository;

public class CatalPicMappingMerger
{

   @Inject
   private CatalPicMappingRepository repository;

   public CatalPicMapping bindComposed(CatalPicMapping entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalPicMapping bindAggregated(CatalPicMapping entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalPicMapping> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalPicMapping> oldCol = new HashSet<CatalPicMapping>(entities);
      entities.clear();
      for (CatalPicMapping entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalPicMapping> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalPicMapping> oldCol = new HashSet<CatalPicMapping>(entities);
      entities.clear();
      for (CatalPicMapping entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalPicMapping unbind(final CatalPicMapping entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalPicMapping newEntity = new CatalPicMapping();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalPicMapping> unbind(final Set<CatalPicMapping> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalPicMapping>();
      //       HashSet<CatalPicMapping> cache = new HashSet<CatalPicMapping>(entities);
      //       entities.clear();
      //       for (CatalPicMapping entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
