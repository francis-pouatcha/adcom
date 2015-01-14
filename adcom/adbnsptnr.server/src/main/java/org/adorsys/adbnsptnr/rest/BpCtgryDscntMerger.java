package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpCtgryDscnt;
import org.adorsys.adbnsptnr.repo.BpCtgryDscntRepository;

public class BpCtgryDscntMerger
{

   @Inject
   private BpCtgryDscntRepository repository;

   public BpCtgryDscnt bindComposed(BpCtgryDscnt entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpCtgryDscnt bindAggregated(BpCtgryDscnt entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpCtgryDscnt> entities)
   {
      if (entities == null)
         return;
      HashSet<BpCtgryDscnt> oldCol = new HashSet<BpCtgryDscnt>(entities);
      entities.clear();
      for (BpCtgryDscnt entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpCtgryDscnt> entities)
   {
      if (entities == null)
         return;
      HashSet<BpCtgryDscnt> oldCol = new HashSet<BpCtgryDscnt>(entities);
      entities.clear();
      for (BpCtgryDscnt entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpCtgryDscnt unbind(final BpCtgryDscnt entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpCtgryDscnt newEntity = new BpCtgryDscnt();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpCtgryDscnt> unbind(final Set<BpCtgryDscnt> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpCtgryDscnt>();
      //       HashSet<BpCtgryDscnt> cache = new HashSet<BpCtgryDscnt>(entities);
      //       entities.clear();
      //       for (BpCtgryDscnt entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
