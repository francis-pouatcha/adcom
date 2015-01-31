package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.repo.CatalProductFamilyRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalProductFamilyEJB 
{

   @Inject
   private CatalProductFamilyRepository repository;
   
   @Inject
   private CatalFamilyFeatMapingEJB familyFeatMapingEJB;

   @Inject
	private SecurityUtil securityUtil;

   public CatalProductFamily create(CatalProductFamily entity)
   {
	   entity = repository.save(attach(entity));

	   CatalFamilyFeatMaping features = entity.getFeatures();
		if(features==null){
			features = new CatalFamilyFeatMaping();
		}
		if(StringUtils.isBlank(features.getLangIso2())){
			String lg = securityUtil.getUserLange();
			features.setLangIso2(lg);
		}
		if(StringUtils.isBlank(features.getLangIso2())){
			String lg = securityUtil.getUserLange();
			features.setLangIso2(lg);
		}
		features.setPfIdentif(entity.getIdentif());
		features = familyFeatMapingEJB.create(features);
		entity.setFeatures(features);
		return entity;
   }

	public CatalProductFamily deleteByFamCode(String identif) {
		CatalProductFamily entity = findByIdentif(identif);
		if(entity!=null){
			processI18n(entity, new Date());
			repository.remove(entity);
			familyFeatMapingEJB.deleteByPfIdentif(identif);
		}
		return entity;
	}

   public CatalProductFamily update(CatalProductFamily entity)
   {
      return processI18n(repository.save(attach(entity)), new Date());
   }

   public CatalProductFamily findById(String id)
   {
      return processI18n(repository.findBy(id), new Date());
   }

   public List<CatalProductFamily> listAll(int start, int max)
   {
	   return processI18n(repository.findAll(start, max), new Date());
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalProductFamily> findBy(CatalProductFamily entity, int start, int max, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
	   return processI18n(repository.findBy(entity, start, max, attributes), new Date());
   }

   public Long countBy(CatalProductFamily entity, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalProductFamily> findByLike(CatalProductFamily entity, int start, int max, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
	   return processI18n(repository.findByLike(entity, start, max, attributes), new Date());
   }

   public Long countByLike(CatalProductFamily entity, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalProductFamily attach(CatalProductFamily entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalProductFamily findByIdentif(String identif){
	   List<CatalProductFamily> resultList = repository.findByIdentif(identif).maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return processI18n(resultList.iterator().next(), new Date());
   }

	private CatalProductFamily processI18n(final CatalProductFamily catalProductFamily, Date validOn){
		if(catalProductFamily==null) return null;
		String langIso2 = securityUtil.getUserLange();
		String identif = CatalFamilyFeatMaping.toIdentif(catalProductFamily.getIdentif(), langIso2);
		CatalFamilyFeatMaping featMapping = familyFeatMapingEJB.findByIdentif(identif, validOn);
		if(featMapping==null){
			List<String> list = securityUtil.getUserLangePrefs();
			for (String lg : list) {
				if(StringUtils.equals(lg, langIso2)) continue;
				identif = CatalArtFeatMapping.toIdentif(catalProductFamily.getIdentif(), lg);
				featMapping = familyFeatMapingEJB.findByIdentif(identif, validOn);
				if(featMapping!=null) break;
			}
		}
		catalProductFamily.setFeatures(featMapping);
		return catalProductFamily;
	}
	private List<CatalProductFamily> processI18n(List<CatalProductFamily> catalProductFamilys, Date validOn){
		for (CatalProductFamily catalProductFamily : catalProductFamilys) {
			processI18n(catalProductFamily, validOn);
		}
		return catalProductFamilys;
	}
}
