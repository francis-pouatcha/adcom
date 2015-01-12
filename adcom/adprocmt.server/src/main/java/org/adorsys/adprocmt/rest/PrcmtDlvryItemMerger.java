package org.adorsys.adprocmt.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemRepository;

public class PrcmtDlvryItemMerger
{

   @Inject
   private PrcmtDlvryItemRepository repository;

   public PrcmtDlvryItem bindComposed(PrcmtDlvryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public PrcmtDlvryItem bindAggregated(PrcmtDlvryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<PrcmtDlvryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtDlvryItem> oldCol = new HashSet<PrcmtDlvryItem>(entities);
      entities.clear();
      for (PrcmtDlvryItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<PrcmtDlvryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<PrcmtDlvryItem> oldCol = new HashSet<PrcmtDlvryItem>(entities);
      entities.clear();
      for (PrcmtDlvryItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public PrcmtDlvryItem unbind(final PrcmtDlvryItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      PrcmtDlvryItem newEntity = new PrcmtDlvryItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<PrcmtDlvryItem> unbind(final Set<PrcmtDlvryItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<PrcmtDlvryItem>();
      //       HashSet<PrcmtDlvryItem> cache = new HashSet<PrcmtDlvryItem>(entities);
      //       entities.clear();
      //       for (PrcmtDlvryItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
