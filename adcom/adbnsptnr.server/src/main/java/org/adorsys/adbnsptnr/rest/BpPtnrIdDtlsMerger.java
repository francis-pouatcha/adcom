package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpPtnrIdDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrIdDtlsRepository;

public class BpPtnrIdDtlsMerger
{

   @Inject
   private BpPtnrIdDtlsRepository repository;

   public BpPtnrIdDtls bindComposed(BpPtnrIdDtls entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpPtnrIdDtls bindAggregated(BpPtnrIdDtls entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpPtnrIdDtls> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrIdDtls> oldCol = new HashSet<BpPtnrIdDtls>(entities);
      entities.clear();
      for (BpPtnrIdDtls entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpPtnrIdDtls> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrIdDtls> oldCol = new HashSet<BpPtnrIdDtls>(entities);
      entities.clear();
      for (BpPtnrIdDtls entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpPtnrIdDtls unbind(final BpPtnrIdDtls entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpPtnrIdDtls newEntity = new BpPtnrIdDtls();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpPtnrIdDtls> unbind(final Set<BpPtnrIdDtls> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpPtnrIdDtls>();
      //       HashSet<BpPtnrIdDtls> cache = new HashSet<BpPtnrIdDtls>(entities);
      //       entities.clear();
      //       for (BpPtnrIdDtls entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
