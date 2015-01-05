package org.adorsys.adacc.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adacc.jpa.AccCoA;
import org.adorsys.adacc.repo.AccCoARepository;

public class AccCoAMerger
{

   @Inject
   private AccCoARepository repository;

   public AccCoA bindComposed(AccCoA entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public AccCoA bindAggregated(AccCoA entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<AccCoA> entities)
   {
      if (entities == null)
         return;
      HashSet<AccCoA> oldCol = new HashSet<AccCoA>(entities);
      entities.clear();
      for (AccCoA entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<AccCoA> entities)
   {
      if (entities == null)
         return;
      HashSet<AccCoA> oldCol = new HashSet<AccCoA>(entities);
      entities.clear();
      for (AccCoA entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public AccCoA unbind(final AccCoA entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      AccCoA newEntity = new AccCoA();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<AccCoA> unbind(final Set<AccCoA> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<AccCoA>();
      //       HashSet<AccCoA> cache = new HashSet<AccCoA>(entities);
      //       entities.clear();
      //       for (AccCoA entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
