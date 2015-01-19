package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrCstmrVchr;
import org.adorsys.adcshdwr.repo.CdrCstmrVchrRepository;

@Stateless
public class CdrCstmrVchrEJB
{

   @Inject
   private CdrCstmrVchrRepository repository;

   public CdrCstmrVchr create(CdrCstmrVchr entity)
   {
      return repository.save(attach(entity));
   }

   public CdrCstmrVchr deleteById(String id)
   {
      CdrCstmrVchr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrCstmrVchr update(CdrCstmrVchr entity)
   {
      return repository.save(attach(entity));
   }

   public CdrCstmrVchr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrCstmrVchr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrCstmrVchr> findBy(CdrCstmrVchr entity, int start, int max, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrCstmrVchr entity, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrCstmrVchr> findByLike(CdrCstmrVchr entity, int start, int max, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrCstmrVchr entity, SingularAttribute<CdrCstmrVchr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrCstmrVchr attach(CdrCstmrVchr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
