package org.adorsys.adprocmt.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;

public class PrcmtDeliveryMerger
{

   @Inject
   private PrcmtDeliveryRepository repository;

   public PrcmtDelivery bindComposed(PrcmtDelivery entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PrcmtDelivery bindAggregated(PrcmtDelivery entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PrcmtDelivery> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtDelivery> oldCol = new HashSet<PrcmtDelivery>(entities);
      entities.clear();
      for (PrcmtDelivery entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PrcmtDelivery> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtDelivery> oldCol = new HashSet<PrcmtDelivery>(entities);
      entities.clear();
      for (PrcmtDelivery entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PrcmtDelivery unbind(final PrcmtDelivery entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PrcmtDelivery newEntity = new PrcmtDelivery();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PrcmtDelivery> unbind(final Set<PrcmtDelivery> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PrcmtDelivery>();
      //       HashSet<PrcmtDelivery> cache = new HashSet<PrcmtDelivery>(entities);
      //       entities.clear();
      //       for (PrcmtDelivery entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
