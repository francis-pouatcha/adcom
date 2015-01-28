package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtPOItemEvtData;
import org.adorsys.adprocmt.repo.PrcmtPOItemEvtDataRepository;

@Stateless
public class PrcmtPOItemEvtDataEJB
{

   @Inject
   private PrcmtPOItemEvtDataRepository repository;

   public PrcmtPOItemEvtData create(PrcmtPOItemEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPOItemEvtData deleteById(String id)
   {
      PrcmtPOItemEvtData entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtPOItemEvtData update(PrcmtPOItemEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPOItemEvtData findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtPOItemEvtData> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtPOItemEvtData> findBy(PrcmtPOItemEvtData entity, int start, int max, SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtPOItemEvtData entity, SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtPOItemEvtData> findByLike(PrcmtPOItemEvtData entity, int start, int max, SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtPOItemEvtData entity, SingularAttribute<PrcmtPOItemEvtData, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtPOItemEvtData attach(PrcmtPOItemEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
