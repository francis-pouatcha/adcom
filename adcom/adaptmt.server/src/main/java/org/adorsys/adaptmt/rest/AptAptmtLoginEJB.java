package org.adorsys.adaptmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adaptmt.jpa.AptAptmtLogin;
import org.adorsys.adaptmt.repo.AptAptmtLoginRepository;
import org.adorsys.adcore.utils.SequenceGenerator;


@Stateless
public class AptAptmtLoginEJB 
{

	   @Inject
	   private AptAptmtLoginRepository repository;

	   public AptAptmtLogin create(AptAptmtLogin entity)
	   {
		   entity.setIdentif(SequenceGenerator
					.getSequence(SequenceGenerator.APPOINTMENTLOGIN_NUMBER_SEQUENCE_PREFIXE));
		   
	      return repository.save(attach(entity));
	   }

	   public AptAptmtLogin deleteById(String id)
	   {
		   AptAptmtLogin entity = repository.findBy(id);
	      if (entity != null)
	      {
	         repository.remove(entity);
	      }
	      return entity;
	   }

	   public AptAptmtLogin update(AptAptmtLogin entity)
	   {
	      return repository.save(attach(entity));
	   }

	   public AptAptmtLogin findById(String id)
	   {
	      return repository.findBy(id);
	   }

	   public List<AptAptmtLogin> listAll(int start, int max)
	   {
	      return repository.findAll(start, max);
	   }

	   public Long count()
	   {
	      return repository.count();
	   }

	   public List<AptAptmtLogin> findBy(AptAptmtLogin entity, int start, int max, SingularAttribute<AptAptmtLogin, ?>[] attributes)
	   {
	      return repository.findBy(entity, start, max, attributes);
	   }

	   public Long countBy(AptAptmtLogin entity, SingularAttribute<AptAptmtLogin, ?>[] attributes)
	   {
	      return repository.count(entity, attributes);
	   }

	   public List<AptAptmtLogin> findByLike(AptAptmtLogin entity, int start, int max, SingularAttribute<AptAptmtLogin, ?>[] attributes)
	   {
	      return repository.findByLike(entity, start, max, attributes);
	   }

	   public Long countByLike(AptAptmtLogin entity, SingularAttribute<AptAptmtLogin, ?>[] attributes)
	   {
	      return repository.countLike(entity, attributes);
	   }

	   private AptAptmtLogin attach(AptAptmtLogin entity)
	   {
	      if (entity == null)
	         return null;

	      return entity;
	   }

}
