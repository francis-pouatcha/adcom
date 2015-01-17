package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsInvoiceType;
import org.adorsys.adsales.repo.SlsInvoiceTypeRepository;

@Stateless
public class SlsInvoiceTypeEJB
{

   @Inject
   private SlsInvoiceTypeRepository repository;

   public SlsInvoiceType create(SlsInvoiceType entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvoiceType deleteById(String id)
   {
      SlsInvoiceType entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsInvoiceType update(SlsInvoiceType entity)
   {
      return repository.save(attach(entity));
   }

   public SlsInvoiceType findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsInvoiceType> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsInvoiceType> findBy(SlsInvoiceType entity, int start, int max, SingularAttribute<SlsInvoiceType, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsInvoiceType entity, SingularAttribute<SlsInvoiceType, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsInvoiceType> findByLike(SlsInvoiceType entity, int start, int max, SingularAttribute<SlsInvoiceType, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsInvoiceType entity, SingularAttribute<SlsInvoiceType, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsInvoiceType attach(SlsInvoiceType entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
