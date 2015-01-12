package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.adorsys.adprocmt.repo.PrcmtProcOrderRepository;

@Stateless
public class PrcmtProcOrderEJB
{

   @Inject
   private PrcmtProcOrderRepository repository;

   public PrcmtProcOrder create(PrcmtProcOrder entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrder deleteById(String id)
   {
      PrcmtProcOrder entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtProcOrder update(PrcmtProcOrder entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrder findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtProcOrder> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtProcOrder> findBy(PrcmtProcOrder entity, int start, int max, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtProcOrder entity, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtProcOrder> findByLike(PrcmtProcOrder entity, int start, int max, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtProcOrder entity, SingularAttribute<PrcmtProcOrder, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtProcOrder attach(PrcmtProcOrder entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
