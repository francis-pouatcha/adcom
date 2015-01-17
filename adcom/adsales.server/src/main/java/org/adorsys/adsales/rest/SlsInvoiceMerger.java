package org.adorsys.adsales.rest;

import java.util.HashSet;
import java.util.Set;
import java.util.List;

import javax.inject.Inject;
import org.adorsys.adsales.jpa.SlsInvoice;
import org.adorsys.adsales.repo.SlsInvoiceRepository;

public class SlsInvoiceMerger
{

   @Inject
   private SlsInvoiceRepository repository;

   public SlsInvoice bindComposed(SlsInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return entity;
      return repository.save(entity);
   }

   public SlsInvoice bindAggregated(SlsInvoice entity)
   {
      if (entity == null)
         return null;
      if (entity.getId() == null)
         return null;
      return repository.findBy(entity.getId());
   }

   public void bindComposed(final Set<SlsInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvoice> oldCol = new HashSet<SlsInvoice>(entities);
      entities.clear();
      for (SlsInvoice entity : oldCol)
      {
         entity = bindComposed(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public void bindAggregated(final Set<SlsInvoice> entities)
   {
      if (entities == null)
         return;
      HashSet<SlsInvoice> oldCol = new HashSet<SlsInvoice>(entities);
      entities.clear();
      for (SlsInvoice entity : oldCol)
      {
         entity = bindAggregated(entity);
         if (entity != null)
            entities.add(entity);
      }
   }

   public SlsInvoice unbind(final SlsInvoice entity, List<String> fieldList)
   {
      if (entity == null)
         return null;
      SlsInvoice newEntity = new SlsInvoice();
      newEntity.setId(entity.getId());
      newEntity.setVersion(entity.getVersion());
      MergerUtils.copyFields(entity, newEntity, fieldList);
      return newEntity;
   }

   public Set<SlsInvoice> unbind(final Set<SlsInvoice> entities, List<String> fieldList)
   {
      if (entities == null)
         return null;
      return new HashSet<SlsInvoice>();
      //       HashSet<SlsInvoice> cache = new HashSet<SlsInvoice>(entities);
      //       entities.clear();
      //       for (SlsInvoice entity : cache) {
      //  		entities.add(unbind(entity, fieldList));
      //       }
      //      return entities;
   }
}
