package org.adorsys.adacc.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adacc.jpa.AccBrr;
import org.adorsys.adacc.repo.AccBrrRepository;

public class AccBrrMerger
{

   @Inject
   private AccBrrRepository repository;

   public AccBrr bindComposed(AccBrr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public AccBrr bindAggregated(AccBrr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<AccBrr> entities)
   {
      if (entities == null)
         return;
      HashSet<AccBrr> oldCol = new HashSet<AccBrr>(entities);
      entities.clear();
      for (AccBrr entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<AccBrr> entities)
   {
      if (entities == null)
         return;
      HashSet<AccBrr> oldCol = new HashSet<AccBrr>(entities);
      entities.clear();
      for (AccBrr entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public AccBrr unbind(final AccBrr entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      AccBrr newEntity = new AccBrr();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<AccBrr> unbind(final Set<AccBrr> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<AccBrr>();
      //       HashSet<AccBrr> cache = new HashSet<AccBrr>(entities);
      //       entities.clear();
      //       for (AccBrr entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
