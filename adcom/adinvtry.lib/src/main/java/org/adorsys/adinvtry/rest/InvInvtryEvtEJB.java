package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.repo.InvInvtryEvtRepository;

@Stateless
public class InvInvtryEvtEJB
{

   @Inject
   private InvInvtryEvtRepository repository;

   public InvInvtryEvt create(InvInvtryEvt entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryEvt deleteById(String id)
   {
      InvInvtryEvt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtryEvt update(InvInvtryEvt entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryEvt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<InvInvtryEvt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InvInvtryEvt> findBy(InvInvtryEvt entity, int start, int max, SingularAttribute<InvInvtryEvt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(InvInvtryEvt entity, SingularAttribute<InvInvtryEvt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<InvInvtryEvt> findByLike(InvInvtryEvt entity, int start, int max, SingularAttribute<InvInvtryEvt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(InvInvtryEvt entity, SingularAttribute<InvInvtryEvt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private InvInvtryEvt attach(InvInvtryEvt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   
   public List<InvInvtryEvt> findByLstnrNameAndEvtName(String lstnrName, String evtName){
	   return repository.findByLstnrNameAndEvtName(lstnrName, evtName);
   }
}
