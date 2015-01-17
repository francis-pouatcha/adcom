package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsRoleInSales;
import org.adorsys.adsales.repo.SlsRoleInSalesRepository;

@Stateless
public class SlsRoleInSalesEJB
{

   @Inject
   private SlsRoleInSalesRepository repository;

   public SlsRoleInSales create(SlsRoleInSales entity)
   {
      return repository.save(attach(entity));
   }

   public SlsRoleInSales deleteById(String id)
   {
      SlsRoleInSales entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsRoleInSales update(SlsRoleInSales entity)
   {
      return repository.save(attach(entity));
   }

   public SlsRoleInSales findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsRoleInSales> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsRoleInSales> findBy(SlsRoleInSales entity, int start, int max, SingularAttribute<SlsRoleInSales, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsRoleInSales entity, SingularAttribute<SlsRoleInSales, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsRoleInSales> findByLike(SlsRoleInSales entity, int start, int max, SingularAttribute<SlsRoleInSales, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsRoleInSales entity, SingularAttribute<SlsRoleInSales, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsRoleInSales attach(SlsRoleInSales entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
