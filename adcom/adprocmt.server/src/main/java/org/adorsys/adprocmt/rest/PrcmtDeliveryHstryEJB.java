package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.jpa.PrcmtDlvryEvtLstnr;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryRepository;

@Stateless
public class PrcmtDeliveryHstryEJB {

	@Inject
	private PrcmtDeliveryHstryRepository repository;
	
	@Inject
	private PrcmtDlvryEvtLstnrEJB evtLstnrEJB;
	
	@Inject
	private PrcmtDeliveryEvtEJB evtEJB;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<PrcmtDeliveryHstry> deliveryClosingEvent;

	public PrcmtDeliveryHstry create(PrcmtDeliveryHstry entity) {
		PrcmtDeliveryHstry deliveryHstry = repository.save(attach(entity));
		String evtName = deliveryHstry.getHstryType();
		List<PrcmtDlvryEvtLstnr> found = evtLstnrEJB.findByEvtName(evtName);
		for (PrcmtDlvryEvtLstnr evtLstnr : found) {
			PrcmtDeliveryEvt evt = new PrcmtDeliveryEvt();
			deliveryHstry.copyTo(evt);
			evt.setEvtName(evtName);
			evt.setLstnrName(evtLstnr.getLstnrName());
			evt.setId(deliveryHstry.getId() + "_" + evtLstnr.getId());
			evtEJB.create(evt);
		}
		deliveryClosingEvent.fire(deliveryHstry);
		return deliveryHstry;
	}

	public PrcmtDeliveryHstry deleteById(String id) {
		PrcmtDeliveryHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public PrcmtDeliveryHstry update(PrcmtDeliveryHstry entity) {
		return repository.save(attach(entity));
	}

	public PrcmtDeliveryHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<PrcmtDeliveryHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDeliveryHstry> findBy(PrcmtDeliveryHstry entity,
			int start, int max,
			SingularAttribute<PrcmtDeliveryHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDeliveryHstry entity,
			SingularAttribute<PrcmtDeliveryHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDeliveryHstry> findByLike(PrcmtDeliveryHstry entity,
			int start, int max,
			SingularAttribute<PrcmtDeliveryHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDeliveryHstry entity,
			SingularAttribute<PrcmtDeliveryHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDeliveryHstry attach(PrcmtDeliveryHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
