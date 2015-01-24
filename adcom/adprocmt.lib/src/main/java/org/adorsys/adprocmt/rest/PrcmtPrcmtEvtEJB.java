package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtPrcmtEvt;
import org.adorsys.adprocmt.repo.PrcmtPrcmtEvtRepository;

@Stateless
public class PrcmtPrcmtEvtEJB
{

   @Inject
   private PrcmtPrcmtEvtRepository repository;

   public PrcmtPrcmtEvt create(PrcmtPrcmtEvt entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPrcmtEvt deleteById(String id)
   {
      PrcmtPrcmtEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtPrcmtEvt update(PrcmtPrcmtEvt entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPrcmtEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtPrcmtEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtPrcmtEvt> findBy(PrcmtPrcmtEvt entity, int start, int max, SingularAttribute<PrcmtPrcmtEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtPrcmtEvt entity, SingularAttribute<PrcmtPrcmtEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtPrcmtEvt> findByLike(PrcmtPrcmtEvt entity, int start, int max, SingularAttribute<PrcmtPrcmtEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtPrcmtEvt entity, SingularAttribute<PrcmtPrcmtEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtPrcmtEvt attach(PrcmtPrcmtEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
