package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.repo.CatalArtDetailConfigRepository;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalArtDetailConfigEJB 
{

   @Inject
   private CatalArtDetailConfigRepository repository;

   @Inject
   private CatalArticleEJB catalArticleEJB;
   
   public CatalArtDetailConfig create(CatalArtDetailConfig entity)
   {
      return repository.save(attach(entity));
   }

	public CatalArtDetailConfig createCustom(CatalArtDetailConfig entity) {
		String artPic = entity.getPic();
		if(StringUtils.isBlank(artPic)) throw new IllegalArgumentException("The article pic should not be null here.");
		Date validOn = new Date();
		CatalArticle catalArticle = catalArticleEJB.findByIdentif(artPic, validOn);
		if(catalArticle == null) throw new IllegalStateException("The article was not found");
		entity.setPic(artPic);
		entity.setVatRate(catalArticle.getVatRate());
		entity.setValidFrom(validOn);
		return repository.save(entity);
	}
	
   public CatalArtDetailConfig deleteById(String id)
   {
      CatalArtDetailConfig entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return entity;
   }
   
   public CatalArtDetailConfig deleteCustomById(String id)
   {
	   CatalArtDetailConfig entity = repository.findBy(id);
	   if (entity != null)
	   {
		   entity.setValidTo(new Date());
		   repository.save(entity);
	   }
	   return entity;
   }

   public CatalArtDetailConfig update(CatalArtDetailConfig entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtDetailConfig findById(String id)
   {
      return repository.findBy(id);
   }

   public List<CatalArtDetailConfig> listAll(int start, int max)
   {
      return repository.findAll(start, max);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArtDetailConfig> findBy(CatalArtDetailConfig entity, int start, int max, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.findBy(entity, start, max, attributes);
   }

   public Long countBy(CatalArtDetailConfig entity, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArtDetailConfig> findByLike(CatalArtDetailConfig entity, int start, int max, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.findByLike(entity, start, max, attributes);
   }

   public Long countByLike(CatalArtDetailConfig entity, SingularAttribute<CatalArtDetailConfig, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArtDetailConfig attach(CatalArtDetailConfig entity)
   {
      if (entity == null)
         return null;

      return entity;
   }

   public CatalArtDetailConfig findByIdentif(String identif, Date validOn){
	   List<CatalArtDetailConfig> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   

	public List<CatalArtDetailConfig> findByArtPicAndIdentif(String pic, Date validOn) {
		List<CatalArtDetailConfig> resultList = repository.findByArtPicAndIdentif(pic, new Date());
		return resultList;
	}

}
