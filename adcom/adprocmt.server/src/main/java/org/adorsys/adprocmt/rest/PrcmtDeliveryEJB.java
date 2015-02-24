package org.adorsys.adprocmt.rest;

import java.math.BigDecimal;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcore.utils.SequenceGenerator;
import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2Ou;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2OuEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2PO;
import org.adorsys.adprocmt.jpa.PrcmtDlvry2POEvtData;
import org.adorsys.adprocmt.repo.PrcmtDeliveryRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvry2OuRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvry2PORepository;
import org.apache.commons.lang3.StringUtils;

@Stateless
public class PrcmtDeliveryEJB {

	@Inject
	private PrcmtDeliveryRepository repository;

	@Inject
	private PrcmtDeliveryEvtDataEJB evtDataEJB;

	@Inject
	private PrcmtDlvry2PORepository dlvry2poRepository;

	@Inject
	private PrcmtDlvry2OuRepository dlvry2OuRepository;

	public PrcmtDelivery create(PrcmtDelivery entity) {
		if (StringUtils.isBlank(entity.getDlvryNbr())) {
			entity.setDlvryNbr(SequenceGenerator
					.getSequence(SequenceGenerator.DELIVERY_SEQUENCE_PREFIXE));
		}
		entity.setId(entity.getDlvryNbr());
		PrcmtDeliveryEvtData evtData = new PrcmtDeliveryEvtData();
		entity = repository.save(attach(entity));
		entity.copyTo(evtData);
		evtData.setId(entity.getId());
		evtDataEJB.create(evtData);
		return entity;
	}

	public PrcmtDlvry2PO addProcOrder(PrcmtDelivery entity, String poNbr) {
		PrcmtDlvry2PO dlvryPO = new PrcmtDlvry2PO();
		dlvryPO.setDlvryNbr(entity.getDlvryNbr());
		dlvryPO.setPoNbr(poNbr);
		dlvryPO = dlvry2poRepository.save(dlvryPO);

		PrcmtDeliveryEvtData evtData = evtDataEJB.findById(entity.getId());
		PrcmtDlvry2POEvtData poEvtData = new PrcmtDlvry2POEvtData();
		poEvtData.setDlvryNbr(evtData.getDlvryNbr());
		poEvtData.setPoNbr(poNbr);
		evtDataEJB.addDlvry2POEvtData(evtData, poEvtData);

		return dlvryPO;
	}

	public PrcmtDlvry2Ou addOrgUnit(PrcmtDelivery entity, String rcvngOrgUnit,
			BigDecimal qtyPct) {
		PrcmtDlvry2Ou dlvry2Ou = new PrcmtDlvry2Ou();
		dlvry2Ou.setDlvryNbr(entity.getDlvryNbr());
		dlvry2Ou.setRcvngOrgUnit(rcvngOrgUnit);
		dlvry2Ou.setQtyPct(qtyPct);
		dlvry2Ou = dlvry2OuRepository.save(dlvry2Ou);

		PrcmtDeliveryEvtData evtData = evtDataEJB.findById(entity.getId());
		PrcmtDlvry2OuEvtData ouEvtData = new PrcmtDlvry2OuEvtData();
		ouEvtData.setDlvryNbr(entity.getDlvryNbr());
		ouEvtData.setRcvngOrgUnit(rcvngOrgUnit);
		ouEvtData.setQtyPct(qtyPct);
		evtDataEJB.addDlvry2OuEvtData(evtData, ouEvtData);

		return dlvry2Ou;
	}

	public PrcmtDelivery deleteById(String id) {
		PrcmtDelivery entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);

			List<PrcmtDlvry2PO> pos = dlvry2poRepository.findByDlvryNbr(entity
					.getDlvryNbr());
			for (PrcmtDlvry2PO po : pos) {
				dlvry2poRepository.remove(po);
			}
			List<PrcmtDlvry2Ou> ous = dlvry2OuRepository.findByDlvryNbr(entity
					.getDlvryNbr());
			for (PrcmtDlvry2Ou ou : ous) {
				dlvry2OuRepository.remove(ou);
			}

			evtDataEJB.deleteById(id);
		}
		return entity;
	}

	public PrcmtDlvry2PO deleteProcOrder(String id) {
		PrcmtDlvry2PO po = dlvry2poRepository.findBy(id);
		if (po != null) {
			dlvry2poRepository.remove(po);
			evtDataEJB.deleteByProcOrder(id);
		}
		return po;
	}

	public PrcmtDlvry2Ou deleteOrgUnit(String id) {
		PrcmtDlvry2Ou ou = dlvry2OuRepository.findBy(id);
		if (ou != null) {
			dlvry2OuRepository.remove(ou);
			evtDataEJB.deleteByOrgUnit(id);
		}
		return ou;
	}

	public PrcmtDelivery update(PrcmtDelivery entity) {
		entity = repository.save(attach(entity));
		PrcmtDeliveryEvtData eventData = evtDataEJB.findById(entity.getId());
		if (eventData != null) {
			entity.copyTo(eventData);
			evtDataEJB.update(eventData);
		}
		return entity;
	}

	public PrcmtDlvry2Ou updateOrgUnit(PrcmtDlvry2Ou ou) {
		ou = dlvry2OuRepository.save(ou);
		PrcmtDlvry2OuEvtData ouEvtData = evtDataEJB.findDlvry2OuEvtData(ou.getDlvryNbr(), ou.getRcvngOrgUnit());
		if(ou.contentEquals(ouEvtData)){
			ou.copyTo(ouEvtData);
			evtDataEJB.updateOrgUnit(ouEvtData);
		}
		return ou;
	}

	public PrcmtDelivery findById(String id) {
		return repository.findBy(id);
	}

	public List<PrcmtDelivery> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDelivery> findBy(PrcmtDelivery entity, int start, int max,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public PrcmtDlvry2PO findProcOrder(String dlvryNbr, String poNbr) {
		return dlvry2poRepository.findBy(PrcmtDlvry2PO.toId(dlvryNbr, poNbr));
	}

	public PrcmtDlvry2Ou findOrgUnit(String dlvryNbr, String rcvngOrgUnit) {
		return dlvry2OuRepository.findBy(PrcmtDlvry2Ou.toId(dlvryNbr,
				rcvngOrgUnit));
	}

	public Long countBy(PrcmtDelivery entity,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDelivery> findByLike(PrcmtDelivery entity, int start,
			int max, SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDelivery entity,
			SingularAttribute<PrcmtDelivery, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDelivery attach(PrcmtDelivery entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<PrcmtDlvry2PO> listProcOrders(String dlvryNbr) {
		return dlvry2poRepository.findByDlvryNbr(dlvryNbr);
	}

	public List<PrcmtDlvry2Ou> listOrgUnits(String dlvryNbr) {
		return dlvry2OuRepository.findByDlvryNbr(dlvryNbr);
	}

	public PrcmtDelivery findByIdentif(String identif) {
		List<PrcmtDelivery> resultList = repository.findByIdentif(identif).maxResults(1).getResultList();
		if(resultList.isEmpty()) return null;
		return resultList.iterator().next();
	}
}
