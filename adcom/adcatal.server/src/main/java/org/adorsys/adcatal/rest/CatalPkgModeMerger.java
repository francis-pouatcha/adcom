package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.adorsys.adcatal.repo.CatalPkgModeRepository;

public class CatalPkgModeMerger
{

   @Inject
   private CatalPkgModeRepository repository;

   public CatalPkgMode bindComposed(CatalPkgMode entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalPkgMode bindAggregated(CatalPkgMode entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalPkgMode> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalPkgMode> oldCol = new HashSet<CatalPkgMode>(entities);
      entities.clear();
      for (CatalPkgMode entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalPkgMode> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalPkgMode> oldCol = new HashSet<CatalPkgMode>(entities);
      entities.clear();
      for (CatalPkgMode entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalPkgMode unbind(final CatalPkgMode entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalPkgMode newEntity = new CatalPkgMode();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalPkgMode> unbind(final Set<CatalPkgMode> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalPkgMode>();
      //       HashSet<CatalPkgMode> cache = new HashSet<CatalPkgMode>(entities);
      //       entities.clear();
      //       for (CatalPkgMode entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
