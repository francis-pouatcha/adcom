package org.adorsys.adbnsptnr.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.repo.BpIndivPtnrNameRepository;

public class BpIndivPtnrNameMerger
{

   @Inject
   private BpIndivPtnrNameRepository repository;

   public BpIndivPtnrName bindComposed(BpIndivPtnrName entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public BpIndivPtnrName bindAggregated(BpIndivPtnrName entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<BpIndivPtnrName> entities)
   {
      if (entities == null)
         return;
      HashSet<BpIndivPtnrName> oldCol = new HashSet<BpIndivPtnrName>(entities);
      entities.clear();
      for (BpIndivPtnrName entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<BpIndivPtnrName> entities)
   {
      if (entities == null)
         return;
      HashSet<BpIndivPtnrName> oldCol = new HashSet<BpIndivPtnrName>(entities);
      entities.clear();
      for (BpIndivPtnrName entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public BpIndivPtnrName unbind(final BpIndivPtnrName entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      BpIndivPtnrName newEntity = new BpIndivPtnrName();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<BpIndivPtnrName> unbind(final Set<BpIndivPtnrName> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<BpIndivPtnrName>();
      //       HashSet<BpIndivPtnrName> cache = new HashSet<BpIndivPtnrName>(entities);
      //       entities.clear();
      //       for (BpIndivPtnrName entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
