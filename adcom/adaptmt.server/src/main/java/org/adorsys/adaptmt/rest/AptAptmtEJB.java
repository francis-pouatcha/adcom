package org.adorsys.adaptmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmt;
import org.adorsys.adaptmt.repo.AptAptmtRepository;


@Stateless
public class AptAptmtEJB 
{

	   @Inject
	   private AptAptmtRepository repository;

	   public AptAptmt create(AptAptmt entity)
	   {
	      return repository.save(attach(entity));
	   }

	   public AptAptmt deleteById(String id)
	   {
		   AptAptmt entity = repository.findBy(id);
	      if (entity != null)
	      {
	         repository.remove(entity);
	      }
	      return entity;
	   }

	   public AptAptmt update(AptAptmt entity)
	   {
	      return repository.save(attach(entity));
	   }

	   public AptAptmt findById(String id)
	   {
	      return repository.findBy(id);
	   }

	   public List<AptAptmt> listAll(int start, int max)
	   {
	      return repository.findAll(start, max);
	   }

	   public Long count()
	   {
	      return repository.count();
	   }

	   public List<AptAptmt> findBy(AptAptmt entity, int start, int max, SingularAttribute<AptAptmt, ?>[] attributes)
	   {
	      return repository.findBy(entity, start, max, attributes);
	   }

	   public Long countBy(AptAptmt entity, SingularAttribute<AptAptmt, ?>[] attributes)
	   {
	      return repository.count(entity, attributes);
	   }

	   public List<AptAptmt> findByLike(AptAptmt entity, int start, int max, SingularAttribute<AptAptmt, ?>[] attributes)
	   {
	      return repository.findByLike(entity, start, max, attributes);
	   }

	   public Long countByLike(AptAptmt entity, SingularAttribute<AptAptmt, ?>[] attributes)
	   {
	      return repository.countLike(entity, attributes);
	   }

	   private AptAptmt attach(AptAptmt entity)
	   {
	      if (entity == null)
	         return null;

	      return entity;
	   }

}
