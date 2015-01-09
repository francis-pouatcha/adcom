package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalManufSuppl;
import org.adorsys.adcatal.repo.CatalManufSupplRepository;

public class CatalManufSupplMerger
{

   @Inject
   private CatalManufSupplRepository repository;

   public CatalManufSuppl bindComposed(CatalManufSuppl entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalManufSuppl bindAggregated(CatalManufSuppl entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalManufSuppl> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalManufSuppl> oldCol = new HashSet<CatalManufSuppl>(entities);
      entities.clear();
      for (CatalManufSuppl entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalManufSuppl> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalManufSuppl> oldCol = new HashSet<CatalManufSuppl>(entities);
      entities.clear();
      for (CatalManufSuppl entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalManufSuppl unbind(final CatalManufSuppl entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalManufSuppl newEntity = new CatalManufSuppl();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalManufSuppl> unbind(final Set<CatalManufSuppl> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalManufSuppl>();
      //       HashSet<CatalManufSuppl> cache = new HashSet<CatalManufSuppl>(entities);
      //       entities.clear();
      //       for (CatalManufSuppl entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
