package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.repo.BpPtnrCtgryRepository;

public class BpPtnrCtgryMerger
{

   @Inject
   private BpPtnrCtgryRepository repository;

   public BpPtnrCtgry bindComposed(BpPtnrCtgry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpPtnrCtgry bindAggregated(BpPtnrCtgry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpPtnrCtgry> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrCtgry> oldCol = new HashSet<BpPtnrCtgry>(entities);
      entities.clear();
      for (BpPtnrCtgry entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpPtnrCtgry> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrCtgry> oldCol = new HashSet<BpPtnrCtgry>(entities);
      entities.clear();
      for (BpPtnrCtgry entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpPtnrCtgry unbind(final BpPtnrCtgry entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpPtnrCtgry newEntity = new BpPtnrCtgry();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpPtnrCtgry> unbind(final Set<BpPtnrCtgry> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpPtnrCtgry>();
      //       HashSet<BpPtnrCtgry> cache = new HashSet<BpPtnrCtgry>(entities);
      //       entities.clear();
      //       for (BpPtnrCtgry entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
