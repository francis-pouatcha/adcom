package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkInvtryItem;
import org.adorsys.adstock.repo.StkInvtryItemRepository;

public class StkInvtryItemMerger
{

   @Inject
   private StkInvtryItemRepository repository;

   public StkInvtryItem bindComposed(StkInvtryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkInvtryItem bindAggregated(StkInvtryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkInvtryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<StkInvtryItem> oldCol = new HashSet<StkInvtryItem>(entities);
      entities.clear();
      for (StkInvtryItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkInvtryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<StkInvtryItem> oldCol = new HashSet<StkInvtryItem>(entities);
      entities.clear();
      for (StkInvtryItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkInvtryItem unbind(final StkInvtryItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkInvtryItem newEntity = new StkInvtryItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkInvtryItem> unbind(final Set<StkInvtryItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkInvtryItem>();
      //       HashSet<StkInvtryItem> cache = new HashSet<StkInvtryItem>(entities);
      //       entities.clear();
      //       for (StkInvtryItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
