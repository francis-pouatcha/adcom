package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsRoleInSales;
import org.adorsys.adsales.repo.SlsRoleInSalesRepository;

public class SlsRoleInSalesMerger
{

   @Inject
   private SlsRoleInSalesRepository repository;

   public SlsRoleInSales bindComposed(SlsRoleInSales entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsRoleInSales bindAggregated(SlsRoleInSales entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsRoleInSales> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsRoleInSales> oldCol = new HashSet<SlsRoleInSales>(entities);
      entities.clear();
      for (SlsRoleInSales entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsRoleInSales> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsRoleInSales> oldCol = new HashSet<SlsRoleInSales>(entities);
      entities.clear();
      for (SlsRoleInSales entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsRoleInSales unbind(final SlsRoleInSales entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsRoleInSales newEntity = new SlsRoleInSales();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsRoleInSales> unbind(final Set<SlsRoleInSales> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsRoleInSales>();
      //       HashSet<SlsRoleInSales> cache = new HashSet<SlsRoleInSales>(entities);
      //       entities.clear();
      //       for (SlsRoleInSales entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
