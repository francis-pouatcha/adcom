package org.adorsys.adstock.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adstock.jpa.StkArtLotSection;
import org.adorsys.adstock.repo.StkArtLotSectionRepository;

public class StkArtLotSectionMerger
{

   @Inject
   private StkArtLotSectionRepository repository;

   public StkArtLotSection bindComposed(StkArtLotSection entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public StkArtLotSection bindAggregated(StkArtLotSection entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<StkArtLotSection> entities)
   {
      if (entities == null)
         return;
      HashSet<StkArtLotSection> oldCol = new HashSet<StkArtLotSection>(entities);
      entities.clear();
      for (StkArtLotSection entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<StkArtLotSection> entities)
   {
      if (entities == null)
         return;
      HashSet<StkArtLotSection> oldCol = new HashSet<StkArtLotSection>(entities);
      entities.clear();
      for (StkArtLotSection entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public StkArtLotSection unbind(final StkArtLotSection entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      StkArtLotSection newEntity = new StkArtLotSection();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<StkArtLotSection> unbind(final Set<StkArtLotSection> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<StkArtLotSection>();
      //       HashSet<StkArtLotSection> cache = new HashSet<StkArtLotSection>(entities);
      //       entities.clear();
      //       for (StkArtLotSection entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
