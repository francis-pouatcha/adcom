package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryEvtLstnr;
import org.adorsys.adinvtry.repo.InvInvtryEvtLstnrRepository;

@Stateless
public class InvInvtryEvtLstnrEJB
{

   @Inject
   private InvInvtryEvtLstnrRepository repository;

   public InvInvtryEvtLstnr create(InvInvtryEvtLstnr entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryEvtLstnr deleteById(String id)
   {
      InvInvtryEvtLstnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtryEvtLstnr update(InvInvtryEvtLstnr entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryEvtLstnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<InvInvtryEvtLstnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InvInvtryEvtLstnr> findBy(InvInvtryEvtLstnr entity, int start, int max, SingularAttribute<InvInvtryEvtLstnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(InvInvtryEvtLstnr entity, SingularAttribute<InvInvtryEvtLstnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<InvInvtryEvtLstnr> findByLike(InvInvtryEvtLstnr entity, int start, int max, SingularAttribute<InvInvtryEvtLstnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(InvInvtryEvtLstnr entity, SingularAttribute<InvInvtryEvtLstnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private InvInvtryEvtLstnr attach(InvInvtryEvtLstnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

	public List<InvInvtryEvtLstnr> findByEvtName(String evtName) {
		return repository.findByEvtName(evtName);
	}
}
