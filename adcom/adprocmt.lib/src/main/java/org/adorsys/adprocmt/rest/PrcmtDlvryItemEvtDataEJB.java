package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2OuEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItemEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctnEvtData;
import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2OuEvtDataRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2POItemEvtDataRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItem2StrgSctnEvtDataRepository;
import org.adorsys.adprocmt.repo.PrcmtDlvryItemEvtDataRepository;

@Stateless
public class PrcmtDlvryItemEvtDataEJB {

	@Inject
	private PrcmtDlvryItemEvtDataRepository repository;
	@Inject
	private PrcmtDlvryItem2OuEvtDataRepository ouEvtDataRepository;
	@Inject
	private PrcmtDlvryItem2POItemEvtDataRepository poEvtDataRepository;
	@Inject
	private PrcmtDlvryItem2StrgSctnEvtDataRepository strgSctnEvtDataRepository;

	public PrcmtDlvryItemEvtData create(PrcmtDlvryItemEvtData entity) {
		return repository.save(attach(entity));
	}

	public PrcmtDlvryItem2OuEvtData addDlvryItem2OuEvtData(
			PrcmtDlvryItemEvtData dlvryItemEvtData,
			PrcmtDlvryItem2OuEvtData ouEvtData) {
		ouEvtData.setDlvryItemNbr(dlvryItemEvtData.getDlvryItemNbr());
		return ouEvtDataRepository.save(ouEvtData);
	}

	public PrcmtDlvryItem2POItemEvtData addDlvryItem2POItemEvtData(
			PrcmtDlvryItemEvtData dlvryItemEvtData,
			PrcmtDlvryItem2POItemEvtData poEvtData) {
		poEvtData.setDlvryItemNbr(dlvryItemEvtData.getDlvryItemNbr());
		return poEvtDataRepository.save(poEvtData);
	}

	public PrcmtDlvryItem2StrgSctnEvtData addPrcmtDlvryItem2StrgSctnEvtData(
			PrcmtDlvryItemEvtData dlvryItemEvtData,
			PrcmtDlvryItem2StrgSctnEvtData strgSctnEvtData) {
		strgSctnEvtData.setDlvryItemNbr(dlvryItemEvtData.getDlvryItemNbr());
		return strgSctnEvtDataRepository.save(strgSctnEvtData);
	}

