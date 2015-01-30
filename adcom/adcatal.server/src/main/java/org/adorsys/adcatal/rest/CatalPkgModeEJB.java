package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.adorsys.adcatal.jpa.CatalPkgModeSearchInput;
import org.adorsys.adcatal.jpa.CatalPkgModeSearchResult;
import org.adorsys.adcatal.repo.CatalPkgModeRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalPkgModeEJB
{

   @Inject
   private CatalPkgModeRepository repository;
   
   @Inject
   private EntityManager em;

   public CatalPkgMode create(CatalPkgMode entity)
   {
      return repository.save(attach(entity));
   }

   public CatalPkgMode deleteById(String id)
   {
      CatalPkgMode entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }
   
   public CatalPkgMode deleteCustomById(String id)
   {
	   CatalPkgMode entity = repository.findBy(id);
	   if (entity != null)
	   {
		   entity.setValidTo(new Date());
		   repository.save(entity);
	   }
	   return entity;
   }

   public CatalPkgMode update(CatalPkgMode entity)
   {
      return repository.save(attach(entity));
   }

   public CatalPkgMode findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalPkgMode> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalPkgMode> findBy(CatalPkgMode entity, int start, int max, SingularAttribute<CatalPkgMode, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalPkgMode entity, SingularAttribute<CatalPkgMode, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalPkgMode> findByLike(CatalPkgMode entity, int start, int max, SingularAttribute<CatalPkgMode, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalPkgMode entity, SingularAttribute<CatalPkgMode, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalPkgMode attach(CatalPkgMode entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalPkgMode findByIdentif(String identif, Date validOn){
	   List<CatalPkgMode> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
   public List<CatalPkgMode> searchCatalPkgModes(CatalPkgModeSearchInput searchInput) {
	   CatalPkgMode entity = searchInput.getEntity();
	   int start = searchInput.getStart();
	   int max = searchInput.getMax();
	   String pkgCode = entity.getPkgCode();
	   String name = entity.getName();
	   Date validFrom = entity.getValidFrom();
	   if(validFrom == null) validFrom = new Date();
	   StringBuilder qBuilder = new StringBuilder("SELECT e FROM CatalPkgMode AS e WHERE e.validFrom <= :validFrom AND (e.validTo IS NULL OR e.validTo > :validFrom)");
	   boolean isPkgCode = false;
	   boolean isName = false;
	   if(StringUtils.isNotBlank(pkgCode)) {
		   qBuilder.append(" AND LOWER(e.pkgCode) LIKE ( LOWER(:pkgCode))");
		   isPkgCode = true;
	   }
	   if(StringUtils.isNotBlank(name)) {
		   qBuilder.append(" AND LOWER(e.name) LIKE ( LOWER(:name))");
		   isName = true;
	   }
	   TypedQuery<CatalPkgMode> query = em.createQuery(qBuilder.toString(), CatalPkgMode.class);
	   query.setParameter("validFrom", validFrom);
	   if(isPkgCode) 
		   query.setParameter("pkgCode", pkgCode+"%");
	   if(isName)
		   query.setParameter("name", name+"%");
	   
	   if(start < 0) start = 0;
		  query.setFirstResult(start);
	   if(max >= 0) {
		   query.setMaxResults(max);
	   }
	   return query.getResultList();
   }
   
   public Long countCatalPkgModes(CatalPkgModeSearchInput searchInput) {
	   CatalPkgMode entity = searchInput.getEntity();
	   String pkgCode = entity.getPkgCode();
	   String name = entity.getName();
	   Date validFrom = entity.getValidFrom();
	   if(validFrom == null) validFrom = new Date();
	   StringBuilder qBuilder = new StringBuilder("SELECT COUNT(e) FROM CatalPkgMode AS e WHERE e.validFrom <= :validFrom AND (e.validTo IS NULL OR e.validTo > :validFrom)");
	   boolean isPkgCode = false;
	   boolean isName = false;
	   if(StringUtils.isNotBlank(pkgCode)) {
		   qBuilder.append(" AND LOWER(e.pkgCode) LIKE ( LOWER(:pkgCode))");
		   isPkgCode = true;
	   }
	   if(StringUtils.isNotBlank(name)) {
		   qBuilder.append(" AND LOWER(e.name) LIKE ( LOWER(:name))");
		   isName = true;
	   }
	   TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
	   query.setParameter("validFrom", validFrom);
	   if(isPkgCode) 
		   query.setParameter("pkgCode", pkgCode+"%");
	   if(isName)
		   query.setParameter("name", name+"%");
	   return query.getSingleResult();
   }
   
}
