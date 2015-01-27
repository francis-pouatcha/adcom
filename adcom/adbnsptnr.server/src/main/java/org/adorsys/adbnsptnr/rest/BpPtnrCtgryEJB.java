package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpPtnrCtgry;
import org.adorsys.adbnsptnr.repo.BpPtnrCtgryRepository;

@Stateless
public class BpPtnrCtgryEJB 
{

   @Inject
   private BpPtnrCtgryRepository repository;

   public BpPtnrCtgry create(BpPtnrCtgry entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrCtgry deleteById(String id)
   {
      BpPtnrCtgry entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpPtnrCtgry update(BpPtnrCtgry entity)
   {
      return repository.save(attach(entity));
   }

   public BpPtnrCtgry findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpPtnrCtgry> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpPtnrCtgry> findBy(BpPtnrCtgry entity, int start, int max, SingularAttribute<BpPtnrCtgry, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpPtnrCtgry entity, SingularAttribute<BpPtnrCtgry, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpPtnrCtgry> findByLike(BpPtnrCtgry entity, int start, int max, SingularAttribute<BpPtnrCtgry, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpPtnrCtgry entity, SingularAttribute<BpPtnrCtgry, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpPtnrCtgry attach(BpPtnrCtgry entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpPtnrCtgry findByIdentif(String identif, Date validOn){
	   List<BpPtnrCtgry> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
