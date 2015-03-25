package org.adorsys.adaptmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmtBsPtnr;
import org.adorsys.adaptmt.repo.AptAptmtBsPtnrRepository;


@Stateless
public class AptAptmtBsPtnrEJB 
{

	   @Inject
	   private AptAptmtBsPtnrRepository repository;

	   public AptAptmtBsPtnr create(AptAptmtBsPtnr entity)
	   {
	      return repository.save(attach(entity));
	   }

	   public AptAptmtBsPtnr deleteById(String id)
	   {
		   AptAptmtBsPtnr entity = repository.findBy(id);
	      if (entity != null)
	      {
	         repository.remove(entity);
	      }
	      return entity;
	   }

	   public AptAptmtBsPtnr update(AptAptmtBsPtnr entity)
	   {
	      return repository.save(attach(entity));
	   }

	   public AptAptmtBsPtnr findById(String id)
	   {
	      return repository.findBy(id);
	   }

	   public List<AptAptmtBsPtnr> listAll(int start, int max)
	   {
	      return repository.findAll(start, max);
	   }

	   public Long count()
	   {
	      return repository.count();
	   }

	   public List<AptAptmtBsPtnr> findBy(AptAptmtBsPtnr entity, int start, int max, SingularAttribute<AptAptmtBsPtnr, ?>[] attributes)
	   {
	      return repository.findBy(entity, start, max, attributes);
	   }

	   public Long countBy(AptAptmtBsPtnr entity, SingularAttribute<AptAptmtBsPtnr, ?>[] attributes)
	   {
	      return repository.count(entity, attributes);
	   }

	   public List<AptAptmtBsPtnr> findByLike(AptAptmtBsPtnr entity, int start, int max, SingularAttribute<AptAptmtBsPtnr, ?>[] attributes)
	   {
	      return repository.findByLike(entity, start, max, attributes);
	   }

	   public Long countByLike(AptAptmtBsPtnr entity, SingularAttribute<AptAptmtBsPtnr, ?>[] attributes)
	   {
	      return repository.countLike(entity, attributes);
	   }

	   private AptAptmtBsPtnr attach(AptAptmtBsPtnr entity)
	   {
	      if (entity == null)
	         return null;

	      return entity;
	   }

}
