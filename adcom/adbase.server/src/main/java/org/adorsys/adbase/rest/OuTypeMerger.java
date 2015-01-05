package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OuTypeRepository;

public class OuTypeMerger
{

   @Inject
   private OuTypeRepository repository;

   public OuType bindComposed(OuType entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public OuType bindAggregated(OuType entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<OuType> entities)
   {
      if (entities == null)
         return;
      HashSet<OuType> oldCol = new HashSet<OuType>(entities);
      entities.clear();
      for (OuType entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<OuType> entities)
   {
      if (entities == null)
         return;
      HashSet<OuType> oldCol = new HashSet<OuType>(entities);
      entities.clear();
      for (OuType entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public OuType unbind(final OuType entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      OuType newEntity = new OuType();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<OuType> unbind(final Set<OuType> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<OuType>();
      //       HashSet<OuType> cache = new HashSet<OuType>(entities);
      //       entities.clear();
      //       for (OuType entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
