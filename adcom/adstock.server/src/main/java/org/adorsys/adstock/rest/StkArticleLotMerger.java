package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.repo.StkArticleLotRepository;

public class StkArticleLotMerger
{

   @Inject
   private StkArticleLotRepository repository;

   public StkArticleLot bindComposed(StkArticleLot entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkArticleLot bindAggregated(StkArticleLot entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkArticleLot> entities)
   {
      if (entities == null)
         return;
      HashSet<StkArticleLot> oldCol = new HashSet<StkArticleLot>(entities);
      entities.clear();
      for (StkArticleLot entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkArticleLot> entities)
   {
      if (entities == null)
         return;
      HashSet<StkArticleLot> oldCol = new HashSet<StkArticleLot>(entities);
      entities.clear();
      for (StkArticleLot entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkArticleLot unbind(final StkArticleLot entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkArticleLot newEntity = new StkArticleLot();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkArticleLot> unbind(final Set<StkArticleLot> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkArticleLot>();
      //       HashSet<StkArticleLot> cache = new HashSet<StkArticleLot>(entities);
      //       entities.clear();
      //       for (StkArticleLot entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
