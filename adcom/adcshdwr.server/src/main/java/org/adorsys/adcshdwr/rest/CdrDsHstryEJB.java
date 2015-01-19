package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrDsHstry;
import org.adorsys.adcshdwr.repo.CdrDsHstryRepository;

@Stateless
public class CdrDsHstryEJB
{

   @Inject
   private CdrDsHstryRepository repository;

   public CdrDsHstry create(CdrDsHstry entity)
   {
      return repository.save(attach(entity));
   }

   public CdrDsHstry deleteById(String id)
   {
      CdrDsHstry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrDsHstry update(CdrDsHstry entity)
   {
      return repository.save(attach(entity));
   }

   public CdrDsHstry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrDsHstry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrDsHstry> findBy(CdrDsHstry entity, int start, int max, SingularAttribute<CdrDsHstry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrDsHstry entity, SingularAttribute<CdrDsHstry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrDsHstry> findByLike(CdrDsHstry entity, int start, int max, SingularAttribute<CdrDsHstry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrDsHstry entity, SingularAttribute<CdrDsHstry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrDsHstry attach(CdrDsHstry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
