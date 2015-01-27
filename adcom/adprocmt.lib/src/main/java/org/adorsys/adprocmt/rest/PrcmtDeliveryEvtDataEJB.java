package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.repo.PrcmtDeliveryEvtDataRepository;

@Stateless
public class PrcmtDeliveryEvtDataEJB
{

   @Inject
   private PrcmtDeliveryEvtDataRepository repository;

   public PrcmtDeliveryEvtData create(PrcmtDeliveryEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDeliveryEvtData deleteById(String id)
   {
      PrcmtDeliveryEvtData entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtDeliveryEvtData update(PrcmtDeliveryEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDeliveryEvtData findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDeliveryEvtData> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDeliveryEvtData> findBy(PrcmtDeliveryEvtData entity, int start, int max, SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDeliveryEvtData entity, SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDeliveryEvtData> findByLike(PrcmtDeliveryEvtData entity, int start, int max, SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDeliveryEvtData entity, SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDeliveryEvtData attach(PrcmtDeliveryEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
