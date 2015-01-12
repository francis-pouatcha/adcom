package org.adorsys.adprocmt.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.repo.PrcmtProcOrderRepository;

public class PrcmtProcOrderMerger
{

   @Inject
   private PrcmtProcOrderRepository repository;

   public PrcmtProcOrder bindComposed(PrcmtProcOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PrcmtProcOrder bindAggregated(PrcmtProcOrder entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PrcmtProcOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtProcOrder> oldCol = new HashSet<PrcmtProcOrder>(entities);
      entities.clear();
      for (PrcmtProcOrder entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PrcmtProcOrder> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtProcOrder> oldCol = new HashSet<PrcmtProcOrder>(entities);
      entities.clear();
      for (PrcmtProcOrder entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PrcmtProcOrder unbind(final PrcmtProcOrder entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PrcmtProcOrder newEntity = new PrcmtProcOrder();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PrcmtProcOrder> unbind(final Set<PrcmtProcOrder> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PrcmtProcOrder>();
      //       HashSet<PrcmtProcOrder> cache = new HashSet<PrcmtProcOrder>(entities);
      //       entities.clear();
      //       for (PrcmtProcOrder entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
