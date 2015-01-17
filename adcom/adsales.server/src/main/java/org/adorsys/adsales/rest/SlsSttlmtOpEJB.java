package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsSttlmtOp;
import org.adorsys.adsales.repo.SlsSttlmtOpRepository;

@Stateless
public class SlsSttlmtOpEJB
{

   @Inject
   private SlsSttlmtOpRepository repository;

   public SlsSttlmtOp create(SlsSttlmtOp entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSttlmtOp deleteById(String id)
   {
      SlsSttlmtOp entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSttlmtOp update(SlsSttlmtOp entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSttlmtOp findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSttlmtOp> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSttlmtOp> findBy(SlsSttlmtOp entity, int start, int max, SingularAttribute<SlsSttlmtOp, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSttlmtOp entity, SingularAttribute<SlsSttlmtOp, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSttlmtOp> findByLike(SlsSttlmtOp entity, int start, int max, SingularAttribute<SlsSttlmtOp, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSttlmtOp entity, SingularAttribute<SlsSttlmtOp, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsSttlmtOp attach(SlsSttlmtOp entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
