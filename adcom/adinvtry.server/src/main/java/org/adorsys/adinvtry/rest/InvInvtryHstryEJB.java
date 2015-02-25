package org.adorsys.adinvtry.rest;

import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.adorsys.adinvtry.jpa.InvInvtryEvtLstnr;
import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.adorsys.adinvtry.repo.InvInvtryHstryRepository;

@Stateless
public class InvInvtryHstryEJB {

	@Inject
	private InvInvtryHstryRepository repository;
	
	@Inject
	private InvInvtryEvtLstnrEJB evtLstnrEJB;
	
	@Inject
	private InvInvtryEvtEJB evtEJB;

	public InvInvtryHstry create(InvInvtryHstry entity) {
		InvInvtryHstry deliveryHstry = repository.save(attach(entity));
		String evtName = deliveryHstry.getHstryType();
		List<InvInvtryEvtLstnr> found = evtLstnrEJB.findByEvtName(evtName);
		for (InvInvtryEvtLstnr evtLstnr : found) {
			InvInvtryEvt evt = new InvInvtryEvt();
			deliveryHstry.copyTo(evt);
			evt.setEvtName(evtName);
			evt.setLstnrName(evtLstnr.getLstnrName());
			evt.setId(deliveryHstry.getId() + "_" + evtLstnr.getId());
			evtEJB.create(evt);
		}
		return deliveryHstry;
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
