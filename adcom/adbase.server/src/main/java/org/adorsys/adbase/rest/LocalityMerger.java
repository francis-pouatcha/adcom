package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.Locality;
import org.adorsys.adbase.repo.LocalityRepository;

public class LocalityMerger
{

   @Inject
   private LocalityRepository repository;

   public Locality bindComposed(Locality entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Locality bindAggregated(Locality entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Locality> entities)
   {
      if (entities == null)
         return;
      HashSet<Locality> oldCol = new HashSet<Locality>(entities);
      entities.clear();
      for (Locality entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Locality> entities)
   {
      if (entities == null)
         return;
      HashSet<Locality> oldCol = new HashSet<Locality>(entities);
      entities.clear();
      for (Locality entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Locality unbind(final Locality entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Locality newEntity = new Locality();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Locality> unbind(final Set<Locality> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Locality>();
      //       HashSet<Locality> cache = new HashSet<Locality>(entities);
      //       entities.clear();
      //       for (Locality entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
