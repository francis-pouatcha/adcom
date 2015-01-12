package org.adorsys.adstock.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adstock.jpa.StkArtLotSection;
import org.adorsys.adstock.repo.StkArtLotSectionRepository;

@Stateless
public class StkArtLotSectionEJB
{

   @Inject
   private StkArtLotSectionRepository repository;

   public StkArtLotSection create(StkArtLotSection entity)
   {
      return repository.save(attach(entity));
   }

   public StkArtLotSection deleteById(String id)
   {
      StkArtLotSection entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public StkArtLotSection update(StkArtLotSection entity)
   {
      return repository.save(attach(entity));
   }

   public StkArtLotSection findById(String id)
   {
      return repository.findBy(id);
   }

   public List<StkArtLotSection> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<StkArtLotSection> findBy(StkArtLotSection entity, int start, int max, SingularAttribute<StkArtLotSection, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(StkArtLotSection entity, SingularAttribute<StkArtLotSection, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<StkArtLotSection> findByLike(StkArtLotSection entity, int start, int max, SingularAttribute<StkArtLotSection, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(StkArtLotSection entity, SingularAttribute<StkArtLotSection, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private StkArtLotSection attach(StkArtLotSection entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public StkArtLotSection findByIdentif(String identif, Date validOn){
	   List<StkArtLotSection> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
