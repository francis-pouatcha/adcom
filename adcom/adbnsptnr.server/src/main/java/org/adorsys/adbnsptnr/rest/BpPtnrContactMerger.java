package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpPtnrContact;
import org.adorsys.adbnsptnr.repo.BpPtnrContactRepository;

public class BpPtnrContactMerger
{

   @Inject
   private BpPtnrContactRepository repository;

   public BpPtnrContact bindComposed(BpPtnrContact entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpPtnrContact bindAggregated(BpPtnrContact entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpPtnrContact> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrContact> oldCol = new HashSet<BpPtnrContact>(entities);
      entities.clear();
      for (BpPtnrContact entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpPtnrContact> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrContact> oldCol = new HashSet<BpPtnrContact>(entities);
      entities.clear();
      for (BpPtnrContact entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpPtnrContact unbind(final BpPtnrContact entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpPtnrContact newEntity = new BpPtnrContact();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpPtnrContact> unbind(final Set<BpPtnrContact> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpPtnrContact>();
      //       HashSet<BpPtnrContact> cache = new HashSet<BpPtnrContact>(entities);
      //       entities.clear();
      //       for (BpPtnrContact entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
