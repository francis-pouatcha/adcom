package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrIdDtls;
import org.adorsys.adbnsptnr.repo.BpPtnrIdDtlsRepository;

@Stateless
public class BpPtnrIdDtlsEJB 
{

   @Inject
   private BpPtnrIdDtlsRepository repository;

   public BpPtnrIdDtls create(BpPtnrIdDtls entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrIdDtls deleteById(String id)
   {
      BpPtnrIdDtls entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrIdDtls update(BpPtnrIdDtls entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrIdDtls findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpPtnrIdDtls> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrIdDtls> findBy(BpPtnrIdDtls entity, int start, int max, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpPtnrIdDtls entity, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrIdDtls> findByLike(BpPtnrIdDtls entity, int start, int max, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpPtnrIdDtls entity, SingularAttribute<BpPtnrIdDtls, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrIdDtls attach(BpPtnrIdDtls entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpPtnrIdDtls findByIdentif(String identif, Date validOn){
	   List<BpPtnrIdDtls> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
