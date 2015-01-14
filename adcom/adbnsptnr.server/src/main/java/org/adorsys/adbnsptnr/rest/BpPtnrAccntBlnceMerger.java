package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpPtnrAccntBlnce;
import org.adorsys.adbnsptnr.repo.BpPtnrAccntBlnceRepository;

public class BpPtnrAccntBlnceMerger
{

   @Inject
   private BpPtnrAccntBlnceRepository repository;

   public BpPtnrAccntBlnce bindComposed(BpPtnrAccntBlnce entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpPtnrAccntBlnce bindAggregated(BpPtnrAccntBlnce entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpPtnrAccntBlnce> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrAccntBlnce> oldCol = new HashSet<BpPtnrAccntBlnce>(entities);
      entities.clear();
      for (BpPtnrAccntBlnce entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpPtnrAccntBlnce> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrAccntBlnce> oldCol = new HashSet<BpPtnrAccntBlnce>(entities);
      entities.clear();
      for (BpPtnrAccntBlnce entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpPtnrAccntBlnce unbind(final BpPtnrAccntBlnce entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpPtnrAccntBlnce newEntity = new BpPtnrAccntBlnce();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpPtnrAccntBlnce> unbind(final Set<BpPtnrAccntBlnce> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpPtnrAccntBlnce>();
      //       HashSet<BpPtnrAccntBlnce> cache = new HashSet<BpPtnrAccntBlnce>(entities);
      //       entities.clear();
      //       for (BpPtnrAccntBlnce entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
