package org.adorsys.adprocmt.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.adorsys.adprocmt.repo.PrcmtPOItemRepository;

@Stateless
public class PrcmtPOItemEJB
{

   @Inject
   private PrcmtPOItemRepository repository;
   @Inject
	private SecurityUtil securityUtil;

   public PrcmtPOItem create(PrcmtPOItem entity)
   {
	   	String currentLoginName = securityUtil.getCurrentLoginName();
		Date now = new Date();
		entity.setCreatingUsr(currentLoginName);
		entity.setCreatedDt(now);
		
		entity = repository.save(attach(entity));

		return entity;
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
	   entity = repository.save(attach(entity));

		return entity;
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
   
   public List<PrcmtPOItem> findByPoNbrAndArtPic(String poNbr, String artPic){
	   return repository.findByPoNbrAndArtPic(poNbr, artPic);
   }
   public List<PrcmtPOItem> findByPoNbr(String poNbr){
	   return repository.findByPoNbr(poNbr).getResultList();
   }
   public Long countByPoNbr(String poNbr){
	   return repository.findByPoNbr(poNbr).count();
   }
   public List<PrcmtPOItem> findByPoNbr(String poNbr, int start, int max){
		return repository.findByPoNbr(poNbr).firstResult(start).maxResults(max).getResultList();
	}
   public List<PrcmtPOItem> findByPoNbrAndArtPicAndRcvngOrgUnit(String poNbr, String artPic, String rcvngOrgUnit){
	   return repository.findByPoNbrAndArtPicAndRcvngOrgUnit(poNbr, artPic, rcvngOrgUnit);
   }
   public List<PrcmtPOItem> findByPoNbrAndArtPicAndStrgSection(String poNbr, String artPic, String strgSection){
	   return repository.findByPoNbrAndArtPicAndStrgSection(poNbr, artPic, strgSection);
   }
   public PrcmtPOItem findByIdentifFields(String poNbr, String artPic, String rcvngOrgUnit, String strgSection){
	   return findById(PrcmtPOItem.toId(poNbr, artPic, rcvngOrgUnit, strgSection));
   }
}
