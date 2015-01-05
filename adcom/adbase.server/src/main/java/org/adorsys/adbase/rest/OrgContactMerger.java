package org.adorsys.adbase.rest;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.repo.OrgContactRepository;

public class OrgContactMerger
{

   @Inject
   private OrgContactRepository repository;

   public OrgContact bindComposed(OrgContact entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public OrgContact bindAggregated(OrgContact entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<OrgContact> entities)
   {
      if (entities == null)
         return;
      HashSet<OrgContact> oldCol = new HashSet<OrgContact>(entities);
      entities.clear();
      for (OrgContact entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<OrgContact> entities)
   {
      if (entities == null)
         return;
      HashSet<OrgContact> oldCol = new HashSet<OrgContact>(entities);
      entities.clear();
      for (OrgContact entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public OrgContact unbind(final OrgContact entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      OrgContact newEntity = new OrgContact();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<OrgContact> unbind(final Set<OrgContact> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<OrgContact>();
      //       HashSet<OrgContact> cache = new HashSet<OrgContact>(entities);
      //       entities.clear();
      //       for (OrgContact entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
