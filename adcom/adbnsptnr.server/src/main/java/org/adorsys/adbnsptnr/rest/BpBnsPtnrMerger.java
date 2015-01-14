package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.repo.BpBnsPtnrRepository;

public class BpBnsPtnrMerger
{

   @Inject
   private BpBnsPtnrRepository repository;

   public BpBnsPtnr bindComposed(BpBnsPtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpBnsPtnr bindAggregated(BpBnsPtnr entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpBnsPtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<BpBnsPtnr> oldCol = new HashSet<BpBnsPtnr>(entities);
      entities.clear();
      for (BpBnsPtnr entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpBnsPtnr> entities)
   {
      if (entities == null)
         return;
      HashSet<BpBnsPtnr> oldCol = new HashSet<BpBnsPtnr>(entities);
      entities.clear();
      for (BpBnsPtnr entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpBnsPtnr unbind(final BpBnsPtnr entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpBnsPtnr newEntity = new BpBnsPtnr();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpBnsPtnr> unbind(final Set<BpBnsPtnr> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpBnsPtnr>();
      //       HashSet<BpBnsPtnr> cache = new HashSet<BpBnsPtnr>(entities);
      //       entities.clear();
      //       for (BpBnsPtnr entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
