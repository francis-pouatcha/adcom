package org.adorsys.adprocmt.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

public class PrcmtPOItemMerger
{

   @Inject
   private PrcmtPOItemRepository repository;

   public PrcmtPOItem bindComposed(PrcmtPOItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PrcmtPOItem bindAggregated(PrcmtPOItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PrcmtPOItem> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtPOItem> oldCol = new HashSet<PrcmtPOItem>(entities);
      entities.clear();
      for (PrcmtPOItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PrcmtPOItem> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtPOItem> oldCol = new HashSet<PrcmtPOItem>(entities);
      entities.clear();
      for (PrcmtPOItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PrcmtPOItem unbind(final PrcmtPOItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PrcmtPOItem newEntity = new PrcmtPOItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PrcmtPOItem> unbind(final Set<PrcmtPOItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PrcmtPOItem>();
      //       HashSet<PrcmtPOItem> cache = new HashSet<PrcmtPOItem>(entities);
      //       entities.clear();
      //       for (PrcmtPOItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
