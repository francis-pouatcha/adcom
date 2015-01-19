package org.adorsys.adbnsptnr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbnsptnr.jpa.BpIndivPtnrName;
import org.adorsys.adbnsptnr.repo.BpIndivPtnrNameRepository;

@Stateless
public class BpIndivPtnrNameEJB 
{

   @Inject
   private BpIndivPtnrNameRepository repository;

   public BpIndivPtnrName create(BpIndivPtnrName entity)
   {
      return repository.save(attach(entity));
   }

   public BpIndivPtnrName deleteById(String id)
   {
      BpIndivPtnrName entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public BpIndivPtnrName update(BpIndivPtnrName entity)
   {
      return repository.save(attach(entity));
   }

   public BpIndivPtnrName findById(String id)
   {
      return repository.findBy(id);
   }

   public List<BpIndivPtnrName> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<BpIndivPtnrName> findBy(BpIndivPtnrName entity, int start, int max, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(BpIndivPtnrName entity, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<BpIndivPtnrName> findByLike(BpIndivPtnrName entity, int start, int max, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(BpIndivPtnrName entity, SingularAttribute<BpIndivPtnrName, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private BpIndivPtnrName attach(BpIndivPtnrName entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public BpIndivPtnrName findByIdentif(String identif, Date validOn){
	   List<BpIndivPtnrName> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
