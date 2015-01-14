package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.repo.BpLegalPtnrIdRepository;

public class BpLegalPtnrIdMerger
{

   @Inject
   private BpLegalPtnrIdRepository repository;

   public BpLegalPtnrId bindComposed(BpLegalPtnrId entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpLegalPtnrId bindAggregated(BpLegalPtnrId entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpLegalPtnrId> entities)
   {
      if (entities == null)
         return;
      HashSet<BpLegalPtnrId> oldCol = new HashSet<BpLegalPtnrId>(entities);
      entities.clear();
      for (BpLegalPtnrId entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpLegalPtnrId> entities)
   {
      if (entities == null)
         return;
      HashSet<BpLegalPtnrId> oldCol = new HashSet<BpLegalPtnrId>(entities);
      entities.clear();
      for (BpLegalPtnrId entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpLegalPtnrId unbind(final BpLegalPtnrId entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpLegalPtnrId newEntity = new BpLegalPtnrId();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpLegalPtnrId> unbind(final Set<BpLegalPtnrId> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpLegalPtnrId>();
      //       HashSet<BpLegalPtnrId> cache = new HashSet<BpLegalPtnrId>(entities);
      //       entities.clear();
      //       for (BpLegalPtnrId entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
