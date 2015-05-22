package org.adorsys.adstock.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.security.SecurityUtil;
import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.adorsys.adcatal.rest.CatalArtFeatMappingReaderEJB;
import org.adorsys.adstock.jpa.StkArticleLot;
import org.adorsys.adstock.jpa.StkArticleLotSearchInput;
import org.adorsys.adstock.repo.StkArticleLotRepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class StkArticleLotEJB {

	@Inject
	private StkArticleLotRepository repository;

	@Inject
	private SecurityUtil securityUtil;

	@Inject
	CatalArtFeatMappingReaderEJB catalArtFeatMappingReaderEJB;

	@Inject
	private EntityManager em;

	public StkArticleLot create(StkArticleLot entity) {

		entity.setId(entity.getLotPic());
		if(StringUtils.isBlank(entity.getIdentif())){
			entity.setIdentif(entity.getLotPic());
		}
		return populateStkArticleLot(repository.save(attach(entity)));

	}

	public StkArticleLot deleteById(String id) {
		StkArticleLot entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return populateStkArticleLot(entity);
	}

	public StkArticleLot update(StkArticleLot entity) {
		return populateStkArticleLot(repository.save(attach(entity)));
	}

	public StkArticleLot findById(String id) {
		return populateStkArticleLot(repository.findBy(id));
	}

	public List<StkArticleLot> listAll(int start, int max) {
		List<StkArticleLot> stkArticleLots = repository.findAll(start, max);
		for (StkArticleLot stkArticleLot : stkArticleLots) {
			stkArticleLot = populateStkArticleLot(stkArticleLot);
		}
		return stkArticleLots;
	}

	public Long count() {
		return repository.count();
	}

	public List<StkArticleLot> findBy(StkArticleLot entity, int start, int max,
			SingularAttribute<StkArticleLot, ?>[] attributes) {
		List<StkArticleLot> stkArticleLots = repository.findBy(entity, start,
				max, attributes);
		for (StkArticleLot stkArticleLot : stkArticleLots) {
			stkArticleLot = populateStkArticleLot(stkArticleLot);
		}
		return stkArticleLots;
	}

	public Long countBy(StkArticleLot entity,
			SingularAttribute<StkArticleLot, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<StkArticleLot> findByLike(StkArticleLot entity, int start,
			int max, SingularAttribute<StkArticleLot, ?>[] attributes) {

		List<StkArticleLot> stkArticleLots = repository.findByLike(entity,
				start, max, attributes);
		for (StkArticleLot stkArticleLot : stkArticleLots) {
			stkArticleLot = populateStkArticleLot(stkArticleLot);
		}
		return stkArticleLots;
	}

	public Long countByLike(StkArticleLot entity,
			SingularAttribute<StkArticleLot, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private StkArticleLot attach(StkArticleLot entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public StkArticleLot populateStkArticleLot(StkArticleLot entity) {
		String langIso2 = securityUtil.getUserLange();
		List<String> listLangIso2 = securityUtil.getUserLangePrefs();
		if (StringUtils.isBlank(langIso2)) {
			langIso2 = listLangIso2.get(0);
		}
		String identif = CatalArtFeatMapping.toIdentif(entity.getArtPic(),
				langIso2);
		CatalArtFeatMapping catalArtFeatMapp = catalArtFeatMappingReaderEJB
				.findByIdentif(identif);

		entity.setArtFeatures(catalArtFeatMapp);
		return entity;
	}

	public StkArticleLot findByIdentif(String identif) {
		List<StkArticleLot> resultList = repository.findByIdentif(identif)
				.maxResults(1).getResultList();
		if (resultList.isEmpty())
			return null;

		StkArticleLot stkArtLot = populateStkArticleLot(resultList.iterator()
				.next());
		return stkArtLot;
	}

	public List<StkArticleLot> findByArtPicLike(String artPick) {
		List<StkArticleLot> stkArticleLots = repository
				.findByArtPicLike(artPick);
		for (StkArticleLot stkArticleLot : stkArticleLots) {
			stkArticleLot = populateStkArticleLot(stkArticleLot);
		}
		return stkArticleLots;
	}

	public List<String> findLotPicByArtPic(String artPic) {
		return repository.findLotPicByArtPic(artPic);
	}

	private static final String FIND_CUSTOM_QUERY = "SELECT e FROM StkArticleLot AS e";
	private static final String COUNT_CUSTOM_QUERY = "SELECT count(e.id) FROM StkArticleLot AS e";

	public Long countCustom(StkArticleLotSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(COUNT_CUSTOM_QUERY,
				searchInput);
		TypedQuery<Long> query = em
				.createQuery(qBuilder.toString(), Long.class);
		setParameters(searchInput, query);
		return query.getSingleResult();
	}

	public List<StkArticleLot> findCustom(StkArticleLotSearchInput searchInput) {
		StringBuilder qBuilder = preprocessQuery(FIND_CUSTOM_QUERY, searchInput);
		TypedQuery<StkArticleLot> query = em.createQuery(qBuilder.toString(),
				StkArticleLot.class);
		setParameters(searchInput, query);

		int start = searchInput.getStart();
		int max = searchInput.getMax();

		if (start < 0)
			start = 0;
		query.setFirstResult(start);
		if (max >= 1)
			query.setMaxResults(max);

		List<StkArticleLot> stkArticleLots = query.getResultList();
		for (StkArticleLot stkArticleLot : stkArticleLots) {
			stkArticleLot = populateStkArticleLot(stkArticleLot);
		}
		return stkArticleLots;
	}

	private StringBuilder preprocessQuery(String findOrCount,
			StkArticleLotSearchInput searchInput) {
		StkArticleLot entity = searchInput.getEntity();

		String whereClause = " WHERE  ";
		String andClause = " AND ";

		StringBuilder qBuilder = new StringBuilder(findOrCount);
		boolean whereSet = false;

		if (StringUtils.isNotBlank(entity.getLotPic())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.lotPic=:lotPic");
		}
		if (StringUtils.isNotBlank(entity.getArtPic())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.artPic=:artPic");
		}
		if (StringUtils.isNotBlank(entity.getSupplierPic())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.supplierPic=:supplierPic");
		}
		if (StringUtils.isNotBlank(entity.getSupplier())) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.supplier=:supplier");
		}
		if (searchInput.getFrom() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.expirDt>:from");
		}
		if (searchInput.getTo() != null) {
			if (!whereSet) {
				qBuilder.append(whereClause);
				whereSet = true;
			} else {
				qBuilder.append(andClause);
			}
			qBuilder.append("e.expirDt<:to");
		}

		return qBuilder;
	}

	public void setParameters(StkArticleLotSearchInput searchInput, Query query) {
		StkArticleLot entity = searchInput.getEntity();

		if (StringUtils.isNotBlank(entity.getLotPic())) {
			query.setParameter("lotPic", entity.getLotPic());
		}
		if (StringUtils.isNotBlank(entity.getArtPic())) {
			query.setParameter("artPic", entity.getArtPic());
		}
		if (StringUtils.isNotBlank(entity.getSupplierPic())) {
			query.setParameter("supplierPic", entity.getSupplierPic());
		}
		if (StringUtils.isNotBlank(entity.getSupplier())) {
			query.setParameter("supplier", entity.getSupplier());
		}
		if (searchInput.getFrom() != null) {
			query.setParameter("from", searchInput.getFrom());
		}
		if (searchInput.getTo() != null) {
			query.setParameter("to", searchInput.getTo());
		}
	}

	// public List<String> findIdByDlvryNbr(String dlvryNbr) {
	// return repository.findIdByDlvryNbr(dlvryNbr);
	// }
	//
	// public Long countByDlvryNbr(String dlvryNbr) {
	// return repository.countByDlvryNbr(dlvryNbr);
	// }
	//
	// public List<String> findIdByDlvryItemNbr(String dlvryItemNbr) {
	// return repository.findIdByDlvryItemNbr(dlvryItemNbr);
	// }
}
