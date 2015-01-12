package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

@Stateless
public class PrcmtPOItemEJB
{

   @Inject
   private PrcmtPOItemRepository repository;

   public PrcmtPOItem create(PrcmtPOItem entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPOItem deleteById(String id)
   {
      PrcmtPOItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtPOItem update(PrcmtPOItem entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtPOItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtPOItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtPOItem> findBy(PrcmtPOItem entity, int start, int max, SingularAttribute<PrcmtPOItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtPOItem entity, SingularAttribute<PrcmtPOItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtPOItem> findByLike(PrcmtPOItem entity, int start, int max, SingularAttribute<PrcmtPOItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtPOItem entity, SingularAttribute<PrcmtPOItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtPOItem attach(PrcmtPOItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
