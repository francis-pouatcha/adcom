package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.repo.OrgContactRepository;

@Stateless
public class OrgContactEJB
{

   @Inject
   private OrgContactRepository repository;

   public OrgContact create(OrgContact entity)
   {
      return repository.save(attach(entity));
   }

   public OrgContact deleteById(String id)
   {
      OrgContact entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public OrgContact update(OrgContact entity)
   {
      return repository.save(attach(entity));
   }

   public OrgContact findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OrgContact> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OrgContact> findBy(OrgContact entity, int start, int max, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OrgContact entity, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OrgContact> findByLike(OrgContact entity, int start, int max, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OrgContact entity, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OrgContact attach(OrgContact entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OrgContact findByIdentif(String identif, Date validOn){
	   List<OrgContact> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
