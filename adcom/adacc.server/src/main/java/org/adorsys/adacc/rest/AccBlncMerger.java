package org.adorsys.adacc.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adacc.jpa.AccBlnc;
import org.adorsys.adacc.repo.AccBlncRepository;

public class AccBlncMerger
{

   @Inject
   private AccBlncRepository repository;

   public AccBlnc bindComposed(AccBlnc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public AccBlnc bindAggregated(AccBlnc entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<AccBlnc> entities)
   {
      if (entities == null)
         return;
      HashSet<AccBlnc> oldCol = new HashSet<AccBlnc>(entities);
      entities.clear();
      for (AccBlnc entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<AccBlnc> entities)
   {
      if (entities == null)
         return;
      HashSet<AccBlnc> oldCol = new HashSet<AccBlnc>(entities);
      entities.clear();
      for (AccBlnc entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public AccBlnc unbind(final AccBlnc entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      AccBlnc newEntity = new AccBlnc();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<AccBlnc> unbind(final Set<AccBlnc> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<AccBlnc>();
      //       HashSet<AccBlnc> cache = new HashSet<AccBlnc>(entities);
      //       entities.clear();
      //       for (AccBlnc entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
