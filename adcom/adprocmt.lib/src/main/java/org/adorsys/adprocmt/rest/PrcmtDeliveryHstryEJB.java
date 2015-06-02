package org.adorsys.adprocmt.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosedEvent;
import org.adorsys.adprocmt.event.PrcmtDeliveryClosingEvent;
import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.adorsys.adprocmt.repo.PrcmtDeliveryHstryRepository;

@Stateless
public class PrcmtDeliveryHstryEJB {

	@Inject
	private PrcmtDeliveryHstryRepository repository;
	
	@Inject
	@PrcmtDeliveryClosingEvent
	private Event<PrcmtDeliveryHstry> deliveryClosingEvent;

	@Inject
	@PrcmtDeliveryClosedEvent
	private Event<PrcmtDeliveryHstry> deliveryClosedEvent;
	
	public PrcmtDeliveryHstry create(PrcmtDeliveryHstry entity) {
		PrcmtDeliveryHstry deliveryHstry = repository.save(attach(entity));
		if(BaseHistoryTypeEnum.CLOSING.name().equals(deliveryHstry.getHstryType())){
			deliveryClosingEvent.fire(deliveryHstry);
		} else if (BaseHistoryTypeEnum.CLOSED.name().equals(deliveryHstry.getHstryType())){
			deliveryClosedEvent.fire(deliveryHstry);
		}
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
