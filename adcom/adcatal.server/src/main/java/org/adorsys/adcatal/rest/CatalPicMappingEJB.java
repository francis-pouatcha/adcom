package org.adorsys.adcatal.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalCipOrigine;
import org.adorsys.adcatal.jpa.CatalPicMapping;
import org.adorsys.adcatal.repo.CatalPicMappingRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class CatalPicMappingEJB {

	@Inject
	private CatalPicMappingRepository repository;

	@Inject
	private CatalCipOrigineEJB cipOrigineEJB;

	@Inject
	private SecurityUtil securityUtil;

	public CatalPicMapping create(CatalPicMapping entity) {
		CatalPicMapping picMapping = repository.save(attach(entity));
		return processI18n(picMapping);
	}

	public CatalPicMapping deleteById(String id) {
		CatalPicMapping entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return processI18n(entity);
	}
	public List<CatalPicMapping> deleteByArtIdentif(String artIdentif) {
		List<CatalPicMapping> entities = repository.findByArtIdentif(artIdentif);
		for (CatalPicMapping catalPicMapping : entities) {
			repository.remove(catalPicMapping);
		}
		return entities;
	}
	
	public CatalPicMapping update(CatalPicMapping entity) {
		return repository.save(attach(entity));
	}

	public CatalPicMapping findById(String id) {
		CatalPicMapping picMapping = repository.findBy(id);
		return processI18n(picMapping);
	}

	public List<CatalPicMapping> listAll(int start, int max) {
		List<CatalPicMapping> picMappings = repository.findAll(start, max);
		return processI18n(picMappings);
	}

	public Long count() {
		return repository.count();
	}

	public List<CatalPicMapping> findBy(CatalPicMapping entity, int start,
			int max, SingularAttribute<CatalPicMapping, ?>[] attributes) {
		List<CatalPicMapping> picMappings = repository.findBy(entity, start, max, attributes);
		return processI18n(picMappings);
	}

	public Long countBy(CatalPicMapping entity,
			SingularAttribute<CatalPicMapping, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CatalPicMapping> findByLike(CatalPicMapping entity, int start,
			int max, SingularAttribute<CatalPicMapping, ?>[] attributes) {
		List<CatalPicMapping> picMappings = repository.findByLike(entity, start, max, attributes);
		return processI18n(picMappings);
	}

	public Long countByLike(CatalPicMapping entity,
			SingularAttribute<CatalPicMapping, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CatalPicMapping attach(CatalPicMapping entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public CatalPicMapping findByIdentif(String identif, Date validOn) {
		List<CatalPicMapping> resultList = repository
				.findByIdentif(identif, validOn).orderAsc("validFrom")
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;
		return resultList.iterator().next();
	}

	private CatalPicMapping processI18n(final CatalPicMapping picMapping) {
		if(picMapping==null) return null;
		String codeOrigin = picMapping.getCodeOrigin();
		if (StringUtils.isNotBlank(codeOrigin)) {
			String userLange = securityUtil.getUserLange();
			CatalCipOrigine cipOrigine = cipOrigineEJB
					.findByIdentif(CatalCipOrigine.toIdentif(codeOrigin,
							userLange));
			if (cipOrigine == null) {
				List<String> userLanges = securityUtil.getUserLangePrefs();
				for (String ulang : userLanges) {
					if(StringUtils.equals(ulang, userLange)) continue;
					cipOrigine = cipOrigineEJB.findByIdentif(CatalCipOrigine
							.toIdentif(codeOrigin, ulang));
					if (cipOrigine != null)
						break;
				}
			}
			if (cipOrigine != null) {
				picMapping.setCodeOriginTitle(cipOrigine.getName());
				picMapping.setCodeOriginText(cipOrigine.getDescription());
			} else {
				picMapping.setCodeOriginTitle(codeOrigin);
				picMapping.setCodeOriginText(codeOrigin);
			}
		}
		return picMapping;
	}
	
	private List<CatalPicMapping> processI18n(List<CatalPicMapping> picMappings) {
		for (CatalPicMapping catalPicMapping : picMappings) {
			processI18n(catalPicMapping);
		}
		return picMappings;
	}
}
