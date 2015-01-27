package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemEvtDataRepository;

@Stateless
public class PrcmtDlvryItemEvtDataEJB
{

   @Inject
   private PrcmtDlvryItemEvtDataRepository repository;

   public PrcmtDlvryItemEvtData create(PrcmtDlvryItemEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryItemEvtData deleteById(String id)
   {
      PrcmtDlvryItemEvtData entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtDlvryItemEvtData update(PrcmtDlvryItemEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryItemEvtData findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDlvryItemEvtData> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDlvryItemEvtData> findBy(PrcmtDlvryItemEvtData entity, int start, int max, SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDlvryItemEvtData entity, SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDlvryItemEvtData> findByLike(PrcmtDlvryItemEvtData entity, int start, int max, SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDlvryItemEvtData entity, SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDlvryItemEvtData attach(PrcmtDlvryItemEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
