package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.Country;
import org.adorsys.adbase.repo.CountryRepository;

public class CountryMerger
{

   @Inject
   private CountryRepository repository;

   public Country bindComposed(Country entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public Country bindAggregated(Country entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<Country> entities)
   {
      if (entities == null)
         return;
      HashSet<Country> oldCol = new HashSet<Country>(entities);
      entities.clear();
      for (Country entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<Country> entities)
   {
      if (entities == null)
         return;
      HashSet<Country> oldCol = new HashSet<Country>(entities);
      entities.clear();
      for (Country entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public Country unbind(final Country entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      Country newEntity = new Country();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<Country> unbind(final Set<Country> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<Country>();
      //       HashSet<Country> cache = new HashSet<Country>(entities);
      //       entities.clear();
      //       for (Country entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
