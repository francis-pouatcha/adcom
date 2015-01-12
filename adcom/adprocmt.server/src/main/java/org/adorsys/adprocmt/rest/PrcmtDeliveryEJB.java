package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;

@Stateless
public class PrcmtDeliveryEJB
{

   @Inject
   private PrcmtDeliveryRepository repository;

   public PrcmtDelivery create(PrcmtDelivery entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDelivery deleteById(String id)
   {
      PrcmtDelivery entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtDelivery update(PrcmtDelivery entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDelivery findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDelivery> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDelivery> findBy(PrcmtDelivery entity, int start, int max, SingularAttribute<PrcmtDelivery, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDelivery entity, SingularAttribute<PrcmtDelivery, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDelivery> findByLike(PrcmtDelivery entity, int start, int max, SingularAttribute<PrcmtDelivery, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDelivery entity, SingularAttribute<PrcmtDelivery, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDelivery attach(PrcmtDelivery entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
