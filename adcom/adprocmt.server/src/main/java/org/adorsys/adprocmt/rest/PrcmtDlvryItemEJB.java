package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemRepository;

@Stateless
public class PrcmtDlvryItemEJB
{

   @Inject
   private PrcmtDlvryItemRepository repository;

   public PrcmtDlvryItem create(PrcmtDlvryItem entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryItem deleteById(String id)
   {
      PrcmtDlvryItem entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }

   public PrcmtDlvryItem update(PrcmtDlvryItem entity)
   {
      return repository.save(attach(entity));
   }

   public PrcmtDlvryItem findById(String id)
   {
      return repository.findBy(id);
   }

   public List<PrcmtDlvryItem> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<PrcmtDlvryItem> findBy(PrcmtDlvryItem entity, int start, int max, SingularAttribute<PrcmtDlvryItem, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(PrcmtDlvryItem entity, SingularAttribute<PrcmtDlvryItem, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<PrcmtDlvryItem> findByLike(PrcmtDlvryItem entity, int start, int max, SingularAttribute<PrcmtDlvryItem, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(PrcmtDlvryItem entity, SingularAttribute<PrcmtDlvryItem, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private PrcmtDlvryItem attach(PrcmtDlvryItem entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
}
