package org.adorsys.adcatal.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.repo.CatalArticleRepository;

public class CatalArticleMerger
{

   @Inject
   private CatalArticleRepository repository;

   public CatalArticle bindComposed(CatalArticle entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public CatalArticle bindAggregated(CatalArticle entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<CatalArticle> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArticle> oldCol = new HashSet<CatalArticle>(entities);
      entities.clear();
      for (CatalArticle entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<CatalArticle> entities)
   {
      if (entities == null)
         return;
      HashSet<CatalArticle> oldCol = new HashSet<CatalArticle>(entities);
      entities.clear();
      for (CatalArticle entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public CatalArticle unbind(final CatalArticle entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      CatalArticle newEntity = new CatalArticle();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<CatalArticle> unbind(final Set<CatalArticle> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<CatalArticle>();
      //       HashSet<CatalArticle> cache = new HashSet<CatalArticle>(entities);
      //       entities.clear();
      //       for (CatalArticle entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
