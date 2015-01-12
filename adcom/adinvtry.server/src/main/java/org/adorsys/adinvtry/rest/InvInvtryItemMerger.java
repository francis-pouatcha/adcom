package org.adorsys.adinvtry.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.adorsys.adinvtry.repo.InvInvtryItemRepository;

public class InvInvtryItemMerger
{

   @Inject
   private InvInvtryItemRepository repository;

   public InvInvtryItem bindComposed(InvInvtryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public InvInvtryItem bindAggregated(InvInvtryItem entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<InvInvtryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<InvInvtryItem> oldCol = new HashSet<InvInvtryItem>(entities);
      entities.clear();
      for (InvInvtryItem entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<InvInvtryItem> entities)
   {
      if (entities == null)
         return;
      HashSet<InvInvtryItem> oldCol = new HashSet<InvInvtryItem>(entities);
      entities.clear();
      for (InvInvtryItem entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public InvInvtryItem unbind(final InvInvtryItem entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      InvInvtryItem newEntity = new InvInvtryItem();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<InvInvtryItem> unbind(final Set<InvInvtryItem> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<InvInvtryItem>();
      //       HashSet<InvInvtryItem> cache = new HashSet<InvInvtryItem>(entities);
      //       entities.clear();
      //       for (InvInvtryItem entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
