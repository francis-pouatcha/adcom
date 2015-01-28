package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2OuEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2POEvtData;
import org.adorsys.adprocmt.repo.PrcmtDeliveryEvtDataRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvry2OuEvtDataRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvry2POEvtDataRepository;

@Stateless
public class PrcmtDeliveryEvtDataEJB {

	@Inject
	private PrcmtDeliveryEvtDataRepository repository;
	@Inject
	private PrcmtDlvry2OuEvtDataRepository ouEvtDataRepository;
	@Inject
	private PrcmtDlvry2POEvtDataRepository poEvtDataRepository;

	public PrcmtDeliveryEvtData create(PrcmtDeliveryEvtData entity) {
		return repository.save(attach(entity));
	}

	public PrcmtDlvry2OuEvtData addDlvry2OuEvtData(PrcmtDeliveryEvtData dlvry,
			PrcmtDlvry2OuEvtData ou) {
		ou.setDlvryNbr(dlvry.getDlvryNbr());
		return ouEvtDataRepository.save(ou);
	}

	public PrcmtDlvry2POEvtData addDlvry2POEvtData(PrcmtDeliveryEvtData dlvry,
			PrcmtDlvry2POEvtData po) {
		po.setDlvryNbr(dlvry.getDlvryNbr());
		return poEvtDataRepository.save(po);
	}

	public PrcmtDeliveryEvtData deleteById(String id) {
		PrcmtDeliveryEvtData entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
			List<PrcmtDlvry2OuEvtData> ous = ouEvtDataRepository
					.findByDlvryNbr(entity.getDlvryNbr());
			for (PrcmtDlvry2OuEvtData ouEvtData : ous) {
				ouEvtDataRepository.remove(ouEvtData);
			}
			List<PrcmtDlvry2POEvtData> pos = poEvtDataRepository
					.findByDlvryNbr(entity.getDlvryNbr());
			for (PrcmtDlvry2POEvtData poEvtData : pos) {
				poEvtDataRepository.remove(poEvtData);
			}
		}
		return entity;
	}

	public PrcmtDlvry2POEvtData deleteByProcOrder(String id) {
		PrcmtDlvry2POEvtData po = poEvtDataRepository.findBy(id);
		if (po != null)
			poEvtDataRepository.remove(po);
		return po;
	}

	public PrcmtDlvry2OuEvtData deleteByOrgUnit(String id) {
		PrcmtDlvry2OuEvtData ou = ouEvtDataRepository.findBy(id);
		if (ou != null)
			ouEvtDataRepository.remove(ou);
		return ou;
	}

	public PrcmtDeliveryEvtData update(PrcmtDeliveryEvtData entity) {
		return repository.save(attach(entity));
	}
	public PrcmtDlvry2OuEvtData updateOrgUnit(PrcmtDlvry2OuEvtData ouEvtData) {
		return ouEvtDataRepository.save(ouEvtData);
	}
	
	public PrcmtDeliveryEvtData findById(String id) {
		return repository.findBy(id);
	}

	public PrcmtDlvry2OuEvtData findDlvry2OuEvtData(String dlvryNbr,
			String rcvngOrgUnit) {
		return ouEvtDataRepository.findBy(PrcmtDlvry2OuEvtData.toId(dlvryNbr,
				rcvngOrgUnit));
	}

	public PrcmtDlvry2POEvtData findDlvry2POEvtData(String dlvryNbr,
			String poNbr) {
		return poEvtDataRepository.findBy(PrcmtDlvry2POEvtData.toId(dlvryNbr,
				poNbr));
	}

	public List<PrcmtDeliveryEvtData> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public List<PrcmtDlvry2OuEvtData> listDlvry2OuEvtData(String dlvryNbr) {
		return ouEvtDataRepository.findByDlvryNbr(dlvryNbr);
	}

	public List<PrcmtDlvry2POEvtData> listDlvry2POEvtData(String dlvryNbr) {
		return poEvtDataRepository.findByDlvryNbr(dlvryNbr);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDeliveryEvtData> findBy(PrcmtDeliveryEvtData entity,
			int start, int max,
			SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDeliveryEvtData entity,
			SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDeliveryEvtData> findByLike(PrcmtDeliveryEvtData entity,
			int start, int max,
			SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDeliveryEvtData entity,
			SingularAttribute<PrcmtDeliveryEvtData, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDeliveryEvtData attach(PrcmtDeliveryEvtData entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
