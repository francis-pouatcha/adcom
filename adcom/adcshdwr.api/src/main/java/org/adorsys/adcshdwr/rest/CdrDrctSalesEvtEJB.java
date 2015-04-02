package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvt;
import org.adorsys.adcshdwr.repo.CdrDrctSalesEvtRepository;

@Stateless
public class CdrDrctSalesEvtEJB
{

   @Inject
   private CdrDrctSalesEvtRepository repository;

   public CdrDrctSalesEvt create(CdrDrctSalesEvt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrDrctSalesEvt deleteById(String id)
   {
	   CdrDrctSalesEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrDrctSalesEvt update(CdrDrctSalesEvt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrDrctSalesEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrDrctSalesEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrDrctSalesEvt> findBy(CdrDrctSalesEvt entity, int start, int max, SingularAttribute<CdrDrctSalesEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrDrctSalesEvt entity, SingularAttribute<CdrDrctSalesEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrDrctSalesEvt> findByLike(CdrDrctSalesEvt entity, int start, int max, SingularAttribute<CdrDrctSalesEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrDrctSalesEvt entity, SingularAttribute<CdrDrctSalesEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrDrctSalesEvt attach(CdrDrctSalesEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
