package org.adorsys.adacc.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adacc.jpa.AccPstg;
import org.adorsys.adacc.repo.AccPstgRepository;

public class AccPstgMerger
{

   @Inject
   private AccPstgRepository repository;

   public AccPstg bindComposed(AccPstg entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public AccPstg bindAggregated(AccPstg entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<AccPstg> entities)
   {
      if (entities == null)
         return;
      HashSet<AccPstg> oldCol = new HashSet<AccPstg>(entities);
      entities.clear();
      for (AccPstg entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<AccPstg> entities)
   {
      if (entities == null)
         return;
      HashSet<AccPstg> oldCol = new HashSet<AccPstg>(entities);
      entities.clear();
      for (AccPstg entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public AccPstg unbind(final AccPstg entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      AccPstg newEntity = new AccPstg();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<AccPstg> unbind(final Set<AccPstg> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<AccPstg>();
      //       HashSet<AccPstg> cache = new HashSet<AccPstg>(entities);
      //       entities.clear();
      //       for (AccPstg entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
