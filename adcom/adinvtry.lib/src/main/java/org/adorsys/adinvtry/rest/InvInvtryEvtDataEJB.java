package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.adorsys.adinvtry.repo.InvInvtryEvtDataRepository;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class InvInvtryEvtDataEJB
{

   @Inject
   private InvInvtryEvtDataRepository repository;
   

   public InvInvtryEvtData create(InvInvtryEvtData entity)
   {   
      return repository.save(attach(entity));
   }

   public InvInvtryEvtData deleteById(String id)
   {
      InvInvtryEvtData entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtryEvtData update(InvInvtryEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryEvtData findById(String id)
   {
      return repository.findBy(id);
   }

   public List<InvInvtryEvtData> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InvInvtryEvtData> findBy(InvInvtryEvtData entity, int start, int max, SingularAttribute<InvInvtryEvtData, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(InvInvtryEvtData entity, SingularAttribute<InvInvtryEvtData, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<InvInvtryEvtData> findByLike(InvInvtryEvtData entity, int start, int max, SingularAttribute<InvInvtryEvtData, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(InvInvtryEvtData entity, SingularAttribute<InvInvtryEvtData, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   public InvInvtryEvtData findByIdentif(String identif) {
	   QueryResult<InvInvtryEvtData> queryResult = repository.findByIdentif(identif);
	   List<InvInvtryEvtData> results = queryResult.maxResults(1).getResultList();
	   InvInvtryEvtData invInvtry= null;
	   if(!results.isEmpty()) {
		   invInvtry = results.iterator().next();
	   }
	   return invInvtry;
   }
   private InvInvtryEvtData attach(InvInvtryEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
