package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtPrcmtEvtLstnr;
import org.adorsys.adprocmt.repo.PrcmtPrcmtEvtLstnrRepository;

@Stateless
public class PrcmtPrcmtEvtLstnrEJB
{

   @Inject
   private PrcmtPrcmtEvtLstnrRepository repository;

   public PrcmtPrcmtEvtLstnr create(PrcmtPrcmtEvtLstnr entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPrcmtEvtLstnr deleteById(String id)
   {
      PrcmtPrcmtEvtLstnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtPrcmtEvtLstnr update(PrcmtPrcmtEvtLstnr entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPrcmtEvtLstnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtPrcmtEvtLstnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtPrcmtEvtLstnr> findBy(PrcmtPrcmtEvtLstnr entity, int start, int max, SingularAttribute<PrcmtPrcmtEvtLstnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtPrcmtEvtLstnr entity, SingularAttribute<PrcmtPrcmtEvtLstnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtPrcmtEvtLstnr> findByLike(PrcmtPrcmtEvtLstnr entity, int start, int max, SingularAttribute<PrcmtPrcmtEvtLstnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtPrcmtEvtLstnr entity, SingularAttribute<PrcmtPrcmtEvtLstnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtPrcmtEvtLstnr attach(PrcmtPrcmtEvtLstnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
