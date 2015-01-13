package org.adorsys.adinvtry.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adinvtry.jpa.InvInvtry;
import org.adorsys.adinvtry.repo.InvInvtryRepository;

public class InvInvtryMerger
{

   @Inject
   private InvInvtryRepository repository;

   public InvInvtry bindComposed(InvInvtry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public InvInvtry bindAggregated(InvInvtry entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<InvInvtry> entities)
   {
      if (entities == null)
         return;
      HashSet<InvInvtry> oldCol = new HashSet<InvInvtry>(entities);
      entities.clear();
      for (InvInvtry entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<InvInvtry> entities)
   {
      if (entities == null)
         return;
      HashSet<InvInvtry> oldCol = new HashSet<InvInvtry>(entities);
      entities.clear();
      for (InvInvtry entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public InvInvtry unbind(final InvInvtry entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      InvInvtry newEntity = new InvInvtry();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<InvInvtry> unbind(final Set<InvInvtry> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<InvInvtry>();
      //       HashSet<InvInvtry> cache = new HashSet<InvInvtry>(entities);
      //       entities.clear();
      //       for (InvInvtry entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
