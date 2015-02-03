package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtManufSupp;
import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.adorsys.adcatal.repo.CatalArtManufSuppRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalArtManufSuppEJB 
{

   @Inject
   private CatalArtManufSuppRepository repository;
   @Inject
   private SecurityUtil securityUtil;
   @Inject
   private CatalCipOrigineEJB cipOrigineEJB;

   public CatalArtManufSupp create(CatalArtManufSupp entity)
   {
       CatalArtManufSupp manufSupp = repository.save(attach(entity));
      return processI18n(manufSupp);
   }

   public CatalArtManufSupp deleteById(String id)
   {
      CatalArtManufSupp entity = repository.findBy(id);
      if (entity != null)
      {
         repository.remove(entity);
      }
      return processI18n(entity);
   }

   public CatalArtManufSupp update(CatalArtManufSupp entity)
   {
      return repository.save(attach(entity));
   }

   public CatalArtManufSupp findById(String id)
   {
       CatalArtManufSupp manufSupp = repository.findBy(id);
      return processI18n(manufSupp);
   }

   public List<CatalArtManufSupp> listAll(int start, int max)
   {
       List<CatalArtManufSupp> manufSupps = repository.findAll(start, max);
      return processI18n(manufSupps);
   }

   public Long count()
   {
      return repository.count();
   }

   public List<CatalArtManufSupp> findBy(CatalArtManufSupp entity, int start, int max, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      List<CatalArtManufSupp> manufSupp = repository.findBy(entity, start, max, attributes);
      return processI18n(manufSupp);
   }

   public Long countBy(CatalArtManufSupp entity, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      return repository.count(entity, attributes);
   }

   public List<CatalArtManufSupp> findByLike(CatalArtManufSupp entity, int start, int max, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
	   List<CatalArtManufSupp> manufSupps = repository.findByLike(entity, start, max, attributes);
      return processI18n(manufSupps);
   }

   public Long countByLike(CatalArtManufSupp entity, SingularAttribute<CatalArtManufSupp, ?>[] attributes)
   {
      return repository.countLike(entity, attributes);
   }

   private CatalArtManufSupp attach(CatalArtManufSupp entity)
   {
      if (entity == null)
         return null;

      return entity;
   }
   public CatalArtManufSupp findByIdentif(String identif, Date validOn){
	   List<CatalArtManufSupp> resultList = repository.findByIdentif(identif, validOn).orderAsc("validFrom").maxResults(1).getResultList();
	   if(resultList.isEmpty()) return null;
	   return resultList.iterator().next();
   }
   
   private CatalArtManufSupp processI18n(final CatalArtManufSupp manufSupp) {
		if(manufSupp==null) return null;
		String msType = manufSupp.getMsType();
		if (StringUtils.isNotBlank(msType)) {
			String userLange = securityUtil.getUserLange();
			CatalCipOrigine cipOrigine = cipOrigineEJB
					.findByIdentif(CatalCipOrigine.toIdentif(msType,
							userLange));
			if (cipOrigine == null) {
				List<String> userLanges = securityUtil.getUserLangePrefs();
				for (String ulang : userLanges) {
					if(StringUtils.equals(ulang, userLange)) continue;
					cipOrigine = cipOrigineEJB.findByIdentif(CatalCipOrigine
							.toIdentif(msType, ulang));
					if (cipOrigine != null)
						break;
				}
			}
			if (cipOrigine != null) {
				manufSupp.setMsTypeName(cipOrigine.getName());
				manufSupp.setMsTypeDescription(cipOrigine.getDescription());
			} else {
				manufSupp.setMsTypeName(msType);
				manufSupp.setMsTypeDescription(msType);
			}
		}
		return manufSupp;
	}
	
	private List<CatalArtManufSupp> processI18n(List<CatalArtManufSupp> manufSupps) {
		for (CatalArtManufSupp catalmanufSupp : manufSupps) {
			processI18n(catalmanufSupp);
		}
		return manufSupps;
	}
   
}
