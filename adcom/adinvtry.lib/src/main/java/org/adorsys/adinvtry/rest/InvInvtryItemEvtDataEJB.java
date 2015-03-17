package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.adorsys.adinvtry.repo.InvInvtryItemEvtDataRepository;

@Stateless
public class InvInvtryItemEvtDataEJB
{

   @Inject
   private InvInvtryItemEvtDataRepository repository;

   public InvInvtryItemEvtData create(InvInvtryItemEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryItemEvtData deleteById(String id)
   {
      InvInvtryItemEvtData entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public InvInvtryItemEvtData update(InvInvtryItemEvtData entity)
   {
      return repository.save(attach(entity));
   }

   public InvInvtryItemEvtData findById(String id)
   {
      return repository.findBy(id);
   }

   public List<InvInvtryItemEvtData> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<InvInvtryItemEvtData> findBy(InvInvtryItemEvtData entity, int start, int max, SingularAttribute<InvInvtryItemEvtData, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(InvInvtryItemEvtData entity, SingularAttribute<InvInvtryItemEvtData, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<InvInvtryItemEvtData> findByLike(InvInvtryItemEvtData entity, int start, int max, SingularAttribute<InvInvtryItemEvtData, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(InvInvtryItemEvtData entity, SingularAttribute<InvInvtryItemEvtData, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private InvInvtryItemEvtData attach(InvInvtryItemEvtData entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

	public Long countByInvtryNbr(String invtryNbr) {
		return repository.countByInvtryNbr(invtryNbr);
	}

	public List<InvInvtryItemEvtData> findByInvtryNbr(String invtryNbr,
			int start, int max) {
		return repository.findByInvtryNbr(invtryNbr).firstResult(start)
				.maxResults(max).getResultList();
	}

	public List<String> findIdByInvtryNbr(String invtryNbr,
			int start, int max) {
		return repository.findIdByInvtryNbr(invtryNbr).firstResult(start)
				.maxResults(max).getResultList();
	}

	public void deleteByInvtryNbr(String invtryNbr, int max) {
		List<String> invtryNbrs = findIdByInvtryNbr(invtryNbr, 0, max);
		for (String invtryItemId : invtryNbrs) {
			deleteById(invtryItemId);
		}
	}
}
