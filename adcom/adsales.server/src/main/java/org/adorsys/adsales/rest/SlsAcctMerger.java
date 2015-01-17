package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsAcct;
import org.adorsys.adsales.repo.SlsAcctRepository;

public class SlsAcctMerger
{

   @Inject
   private SlsAcctRepository repository;

   public SlsAcct bindComposed(SlsAcct entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsAcct bindAggregated(SlsAcct entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsAcct> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsAcct> oldCol = new HashSet<SlsAcct>(entities);
      entities.clear();
      for (SlsAcct entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsAcct> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsAcct> oldCol = new HashSet<SlsAcct>(entities);
      entities.clear();
      for (SlsAcct entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsAcct unbind(final SlsAcct entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsAcct newEntity = new SlsAcct();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsAcct> unbind(final Set<SlsAcct> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsAcct>();
      //       HashSet<SlsAcct> cache = new HashSet<SlsAcct>(entities);
      //       entities.clear();
      //       for (SlsAcct entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
