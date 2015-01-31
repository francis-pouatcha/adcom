package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.repo.CatalArticleRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalArticleEJB {

	@Inject
	private CatalArticleRepository repository;

	@Inject
	private CatalArtFeatMappingEJB featMappingEJB;

	@Inject
	private CatalPicMappingEJB picMappingEJB;

	@Inject
	private CatalCipOrigineEnumContract cipOrigineEnumContract;

	@Inject
	private SecurityUtil securityUtil;

	public CatalArticle create(CatalArticle entity) {
		// create the feature mapping
		CatalArtFeatMapping features = entity.getFeatures();

		entity = repository.save(attach(entity));

		if (features == null) {
			features = new CatalArtFeatMapping();
		}
		if (StringUtils.isBlank(features.getLangIso2())) {
			String lg = securityUtil.getUserLange();
			features.setLangIso2(lg);
		}
		features.setArtIdentif(entity.getIdentif());
		features = featMappingEJB.create(features);

		CatalPicMapping picMapping = new CatalPicMapping();
		picMapping.setArtIdentif(entity.getPic());
		picMapping.setCode(entity.getPic());
		picMapping.setCodeOrigin(cipOrigineEnumContract.getMain());
		picMapping.setPriority(0);
		picMappingEJB.create(picMapping);

		entity.setFeatures(features);

		return entity;
	}

	/**
	 * Delete all entities with the given identifier.
	 * 
	 * @param identif
	 * @return
	 */
	public List<CatalArticle> deleteByPic(String pic) {
		List<CatalArticle> entities = repository.findByPic(pic);
		processI18n(entities, new Date());
		for (CatalArticle catalArticle : entities) {
			repository.remove(catalArticle);
		}
		featMappingEJB.deleteByPic(pic);
		picMappingEJB.deleteByArtIdentif(pic);
		return entities;
	}

	public CatalArticle update(CatalArticle entity) {
		CatalArticle catalArticle = repository.save(attach(entity));
		return processI18n(catalArticle, new Date());
	}

	public CatalArticle findById(String id) {
		return processI18n(repository.findBy(id), new Date());
	}

	public CatalArticle findByIdentif(String identif) {
		Date now = new Date();
		List<CatalArticle> resultList = repository.findByIdentif(identif, now)
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return processI18n(resultList.iterator().next(), now);
	}

	public List<CatalArticle> listAll(int start, int max) {
		return processI18n(repository.findAll(start, max), new Date());
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalArticle> findBy(CatalArticle entity, int start, int max,
			SingularAttribute<CatalArticle, ?>[] attributes) {
		return processI18n(repository.findBy(entity, start, max, attributes),
				new Date());
	}

	public Long countBy(CatalArticle entity,
			SingularAttribute<CatalArticle, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalArticle> findByLike(CatalArticle entity, int start,
			int max, SingularAttribute<CatalArticle, ?>[] attributes) {
		return processI18n(
				repository.findByLike(entity, start, max, attributes),
				new Date());
	}

	public Long countByLike(CatalArticle entity,
			SingularAttribute<CatalArticle, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalArticle attach(CatalArticle entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalArticle findByIdentif(String identif, Date validOn) {
		List<CatalArticle> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return processI18n(resultList.iterator().next(), validOn);
	}

	private CatalArticle processI18n(final CatalArticle catalArticle,
			Date validOn) {
		if (catalArticle == null)
			return null;
		String langIso2 = securityUtil.getUserLange();
		String identif = CatalArtFeatMapping.toIdentif(
				catalArticle.getIdentif(), langIso2);
		CatalArtFeatMapping featMapping = featMappingEJB.findByIdentif(identif);
		if (featMapping == null) {
			List<String> list = securityUtil.getUserLangePrefs();
			for (String lg : list) {
				if (StringUtils.equals(lg, langIso2))
					continue;
				identif = CatalArtFeatMapping.toIdentif(catalArticle.getIdentif(), lg);
				featMapping = featMappingEJB.findByIdentif(identif);
				if (featMapping != null)
					break;
			}
		}
		catalArticle.setFeatures(featMapping);
		return catalArticle;
	}

	private List<CatalArticle> processI18n(List<CatalArticle> catalArticles,
			Date validOn) {
		for (CatalArticle catalArticle : catalArticles) {
			processI18n(catalArticle, validOn);
		}
		return catalArticles;
	}

}
