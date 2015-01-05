package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.SecTermRegist;
import org.adorsys.adbase.repo.SecTermRegistRepository;

@Stateless
public class SecTermRegistEJB
{

   @Inject
   private SecTermRegistRepository repository;

   public SecTermRegist create(SecTermRegist entity)
   {
      return repository.save(attach(entity));
   }

   public SecTermRegist deleteById(String id)
   {
      SecTermRegist entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SecTermRegist update(SecTermRegist entity)
   {
      return repository.save(attach(entity));
   }

   public SecTermRegist findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SecTermRegist> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SecTermRegist> findBy(SecTermRegist entity, int start, int max, SingularAttribute<SecTermRegist, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SecTermRegist entity, SingularAttribute<SecTermRegist, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SecTermRegist> findByLike(SecTermRegist entity, int start, int max, SingularAttribute<SecTermRegist, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SecTermRegist entity, SingularAttribute<SecTermRegist, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SecTermRegist attach(SecTermRegist entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public SecTermRegist findByIdentif(String identif, Date validOn){
	   List<SecTermRegist> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
