package org.adorsys.adcshdwr.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrCshDrawer;
import org.adorsys.adcshdwr.repo.CdrCshDrawerRepository;

@Stateless
public class CdrCshDrawerEJB
{

   @Inject
   private CdrCshDrawerRepository repository;

   public CdrCshDrawer create(CdrCshDrawer entity)
   {
      return repository.save(attach(entity));
   }

   public CdrCshDrawer deleteById(String id)
   {
      CdrCshDrawer entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CdrCshDrawer update(CdrCshDrawer entity)
   {
      return repository.save(attach(entity));
   }

   public CdrCshDrawer findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CdrCshDrawer> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CdrCshDrawer> findBy(CdrCshDrawer entity, int start, int max, SingularAttribute<CdrCshDrawer, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CdrCshDrawer entity, SingularAttribute<CdrCshDrawer, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CdrCshDrawer> findByLike(CdrCshDrawer entity, int start, int max, SingularAttribute<CdrCshDrawer, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CdrCshDrawer entity, SingularAttribute<CdrCshDrawer, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CdrCshDrawer attach(CdrCshDrawer entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
