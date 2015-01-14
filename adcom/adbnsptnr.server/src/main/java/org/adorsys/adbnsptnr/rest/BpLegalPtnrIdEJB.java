package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpLegalPtnrId;
import org.adorsys.adbnsptnr.repo.BpLegalPtnrIdRepository;

@Stateless
public class BpLegalPtnrIdEJB
{

   @Inject
   private BpLegalPtnrIdRepository repository;

   public BpLegalPtnrId create(BpLegalPtnrId entity)
   {
      return repository.save(attach(entity));
   }

   public BpLegalPtnrId deleteById(String id)
   {
      BpLegalPtnrId entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpLegalPtnrId update(BpLegalPtnrId entity)
   {
      return repository.save(attach(entity));
   }

   public BpLegalPtnrId findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpLegalPtnrId> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpLegalPtnrId> findBy(BpLegalPtnrId entity, int start, int max, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpLegalPtnrId entity, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpLegalPtnrId> findByLike(BpLegalPtnrId entity, int start, int max, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpLegalPtnrId entity, SingularAttribute<BpLegalPtnrId, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpLegalPtnrId attach(BpLegalPtnrId entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpLegalPtnrId findByIdentif(String identif, Date validOn){
	   List<BpLegalPtnrId> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
