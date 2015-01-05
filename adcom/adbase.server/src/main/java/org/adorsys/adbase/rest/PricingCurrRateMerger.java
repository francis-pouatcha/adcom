package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.PricingCurrRate;
import org.adorsys.adbase.repo.PricingCurrRateRepository;

public class PricingCurrRateMerger
{

   @Inject
   private PricingCurrRateRepository repository;

   public PricingCurrRate bindComposed(PricingCurrRate entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PricingCurrRate bindAggregated(PricingCurrRate entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PricingCurrRate> entities)
   {
      if (entities == null)
         return;
      HashSet<PricingCurrRate> oldCol = new HashSet<PricingCurrRate>(entities);
      entities.clear();
      for (PricingCurrRate entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PricingCurrRate> entities)
   {
      if (entities == null)
         return;
      HashSet<PricingCurrRate> oldCol = new HashSet<PricingCurrRate>(entities);
      entities.clear();
      for (PricingCurrRate entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PricingCurrRate unbind(final PricingCurrRate entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PricingCurrRate newEntity = new PricingCurrRate();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PricingCurrRate> unbind(final Set<PricingCurrRate> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PricingCurrRate>();
      //       HashSet<PricingCurrRate> cache = new HashSet<PricingCurrRate>(entities);
      //       entities.clear();
      //       for (PricingCurrRate entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
