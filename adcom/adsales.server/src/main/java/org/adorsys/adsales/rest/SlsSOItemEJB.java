package org.adorsys.adsales.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adsales.jpa.SlsSOItem;
import org.adorsys.adsales.repo.SlsSOItemRepository;

@Stateless
public class SlsSOItemEJB
{

   @Inject
   private SlsSOItemRepository repository;

   public SlsSOItem create(SlsSOItem entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSOItem deleteById(String id)
   {
      SlsSOItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public SlsSOItem update(SlsSOItem entity)
   {
      return repository.save(attach(entity));
   }

   public SlsSOItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<SlsSOItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<SlsSOItem> findBy(SlsSOItem entity, int start, int max, SingularAttribute<SlsSOItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(SlsSOItem entity, SingularAttribute<SlsSOItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<SlsSOItem> findByLike(SlsSOItem entity, int start, int max, SingularAttribute<SlsSOItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(SlsSOItem entity, SingularAttribute<SlsSOItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private SlsSOItem attach(SlsSOItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

	public List<SlsSOItem> findSoNbr(String soNbr, int start, int max) {
		
		return repository.findBySoNbr(soNbr).firstResult(start).maxResults(max).getResultList();
	}

	public Long countBySoNbr(String soNbr) {
		return repository.findBySoNbr(soNbr).count();
	}
}
