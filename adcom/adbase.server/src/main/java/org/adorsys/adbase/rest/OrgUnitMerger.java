package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.repo.OrgUnitRepository;

public class OrgUnitMerger
{

   @Inject
   private OrgUnitRepository repository;

   public OrgUnit bindComposed(OrgUnit entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public OrgUnit bindAggregated(OrgUnit entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<OrgUnit> entities)
   {
      if (entities == null)
         return;
      HashSet<OrgUnit> oldCol = new HashSet<OrgUnit>(entities);
      entities.clear();
      for (OrgUnit entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<OrgUnit> entities)
   {
      if (entities == null)
         return;
      HashSet<OrgUnit> oldCol = new HashSet<OrgUnit>(entities);
      entities.clear();
      for (OrgUnit entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public OrgUnit unbind(final OrgUnit entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      OrgUnit newEntity = new OrgUnit();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<OrgUnit> unbind(final Set<OrgUnit> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<OrgUnit>();
      //       HashSet<OrgUnit> cache = new HashSet<OrgUnit>(entities);
      //       entities.clear();
      //       for (OrgUnit entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
