package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsProcStep;
import org.adorsys.adsales.repo.SlsProcStepRepository;

@Stateless
public class SlsProcStepEJB
{

   @Inject
   private SlsProcStepRepository repository;

   public SlsProcStep create(SlsProcStep entity)
   {
      return repository.save(attach(entity));
   }

   public SlsProcStep deleteById(String id)
   {
      SlsProcStep entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsProcStep update(SlsProcStep entity)
   {
      return repository.save(attach(entity));
   }

   public SlsProcStep findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsProcStep> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsProcStep> findBy(SlsProcStep entity, int start, int max, SingularAttribute<SlsProcStep, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsProcStep entity, SingularAttribute<SlsProcStep, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsProcStep> findByLike(SlsProcStep entity, int start, int max, SingularAttribute<SlsProcStep, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsProcStep entity, SingularAttribute<SlsProcStep, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsProcStep attach(SlsProcStep entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