	public PrcmtDlvryItemEvtData deleteById(String id) {
		PrcmtDlvryItemEvtData entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
			List<PrcmtDlvryItem2OuEvtData> ous = ouEvtDataRepository
					.findByDlvryItemNbr(entity.getDlvryItemNbr());
			for (PrcmtDlvryItem2OuEvtData ouEvtData : ous) {
				ouEvtDataRepository.remove(ouEvtData);
			}
			List<PrcmtDlvryItem2POItemEvtData> pos = poEvtDataRepository
					.findByDlvryItemNbr(entity.getDlvryItemNbr());
			for (PrcmtDlvryItem2POItemEvtData poEvtData : pos) {
				poEvtDataRepository.remove(poEvtData);
			}
			List<PrcmtDlvryItem2StrgSctnEvtData> strgs = strgSctnEvtDataRepository
					.findByDlvryItemNbr(entity.getDlvryItemNbr());
			for (PrcmtDlvryItem2StrgSctnEvtData strgSctnEvtData : strgs) {
				strgSctnEvtDataRepository.remove(strgSctnEvtData);
			}

		}
		return entity;
	}
	public PrcmtDlvryItem2POItemEvtData deletePoItem(String dlvryItemNbr, String poItemNbr){
		PrcmtDlvryItem2POItemEvtData po = poEvtDataRepository.findBy(PrcmtDlvryItem2POItemEvtData.toId(dlvryItemNbr, poItemNbr));
		if(po!=null)
			poEvtDataRepository.remove(po);
		return po;
	}
	
	public PrcmtDlvryItem2OuEvtData deleteOu(String dlvryItemNbr, String rcvngOrgUnit){
		PrcmtDlvryItem2OuEvtData ou = ouEvtDataRepository.findBy(PrcmtDlvryItem2OuEvtData.toId(dlvryItemNbr, rcvngOrgUnit));
		if(ou!=null)
			ouEvtDataRepository.remove(ou);
		return ou;
	}
	
	public PrcmtDlvryItem2StrgSctnEvtData deleteStrgSctn(String dlvryItemNbr, String strgSection){
		PrcmtDlvryItem2StrgSctnEvtData strgSctn = strgSctnEvtDataRepository.findBy(PrcmtDlvryItem2StrgSctnEvtData.toId(dlvryItemNbr, strgSection));
		if(strgSctn!=null)
			strgSctnEvtDataRepository.remove(strgSctn);
		return strgSctn;
	}

	public PrcmtDlvryItemEvtData update(PrcmtDlvryItemEvtData entity) {
		return repository.save(attach(entity));
	}
	public PrcmtDlvryItem2OuEvtData updateDlvryItem2OuEvtData(
			PrcmtDlvryItemEvtData dlvryItemEvtData,
			PrcmtDlvryItem2OuEvtData ouEvtData) {
		PrcmtDlvryItem2OuEvtData evtData = ouEvtDataRepository.findBy(PrcmtDlvryItem2OuEvtData.toId(ouEvtData.getDlvryItemNbr(), ouEvtData.getRcvngOrgUnit()));
		if(evtData==null)return ouEvtData;
		ouEvtData.copyTo(evtData);
		return ouEvtDataRepository.save(evtData);
	}

	public PrcmtDlvryItem2POItemEvtData updateDlvryItem2POItemEvtData(
			PrcmtDlvryItemEvtData dlvryItemEvtData,
			PrcmtDlvryItem2POItemEvtData poEvtData) {
		PrcmtDlvryItem2POItemEvtData evtData = poEvtDataRepository.findBy(PrcmtDlvryItem2POItemEvtData.toId(poEvtData.getDlvryItemNbr(), poEvtData.getPoItemNbr()));
		if(evtData==null) return poEvtData;
		poEvtData.copyTo(evtData);
		return poEvtDataRepository.save(evtData);
	}

	public PrcmtDlvryItem2StrgSctnEvtData updatePrcmtDlvryItem2StrgSctnEvtData(
			PrcmtDlvryItemEvtData dlvryItemEvtData,
			PrcmtDlvryItem2StrgSctnEvtData strgSctnEvtData) {
		PrcmtDlvryItem2StrgSctnEvtData evtData = strgSctnEvtDataRepository.findBy(PrcmtDlvryItem2StrgSctnEvtData.toId(strgSctnEvtData.getDlvryItemNbr(), strgSctnEvtData.getStrgSection()));
		if(evtData==null) return strgSctnEvtData;
		strgSctnEvtData.copyTo(evtData);
		return strgSctnEvtDataRepository.save(evtData);
	}

	public PrcmtDlvryItemEvtData findById(String id) {
		return repository.findBy(id);
	}
	public PrcmtDlvryItem2OuEvtData findDlvryItem2OuEvtData(String dlvryItemNbr, String rcvngOrgUnit) {
		return ouEvtDataRepository.findBy(PrcmtDlvryItem2OuEvtData.toId(dlvryItemNbr, rcvngOrgUnit));
	}
	public PrcmtDlvryItem2POItemEvtData findDlvryItem2POItemEvtData(String dlvryItemNbr, String poItemNbr) {
		return poEvtDataRepository.findBy(PrcmtDlvryItem2POItemEvtData.toId(dlvryItemNbr, poItemNbr));
	}
	public PrcmtDlvryItem2StrgSctnEvtData findDlvryItem2StrgSctnEvtData(String dlvryItemNbr, String strgSection) {
		return strgSctnEvtDataRepository.findBy(PrcmtDlvryItem2StrgSctnEvtData.toId(dlvryItemNbr, strgSection));
	}

	public List<PrcmtDlvryItemEvtData> listAll(int start, int max) {
		return repository.findAll(start, max);
	}
	public List<PrcmtDlvryItem2OuEvtData> listDlvryItem2OuEvtData(String dlvryItemNbr) {
		return ouEvtDataRepository.findByDlvryItemNbr(dlvryItemNbr);
	}
	public List<PrcmtDlvryItem2POItemEvtData> listDlvryItem2POItemEvtData(String dlvryItemNbr) {
		return poEvtDataRepository.findByDlvryItemNbr(dlvryItemNbr);
	}
	public List<PrcmtDlvryItem2StrgSctnEvtData> listDlvryItem2StrgSctnEvtData(String dlvryItemNbr) {
		return strgSctnEvtDataRepository.findByDlvryItemNbr(dlvryItemNbr);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDlvryItemEvtData> findBy(PrcmtDlvryItemEvtData entity,
			int start, int max,
			SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDlvryItemEvtData entity,
			SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDlvryItemEvtData> findByLike(PrcmtDlvryItemEvtData entity,
			int start, int max,
			SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDlvryItemEvtData entity,
			SingularAttribute<PrcmtDlvryItemEvtData, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDlvryItemEvtData attach(PrcmtDlvryItemEvtData entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public Long countByDlvryNbr(String dlvryNbr) {
		return repository.countByDlvryNbr(dlvryNbr);
	}

	public List<PrcmtDlvryItemEvtData> findByDlvryNbr(String dlvryNbr,
			int start, int max) {
		return repository.findByDlvryNbr(dlvryNbr).firstResult(start)
				.maxResults(max).getResultList();
	}

	public List<String> findIdByDlvryNbr(String dlvryNbr,
			int start, int max) {
		return repository.findIdByDlvryNbr(dlvryNbr).firstResult(start)
				.maxResults(max).getResultList();
	}

	public void deleteByDlvryNbr(String dlvryNbr, int max) {
		List<String> dlvryNbrs = findIdByDlvryNbr(dlvryNbr, 0, max);
		for (String dlvryItemId : dlvryNbrs) {
			deleteById(dlvryItemId);
		}
	}
}
