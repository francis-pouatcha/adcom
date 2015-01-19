package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OuWorkspace;
import org.adorsys.adbase.repo.OuWorkspaceRepository;

@Stateless
public class OuWorkspaceEJB 
{

   @Inject
   private OuWorkspaceRepository repository;

   public OuWorkspace create(OuWorkspace entity)
   {
      return repository.save(attach(entity));
   }

   public OuWorkspace deleteById(String id)
   {
      OuWorkspace entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public OuWorkspace update(OuWorkspace entity)
   {
      return repository.save(attach(entity));
   }

   public OuWorkspace findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OuWorkspace> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OuWorkspace> findBy(OuWorkspace entity, int start, int max, SingularAttribute<OuWorkspace, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OuWorkspace entity, SingularAttribute<OuWorkspace, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OuWorkspace> findByLike(OuWorkspace entity, int start, int max, SingularAttribute<OuWorkspace, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OuWorkspace entity, SingularAttribute<OuWorkspace, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OuWorkspace attach(OuWorkspace entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OuWorkspace findByIdentif(String identif, Date validOn){
	   List<OuWorkspace> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
}
