package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.adorsys.adbnsptnr.repo.BpBnsPtnrRepository;

@Stateless
public class BpBnsPtnrEJB
{

   @Inject
   private BpBnsPtnrRepository repository;

   public BpBnsPtnr create(BpBnsPtnr entity)
   {
      return repository.save(attach(entity));
   }

   public BpBnsPtnr deleteById(String id)
   {
      BpBnsPtnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpBnsPtnr update(BpBnsPtnr entity)
   {
      return repository.save(attach(entity));
   }

   public BpBnsPtnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpBnsPtnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpBnsPtnr> findBy(BpBnsPtnr entity, int start, int max, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpBnsPtnr entity, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpBnsPtnr> findByLike(BpBnsPtnr entity, int start, int max, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpBnsPtnr entity, SingularAttribute<BpBnsPtnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpBnsPtnr attach(BpBnsPtnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpBnsPtnr findByIdentif(String identif, Date validOn){
	   List<BpBnsPtnr> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
