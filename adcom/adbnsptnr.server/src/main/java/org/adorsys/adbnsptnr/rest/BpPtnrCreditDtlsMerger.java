package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpPtnrCreditDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrCreditDtlsRepository;

public class BpPtnrCreditDtlsMerger
{

   @Inject
   private BpPtnrCreditDtlsRepository repository;

   public BpPtnrCreditDtls bindComposed(BpPtnrCreditDtls entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpPtnrCreditDtls bindAggregated(BpPtnrCreditDtls entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpPtnrCreditDtls> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrCreditDtls> oldCol = new HashSet<BpPtnrCreditDtls>(entities);
      entities.clear();
      for (BpPtnrCreditDtls entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpPtnrCreditDtls> entities)
   {
      if (entities == null)
         return;
      HashSet<BpPtnrCreditDtls> oldCol = new HashSet<BpPtnrCreditDtls>(entities);
      entities.clear();
      for (BpPtnrCreditDtls entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpPtnrCreditDtls unbind(final BpPtnrCreditDtls entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpPtnrCreditDtls newEntity = new BpPtnrCreditDtls();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpPtnrCreditDtls> unbind(final Set<BpPtnrCreditDtls> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpPtnrCreditDtls>();
      //       HashSet<BpPtnrCreditDtls> cache = new HashSet<BpPtnrCreditDtls>(entities);
      //       entities.clear();
      //       for (BpPtnrCreditDtls entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
