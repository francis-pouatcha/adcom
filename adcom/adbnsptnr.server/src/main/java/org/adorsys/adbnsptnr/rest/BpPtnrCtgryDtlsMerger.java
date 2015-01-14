package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpPtnrCtgryDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrCtgryDtlsRepository;

public class BpPtnrCtgryDtlsMerger
{

   @Inject
   private BpPtnrCtgryDtlsRepository repository;

   public BpPtnrCtgryDtls bindComposed(BpPtnrCtgryDtls entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpPtnrCtgryDtls bindAggregated(BpPtnrCtgryDtls entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpPtnrCtgryDtls> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrCtgryDtls> oldCol = new HashSet<BpPtnrCtgryDtls>(entities);
      entities.clear();
      for (BpPtnrCtgryDtls entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpPtnrCtgryDtls> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrCtgryDtls> oldCol = new HashSet<BpPtnrCtgryDtls>(entities);
      entities.clear();
      for (BpPtnrCtgryDtls entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpPtnrCtgryDtls unbind(final BpPtnrCtgryDtls entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpPtnrCtgryDtls newEntity = new BpPtnrCtgryDtls();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpPtnrCtgryDtls> unbind(final Set<BpPtnrCtgryDtls> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpPtnrCtgryDtls>();
      //       HashSet<BpPtnrCtgryDtls> cache = new HashSet<BpPtnrCtgryDtls>(entities);
      //       entities.clear();
      //       for (BpPtnrCtgryDtls entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
