package org.adorsys.adcatal.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.adorsys.adcatal.repo.CatalProductFamilyRepository;
import org.adorsys.adcore.utils.SequenceGenerator;
import org.apache.commons.lang3.RandomStringUtils;
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

	@Inject
	private CatalArt2ProductFamilyEJB art2ProductFamilyEJB;
   
   public CatalProductFamily create(CatalProductFamily entity)
   {
	   String famCode = entity.getFamCode();
	   if(StringUtils.isBlank(famCode))
			famCode = SequenceGenerator.getSequence(SequenceGenerator.PRODUCT_FAMILY_SEQUENCE_PREFIXE);

	   if(famCode.contains("/"))
		   famCode.replace('/', '_');

	   if(famCode.length()>50)
		   famCode = famCode.substring(0, 48) + RandomStringUtils.randomNumeric(2);// Keep then distinct.
	   
	   String parentPath = "/";
	   String parentIdentif = entity.getParentIdentif();
	   if(StringUtils.isNotBlank(parentIdentif)){
		   CatalProductFamily parent = findByIdentif(parentIdentif);
		   if(parent!=null){
			   parentPath=parent.getFamPath();
		   }
	   }
	   entity.setFamPath(parentPath + famCode + "/");
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
			String lg = securityUtil.getUserLangePrefs().iterator().next();
			features.setLangIso2(lg);
		}
		features.setPfIdentif(entity.getIdentif());
		features.setFamPath(entity.getFamPath());
		features = familyFeatMapingEJB.create(features);
		entity.setFeatures(features);
		return entity;
   }
	
	public void addProductFamilyMapping(String artPic, String famCode){
		List<CatalFamilyFeatMaping> featMappings = familyFeatMapingEJB.findByPfIdentif(famCode);
		for (CatalFamilyFeatMaping fm : featMappings) {
			CatalArt2ProductFamily a2p = art2ProductFamilyEJB.findByIdentif(CatalArt2ProductFamily.toIdentif(famCode, artPic, fm.getLangIso2()));
			if(a2p==null)
				a2p = new CatalArt2ProductFamily();
			a2p.setArtPic(artPic);
			a2p.setFamCode(famCode);
			a2p.setLangIso2(fm.getLangIso2());
			a2p.setFamilyName(fm.getFamilyName());
			a2p.setFamPath(fm.getFamPath());
			art2ProductFamilyEJB.create(a2p);
		}
	}

	public CatalProductFamily deleteByFamCode(String identif) {
		CatalProductFamily entity = findByIdentif(identif);
		if(entity!=null){
			processI18n(entity);
			repository.remove(entity);
			familyFeatMapingEJB.deleteByPfIdentif(identif);
			art2ProductFamilyEJB.deleteByFamCode(identif);
		}
		return entity;
	}

   public CatalProductFamily update(CatalProductFamily entity)
   {
      return processI18n(repository.save(attach(entity)));
   }

   public CatalProductFamily findById(String id)
   {
      return processI18n(repository.findBy(id));
   }

   public List<CatalProductFamily> listAll(int start, int max)
   {
	   return processI18n(repository.findAll(start, max));
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalProductFamily> findBy(CatalProductFamily entity, int start, int max, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
	   return processI18n(repository.findBy(entity, start, max, attributes));
   }

   public Long countBy(CatalProductFamily entity, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalProductFamily> findByLike(CatalProductFamily entity, int start, int max, SingularAttribute<CatalProductFamily, ?>[] attributes)
   {
	   return processI18n(repository.findByLike(entity, start, max, attributes));
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
	   return processI18n(resultList.iterator().next());
   }

	private CatalProductFamily processI18n(final CatalProductFamily catalProductFamily){
		if(catalProductFamily==null) return null;
		String langIso2 = securityUtil.getUserLange();
		CatalFamilyFeatMaping featureMapping = findFeatureMapping(catalProductFamily.getIdentif(), langIso2);
		catalProductFamily.setFeatures(featureMapping);
		return catalProductFamily;
	}
	
	private CatalFamilyFeatMaping findFeatureMapping(String famCode, String langIso2){
		String identif = CatalFamilyFeatMaping.toIdentif(famCode, langIso2);
		CatalFamilyFeatMaping featMapping = familyFeatMapingEJB.findByIdentif(identif);
		if(featMapping!=null) return featMapping;
		// try for any language.
		List<String> list = securityUtil.getUserLangePrefs();
		for (String lg : list) {
			if(StringUtils.equals(lg, langIso2)) continue;
			identif = CatalArtFeatMapping.toIdentif(famCode, lg);
			featMapping = familyFeatMapingEJB.findByIdentif(identif);
			if(featMapping!=null) return featMapping;
		}
		return null;
	}
	
	private List<CatalProductFamily> processI18n(List<CatalProductFamily> catalProductFamilys){
		for (CatalProductFamily catalProductFamily : catalProductFamilys) {
			processI18n(catalProductFamily);
		}
		return catalProductFamilys;
	}
}
