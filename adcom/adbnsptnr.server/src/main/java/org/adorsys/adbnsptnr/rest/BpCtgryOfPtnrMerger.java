package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpCtgryOfPtnr;
import org.adorsys.adbnsptnr.repo.BpCtgryOfPtnrRepository;

public class BpCtgryOfPtnrMerger
{

   @Inject
   private BpCtgryOfPtnrRepository repository;

   public BpCtgryOfPtnr bindComposed(BpCtgryOfPtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpCtgryOfPtnr bindAggregated(BpCtgryOfPtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpCtgryOfPtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<BpCtgryOfPtnr> oldCol = new HashSet<BpCtgryOfPtnr>(entities);
      entities.clear();
      for (BpCtgryOfPtnr entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpCtgryOfPtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<BpCtgryOfPtnr> oldCol = new HashSet<BpCtgryOfPtnr>(entities);
      entities.clear();
      for (BpCtgryOfPtnr entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpCtgryOfPtnr unbind(final BpCtgryOfPtnr entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpCtgryOfPtnr newEntity = new BpCtgryOfPtnr();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpCtgryOfPtnr> unbind(final Set<BpCtgryOfPtnr> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpCtgryOfPtnr>();
      //       HashSet<BpCtgryOfPtnr> cache = new HashSet<BpCtgryOfPtnr>(entities);
      //       entities.clear();
      //       for (BpCtgryOfPtnr entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
