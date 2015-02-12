package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.repo.StkArticleLotRepository;

@Stateless
public class StkArticleLotEJB 
{

   @Inject
   private StkArticleLotRepository repository;

   public StkArticleLot create(StkArticleLot entity)
   {
      return repository.save(attach(entity));
   }

   public StkArticleLot deleteById(String id)
   {
      StkArticleLot entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkArticleLot update(StkArticleLot entity)
   {
      return repository.save(attach(entity));
   }

   public StkArticleLot findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkArticleLot> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkArticleLot> findBy(StkArticleLot entity, int start, int max, SingularAttribute<StkArticleLot, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkArticleLot entity, SingularAttribute<StkArticleLot, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkArticleLot> findByLike(StkArticleLot entity, int start, int max, SingularAttribute<StkArticleLot, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkArticleLot entity, SingularAttribute<StkArticleLot, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkArticleLot attach(StkArticleLot entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public StkArticleLot findByIdentif(String identif){
	   List<StkArticleLot> resultList = repository.findByIdentif(identif).maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   public List<StkArticleLot> findByArtPicLike(String artPick) {
	   return repository.findByArtPicLike(artPick);
   }
}
