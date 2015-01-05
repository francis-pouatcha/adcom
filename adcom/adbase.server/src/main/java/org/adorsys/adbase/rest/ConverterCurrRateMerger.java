package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.ConverterCurrRate;
import org.adorsys.adbase.repo.ConverterCurrRateRepository;

public class ConverterCurrRateMerger
{

   @Inject
   private ConverterCurrRateRepository repository;

   public ConverterCurrRate bindComposed(ConverterCurrRate entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public ConverterCurrRate bindAggregated(ConverterCurrRate entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<ConverterCurrRate> entities)
   {
      if (entities == null)
         return;
      HashSet<ConverterCurrRate> oldCol = new HashSet<ConverterCurrRate>(entities);
      entities.clear();
      for (ConverterCurrRate entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<ConverterCurrRate> entities)
   {
      if (entities == null)
         return;
      HashSet<ConverterCurrRate> oldCol = new HashSet<ConverterCurrRate>(entities);
      entities.clear();
      for (ConverterCurrRate entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public ConverterCurrRate unbind(final ConverterCurrRate entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      ConverterCurrRate newEntity = new ConverterCurrRate();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<ConverterCurrRate> unbind(final Set<ConverterCurrRate> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<ConverterCurrRate>();
      //       HashSet<ConverterCurrRate> cache = new HashSet<ConverterCurrRate>(entities);
      //       entities.clear();
      //       for (ConverterCurrRate entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
