package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.repo.PrcmtDeliveryEvtRepository;

@Stateless
public class PrcmtDeliveryEvtEJB
{

   @Inject
   private PrcmtDeliveryEvtRepository repository;

   public PrcmtDeliveryEvt create(PrcmtDeliveryEvt entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDeliveryEvt deleteById(String id)
   {
      PrcmtDeliveryEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtDeliveryEvt update(PrcmtDeliveryEvt entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDeliveryEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDeliveryEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDeliveryEvt> findBy(PrcmtDeliveryEvt entity, int start, int max, SingularAttribute<PrcmtDeliveryEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDeliveryEvt entity, SingularAttribute<PrcmtDeliveryEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDeliveryEvt> findByLike(PrcmtDeliveryEvt entity, int start, int max, SingularAttribute<PrcmtDeliveryEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDeliveryEvt entity, SingularAttribute<PrcmtDeliveryEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDeliveryEvt attach(PrcmtDeliveryEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public List<PrcmtDeliveryEvt> findByLstnrNameAndEvtName(String lstnrName, String evtName){
	   return repository.findByLstnrNameAndEvtName(lstnrName, evtName);
   }
}
