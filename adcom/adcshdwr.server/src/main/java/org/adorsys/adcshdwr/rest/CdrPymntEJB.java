package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrPymnt;
import org.adorsys.adcshdwr.repo.CdrPymntRepository;

@Stateless
public class CdrPymntEJB
{

   @Inject
   private CdrPymntRepository repository;

   public CdrPymnt create(CdrPymnt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymnt deleteById(String id)
   {
      CdrPymnt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrPymnt update(CdrPymnt entity)
   {
      return repository.save(attach(entity));
   }

   public CdrPymnt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrPymnt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrPymnt> findBy(CdrPymnt entity, int start, int max, SingularAttribute<CdrPymnt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrPymnt entity, SingularAttribute<CdrPymnt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrPymnt> findByLike(CdrPymnt entity, int start, int max, SingularAttribute<CdrPymnt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrPymnt entity, SingularAttribute<CdrPymnt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrPymnt attach(CdrPymnt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
