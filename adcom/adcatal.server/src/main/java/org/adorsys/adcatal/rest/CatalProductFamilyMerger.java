package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.repo.CatalProductFamilyRepository;

public class CatalProductFamilyMerger
{

   @Inject
   private CatalProductFamilyRepository repository;

   public CatalProductFamily bindComposed(CatalProductFamily entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalProductFamily bindAggregated(CatalProductFamily entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalProductFamily> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalProductFamily> oldCol = new HashSet<CatalProductFamily>(entities);
      entities.clear();
      for (CatalProductFamily entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalProductFamily> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalProductFamily> oldCol = new HashSet<CatalProductFamily>(entities);
      entities.clear();
      for (CatalProductFamily entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalProductFamily unbind(final CatalProductFamily entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalProductFamily newEntity = new CatalProductFamily();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalProductFamily> unbind(final Set<CatalProductFamily> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalProductFamily>();
      //       HashSet<CatalProductFamily> cache = new HashSet<CatalProductFamily>(entities);
      //       entities.clear();
      //       for (CatalProductFamily entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
