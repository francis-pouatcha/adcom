package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.repo.CatalArticleRepository;

@Stateless
public class CatalArticleEJB 
{

   @Inject
   private CatalArticleRepository repository;

   public CatalArticle create(CatalArticle entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArticle deleteById(String id)
   {
      CatalArticle entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public CatalArticle update(CatalArticle entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArticle findById(String id)
   {
      return repository.findBy(id);
   }
   
   public CatalArticle findByIdentif(String identif)
   {
	   List<CatalArticle> resultList = repository.findByIdentif(identif, new Date()).maxResults(1).getResultList();
		if (resultList.isEmpty())return null;
		return resultList.iterator().next();
   }

   public List<CatalArticle> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArticle> findBy(CatalArticle entity, int start, int max, SingularAttribute<CatalArticle, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalArticle entity, SingularAttribute<CatalArticle, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArticle> findByLike(CatalArticle entity, int start, int max, SingularAttribute<CatalArticle, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalArticle entity, SingularAttribute<CatalArticle, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArticle attach(CatalArticle entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalArticle findByIdentif(String identif, Date validOn){
	   List<CatalArticle> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
