package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpCtgryDscnt;
import org.adorsys.adbnsptnr.repo.BpCtgryDscntRepository;

@Stateless
public class BpCtgryDscntEJB
{

   @Inject
   private BpCtgryDscntRepository repository;

   public BpCtgryDscnt create(BpCtgryDscnt entity)
   {
      return repository.save(attach(entity));
   }

   public BpCtgryDscnt deleteById(String id)
   {
      BpCtgryDscnt entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpCtgryDscnt update(BpCtgryDscnt entity)
   {
      return repository.save(attach(entity));
   }

   public BpCtgryDscnt findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpCtgryDscnt> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpCtgryDscnt> findBy(BpCtgryDscnt entity, int start, int max, SingularAttribute<BpCtgryDscnt, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpCtgryDscnt entity, SingularAttribute<BpCtgryDscnt, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpCtgryDscnt> findByLike(BpCtgryDscnt entity, int start, int max, SingularAttribute<BpCtgryDscnt, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpCtgryDscnt entity, SingularAttribute<BpCtgryDscnt, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpCtgryDscnt attach(BpCtgryDscnt entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpCtgryDscnt findByIdentif(String identif, Date validOn){
	   List<BpCtgryDscnt> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
