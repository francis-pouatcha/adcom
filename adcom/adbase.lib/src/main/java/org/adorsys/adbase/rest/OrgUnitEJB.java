package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OrgUnit;
import org.adorsys.adbase.repo.OrgUnitRepository;

@Stateless
public class OrgUnitEJB
{

   @Inject
   private OrgUnitRepository repository;

   
   public OrgUnit create(OrgUnit entity)
   {
      return repository.save(attach(entity));
   }

   public OrgUnit deleteById(String id)
   {
      OrgUnit entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public OrgUnit update(OrgUnit entity)
   {
      return repository.save(attach(entity));
   }

   public OrgUnit findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OrgUnit> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OrgUnit> findBy(OrgUnit entity, int start, int max, SingularAttribute<OrgUnit, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OrgUnit entity, SingularAttribute<OrgUnit, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OrgUnit> findByLike(OrgUnit entity, int start, int max, SingularAttribute<OrgUnit, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OrgUnit entity, SingularAttribute<OrgUnit, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OrgUnit attach(OrgUnit entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OrgUnit findByIdentif(String identif, Date validOn){
	   List<OrgUnit> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
}
