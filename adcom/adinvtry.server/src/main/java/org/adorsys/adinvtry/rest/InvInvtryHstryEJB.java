package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adbase.enums.BaseHistoryTypeEnum;
import org.adorsys.adinvtry.event.InvInvtryClosedEvent;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.repo.InvInvtryHstryRepository;

@Stateless
public class InvInvtryHstryEJB {

	@Inject
	private InvInvtryHstryRepository repository;

	@Inject
	@InvInvtryClosedEvent
	private Event<InvInvtryHstry> invtryClosedEvent;

	public InvInvtryHstry create(InvInvtryHstry entity) {
		InvInvtryHstry invtryHstry = repository.save(attach(entity));

		if (BaseHistoryTypeEnum.CLOSED.name().equals(invtryHstry.getHstryType())){
			invtryClosedEvent.fire(invtryHstry);
		}

		return invtryHstry;
	}

	public InvInvtryHstry deleteById(String id) {
		InvInvtryHstry entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public InvInvtryHstry update(InvInvtryHstry entity) {
		return repository.save(attach(entity));
	}

	public InvInvtryHstry findById(String id) {
		return repository.findBy(id);
	}

	public List<InvInvtryHstry> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<InvInvtryHstry> findBy(InvInvtryHstry entity,
			int start, int max,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtryHstry entity,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<InvInvtryHstry> findByLike(InvInvtryHstry entity,
			int start, int max,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtryHstry entity,
			SingularAttribute<InvInvtryHstry, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private InvInvtryHstry attach(InvInvtryHstry entity) {
		if (entity == null)
			return null;

		return entity;
	}
}
