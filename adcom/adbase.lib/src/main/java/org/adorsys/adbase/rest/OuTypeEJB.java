package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OuType;
import org.adorsys.adbase.repo.OuTypeRepository;

@Stateless
public class OuTypeEJB
{

   @Inject
   private OuTypeRepository repository;

   public OuType create(OuType entity)
   {
      return repository.save(attach(entity));
   }

   public OuType deleteById(String id)
   {
      OuType entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public OuType update(OuType entity)
   {
      return repository.save(attach(entity));
   }

   public OuType findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OuType> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OuType> findBy(OuType entity, int start, int max, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OuType entity, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OuType> findByLike(OuType entity, int start, int max, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OuType entity, SingularAttribute<OuType, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OuType attach(OuType entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OuType findByIdentif(String identif, Date validOn){
	   List<OuType> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
   public List<OuType> findActifsFrom (Date validFrom){
	   return repository.findActifsFrom(validFrom);
   }

   public List<OuType> findActifsFromNow (){
	   return repository.findActifsFrom(new Date());
   }
   
   public Long countActifsFrom(Date validFrom){
	   return repository.countActifsFrom(validFrom);
   }

   public Long countActifsFromNow(){
	   return repository.countActifsFrom(new Date());
   }
}
