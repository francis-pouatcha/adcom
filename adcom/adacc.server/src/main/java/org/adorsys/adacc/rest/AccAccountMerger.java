package org.adorsys.adacc.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adacc.jpa.AccAccount;
import org.adorsys.adacc.repo.AccAccountRepository;

public class AccAccountMerger
{

   @Inject
   private AccAccountRepository repository;

   public AccAccount bindComposed(AccAccount entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public AccAccount bindAggregated(AccAccount entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<AccAccount> entities)
   {
      if (entities == null)
         return;
      HashSet<AccAccount> oldCol = new HashSet<AccAccount>(entities);
      entities.clear();
      for (AccAccount entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<AccAccount> entities)
   {
      if (entities == null)
         return;
      HashSet<AccAccount> oldCol = new HashSet<AccAccount>(entities);
      entities.clear();
      for (AccAccount entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public AccAccount unbind(final AccAccount entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      AccAccount newEntity = new AccAccount();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<AccAccount> unbind(final Set<AccAccount> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<AccAccount>();
      //       HashSet<AccAccount> cache = new HashSet<AccAccount>(entities);
      //       entities.clear();
      //       for (AccAccount entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
