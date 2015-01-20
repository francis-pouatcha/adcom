package org.adorsys.adbase.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.jpa.OrgContact;
import org.adorsys.adbase.repo.OrgContactRepository;
import org.apache.commons.lang3.StringUtils;
import org.apache.deltaspike.data.api.QueryResult;

@Stateless
public class OrgContactEJB 
{

   @Inject
   private OrgContactRepository repository;

   @Inject
   private EntityManager em;
   
   public OrgContact create(OrgContact entity)
   {
      return repository.save(attach(entity));
   }
   
   public OrgContact createCustom(OrgContact entity) {
	   Long count = countByOrgUnit(entity.getOuIdentif(), new Date());
	   entity.setContactIndex(Long.toString(count + 1));
	   return create(entity);
   }

   public OrgContact deleteById(String id)
   {
      OrgContact entity = repository.findBy(id);
      if (entity != null)
      {
    	  repository.remove(entity);
      }
      return entity;
   }

   /**
    * Update the validTo of the entity, and save.
    * @param id
    * @return
    */
   public OrgContact deleteCustomById(String id){
	   OrgContact entity = repository.findBy(id);
	   if(entity != null ) {
	    	entity.setValidTo(new Date());
	    	repository.save(entity);
	   }
	   return entity;
   }

   public OrgContact update(OrgContact entity)
   {
      return repository.save(attach(entity));
   }

   public OrgContact findById(String id)
   {
      return repository.findBy(id);
   }

   public List<OrgContact> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<OrgContact> findBy(OrgContact entity, int start, int max, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(OrgContact entity, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<OrgContact> findByLike(OrgContact entity, int start, int max, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(OrgContact entity, SingularAttribute<OrgContact, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private OrgContact attach(OrgContact entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public OrgContact findByIdentif(String identif, Date validOn){
	   List<OrgContact> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
   public List<OrgContact> searchOrgContacts(String ouIdentif, String email, String phone,
		   String city, String ctryIso3,Date validFrom,int start, int max) {
	   validFrom = validFrom == null ? new Date() : validFrom;
	   StringBuilder qBuilder = new StringBuilder("SELECT e FROM OrgContact AS e WHERE e.validFrom <= :validFrom AND (e.validTo IS NULL OR e.validTo > :validFrom) ");
	   
	   boolean isOuIdentif = false;
	   boolean isEmail = false;
	   boolean isPhone = false;
	   boolean isCity = false;
	   boolean isCtryIso3 = false;
	   
	   if(StringUtils.isNotBlank(ouIdentif)) {
		   qBuilder.append(" AND e.ouIdentif = :ouIdentif");
		   isOuIdentif = true;
	   }
	   if(StringUtils.isNotBlank(email)) {
		   qBuilder.append(" AND e.email = :email");
		   isEmail = true;
	   }
	   if(StringUtils.isNotBlank(phone)) {
		   qBuilder.append(" AND e.phone = :phone");
		   isPhone = true;
	   }
	   if(StringUtils.isNotBlank(city)) {
		   qBuilder.append(" AND LOWER(e.city) LIKE(LOWER(:city))");
		   isCity = true;
	   }
	   if(StringUtils.isNotBlank(ctryIso3)) {
		   qBuilder.append(" AND e.country = :ctryIso3");
		   isCtryIso3 = true;
	   }
	   TypedQuery<OrgContact> query = em.createQuery(qBuilder.toString(), OrgContact.class);
	   query.setParameter("validFrom", validFrom);
	   if(isOuIdentif) {
		   query.setParameter("ouIdentif", ouIdentif);
	   }
	   if(isEmail) {
		   query.setParameter("email", email);
	   }
	   if(isPhone) {
		   query.setParameter("phone", phone);
	   }
	   if(isCity) {
		   query.setParameter("city", "%"+city+"%");
	   }
	   if(isCtryIso3) {
		   query.setParameter("ctryIso3", ctryIso3);
	   }
	   return query.setFirstResult(start).setMaxResults(max).getResultList();
   }

   public Long countOrgContacts(String ouIdentif, String email, String phone,
		   String city, String ctryIso3,Date validFrom,int start, int max) {
	   validFrom = validFrom == null ? new Date() : validFrom;
	   StringBuilder qBuilder = new StringBuilder("SELECT COUNT(e) FROM OrgContact AS e WHERE e.validFrom <= :validFrom AND (e.validTo IS NULL OR e.validTo > :validFrom) ");
	   
	   boolean isOuIdentif = false;
	   boolean isEmail = false;
	   boolean isPhone = false;
	   boolean isCity = false;
	   boolean isCtryIso3 = false;
	   
	   if(StringUtils.isNotBlank(ouIdentif)) {
		   qBuilder.append(" AND e.ouIdentif = :ouIdentif");
		   isOuIdentif = true;
	   }
	   if(StringUtils.isNotBlank(email)) {
		   qBuilder.append(" AND e.email = :email");
		   isEmail = true;
	   }
	   if(StringUtils.isNotBlank(phone)) {
		   qBuilder.append(" AND e.phone = :phone");
		   isPhone = true;
	   }
	   if(StringUtils.isNotBlank(city)) {
		   qBuilder.append(" AND LOWER(e.city) LIKE(LOWER(:city))");
		   isCity = true;
	   }
	   if(StringUtils.isNotBlank(ctryIso3)) {
		   qBuilder.append(" AND e.country = :ctryIso3");
		   isCtryIso3 = true;
	   }
	   TypedQuery<Long> query = em.createQuery(qBuilder.toString(), Long.class);
	   query.setParameter("validFrom", validFrom);
	   if(isOuIdentif) {
		   query.setParameter("ouIdentif", ouIdentif);
	   }
	   if(isEmail) {
		   query.setParameter("email", email);
	   }
	   if(isPhone) {
		   query.setParameter("phone", phone);
	   }
	   if(isCity) {
		   query.setParameter("city", "%"+city+"%");
	   }
	   if(isCtryIso3) {
		   query.setParameter("ctryIso3", ctryIso3);
	   }
	   return query.getSingleResult();
   }
   public QueryResult<OrgContact> findByOrgUnit(String ouIdentif, Date validOn){
	   return repository.findByOrgUnit(ouIdentif, validOn);
   }

   public Long countByOrgUnit(String ouIdentif, Date validOn){
	   return repository.countByOrgUnit(ouIdentif, validOn);
   }
}
