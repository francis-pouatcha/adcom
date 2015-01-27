package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtProcOrderEvtData;
import org.adorsys.adprocmt.repo.PrcmtProcOrderEvtDataRepository;

@Stateless
public class PrcmtProcOrderEvtDataEJB
{

   @Inject
   private PrcmtProcOrderEvtDataRepository repository;

   public PrcmtProcOrderEvtData create(PrcmtProcOrderEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrderEvtData deleteById(String id)
   {
      PrcmtProcOrderEvtData entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtProcOrderEvtData update(PrcmtProcOrderEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtProcOrderEvtData findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtProcOrderEvtData> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtProcOrderEvtData> findBy(PrcmtProcOrderEvtData entity, int start, int max, SingularAttribute<PrcmtProcOrderEvtData, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtProcOrderEvtData entity, SingularAttribute<PrcmtProcOrderEvtData, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtProcOrderEvtData> findByLike(PrcmtProcOrderEvtData entity, int start, int max, SingularAttribute<PrcmtProcOrderEvtData, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtProcOrderEvtData entity, SingularAttribute<PrcmtProcOrderEvtData, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtProcOrderEvtData attach(PrcmtProcOrderEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
