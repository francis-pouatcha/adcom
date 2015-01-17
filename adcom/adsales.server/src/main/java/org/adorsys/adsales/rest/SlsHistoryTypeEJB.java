package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adsales.jpa.SlsHistoryType;
import org.adorsys.adsales.repo.SlsHistoryTypeRepository;

@Stateless
public class SlsHistoryTypeEJB
{
	
   @Inject
   private SlsHistoryTypeRepository repository;

   public SlsHistoryType create(SlsHistoryType entity)
   {
      return repository.save(attach(entity));
   }

   public SlsHistoryType deleteById(String id)
   {
      SlsHistoryType entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsHistoryType update(SlsHistoryType entity)
   {
      return repository.save(attach(entity));
   }

   public SlsHistoryType findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsHistoryType> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsHistoryType> findBy(SlsHistoryType entity, int start, int max, SingularAttribute<SlsHistoryType, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsHistoryType entity, SingularAttribute<SlsHistoryType, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsHistoryType> findByLike(SlsHistoryType entity, int start, int max, SingularAttribute<SlsHistoryType, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsHistoryType entity, SingularAttribute<SlsHistoryType, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsHistoryType attach(SlsHistoryType entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
