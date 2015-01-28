package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDlvryEvtLstnr;
import org.adorsys.adprocmt.repo.PrcmtDlvryEvtLstnrRepository;

@Stateless
public class PrcmtDlvryEvtLstnrEJB
{

   @Inject
   private PrcmtDlvryEvtLstnrRepository repository;

   public PrcmtDlvryEvtLstnr create(PrcmtDlvryEvtLstnr entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryEvtLstnr deleteById(String id)
   {
      PrcmtDlvryEvtLstnr entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtDlvryEvtLstnr update(PrcmtDlvryEvtLstnr entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryEvtLstnr findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDlvryEvtLstnr> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDlvryEvtLstnr> findBy(PrcmtDlvryEvtLstnr entity, int start, int max, SingularAttribute<PrcmtDlvryEvtLstnr, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDlvryEvtLstnr entity, SingularAttribute<PrcmtDlvryEvtLstnr, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDlvryEvtLstnr> findByLike(PrcmtDlvryEvtLstnr entity, int start, int max, SingularAttribute<PrcmtDlvryEvtLstnr, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDlvryEvtLstnr entity, SingularAttribute<PrcmtDlvryEvtLstnr, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDlvryEvtLstnr attach(PrcmtDlvryEvtLstnr entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
	public List<PrcmtDlvryEvtLstnr> findByEvtName(String evtName){
		return repository.findByEvtName(evtName);
	}   
}
