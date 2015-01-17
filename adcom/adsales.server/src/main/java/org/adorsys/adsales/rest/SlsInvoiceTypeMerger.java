package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsInvoiceType;
import org.adorsys.adsales.repo.SlsInvoiceTypeRepository;

public class SlsInvoiceTypeMerger
{

   @Inject
   private SlsInvoiceTypeRepository repository;

   public SlsInvoiceType bindComposed(SlsInvoiceType entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsInvoiceType bindAggregated(SlsInvoiceType entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsInvoiceType> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvoiceType> oldCol = new HashSet<SlsInvoiceType>(entities);
      entities.clear();
      for (SlsInvoiceType entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsInvoiceType> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvoiceType> oldCol = new HashSet<SlsInvoiceType>(entities);
      entities.clear();
      for (SlsInvoiceType entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsInvoiceType unbind(final SlsInvoiceType entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsInvoiceType newEntity = new SlsInvoiceType();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsInvoiceType> unbind(final Set<SlsInvoiceType> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsInvoiceType>();
      //       HashSet<SlsInvoiceType> cache = new HashSet<SlsInvoiceType>(entities);
      //       entities.clear();
      //       for (SlsInvoiceType entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
