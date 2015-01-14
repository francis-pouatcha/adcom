package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpInsurrance;
import org.adorsys.adbnsptnr.repo.BpInsurranceRepository;

public class BpInsurranceMerger
{

   @Inject
   private BpInsurranceRepository repository;

   public BpInsurrance bindComposed(BpInsurrance entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpInsurrance bindAggregated(BpInsurrance entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpInsurrance> entities)
   {
      if (entities == null)
         return;
      HashSet<BpInsurrance> oldCol = new HashSet<BpInsurrance>(entities);
      entities.clear();
      for (BpInsurrance entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpInsurrance> entities)
   {
      if (entities == null)
         return;
      HashSet<BpInsurrance> oldCol = new HashSet<BpInsurrance>(entities);
      entities.clear();
      for (BpInsurrance entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpInsurrance unbind(final BpInsurrance entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpInsurrance newEntity = new BpInsurrance();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpInsurrance> unbind(final Set<BpInsurrance> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpInsurrance>();
      //       HashSet<Insurrance> cache = new HashSet<Insurrance>(entities);
      //       entities.clear();
      //       for (Insurrance entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
