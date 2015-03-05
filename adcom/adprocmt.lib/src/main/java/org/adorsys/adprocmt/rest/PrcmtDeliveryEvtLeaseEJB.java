package org.adorsys.adprocmt.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtLease;
import org.adorsys.adprocmt.repo.PrcmtDeliveryEvtLeaseRepository;

@Stateless
public class PrcmtDeliveryEvtLeaseEJB {

	@Inject
	private PrcmtDeliveryEvtLeaseRepository repository;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public PrcmtDeliveryEvtLease create(PrcmtDeliveryEvtLease entity) {
		return repository.save(attach(entity));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String recover(String processOwner, String leaseId) {
		PrcmtDeliveryEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		lease.extend(processOwner);
		return repository.save(attach(lease)).getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String close(String processOwner, String leaseId) {
		PrcmtDeliveryEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		if(lease.expired(new Date())) return null;
		lease.setValidTo(new Date());;
		return repository.save(attach(lease)).getId();
	}
	
	public PrcmtDeliveryEvtLease deleteById(String id) {
		PrcmtDeliveryEvtLease entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public PrcmtDeliveryEvtLease update(PrcmtDeliveryEvtLease entity) {
		return repository.save(attach(entity));
	}

	public PrcmtDeliveryEvtLease findById(String id) {
		return repository.findBy(id);
	}

	public List<PrcmtDeliveryEvtLease> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<PrcmtDeliveryEvtLease> findBy(PrcmtDeliveryEvtLease entity,
			int start, int max,
			SingularAttribute<PrcmtDeliveryEvtLease, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(PrcmtDeliveryEvtLease entity,
			SingularAttribute<PrcmtDeliveryEvtLease, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<PrcmtDeliveryEvtLease> findByLike(PrcmtDeliveryEvtLease entity,
			int start, int max,
			SingularAttribute<PrcmtDeliveryEvtLease, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(PrcmtDeliveryEvtLease entity,
			SingularAttribute<PrcmtDeliveryEvtLease, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private PrcmtDeliveryEvtLease attach(PrcmtDeliveryEvtLease entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<PrcmtDeliveryEvtLease> findByEvtId(String evtId) {
		return repository.findByEvtId(evtId);
	}
	
	public List<PrcmtDeliveryEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName){
		return repository.findByEvtIdAndHandlerName(evtId, handlerName);
	}
}
