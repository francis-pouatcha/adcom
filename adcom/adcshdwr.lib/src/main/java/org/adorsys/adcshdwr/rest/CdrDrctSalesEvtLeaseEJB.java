package org.adorsys.adcshdwr.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvtLease;
import org.adorsys.adcshdwr.repo.CdrDrctSalesEvtLeaseRepository;

@Stateless
public class CdrDrctSalesEvtLeaseEJB {

	@Inject
	private CdrDrctSalesEvtLeaseRepository repository;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public CdrDrctSalesEvtLease create(CdrDrctSalesEvtLease entity) {
		return repository.save(attach(entity));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String recover(String processOwner, String leaseId) {
		CdrDrctSalesEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		lease.extend(processOwner);
		return repository.save(attach(lease)).getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRED)
	public String close(String processOwner, String leaseId) {
		CdrDrctSalesEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		if(lease.expired(new Date())) return null;
		lease.setValidTo(new Date());;
		return repository.save(attach(lease)).getId();
	}
	
	public CdrDrctSalesEvtLease deleteById(String id) {
		CdrDrctSalesEvtLease entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public CdrDrctSalesEvtLease update(CdrDrctSalesEvtLease entity) {
		return repository.save(attach(entity));
	}

	public CdrDrctSalesEvtLease findById(String id) {
		return repository.findBy(id);
	}

	public List<CdrDrctSalesEvtLease> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<CdrDrctSalesEvtLease> findBy(CdrDrctSalesEvtLease entity,
			int start, int max,
			SingularAttribute<CdrDrctSalesEvtLease, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(CdrDrctSalesEvtLease entity,
			SingularAttribute<CdrDrctSalesEvtLease, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<CdrDrctSalesEvtLease> findByLike(CdrDrctSalesEvtLease entity,
			int start, int max,
			SingularAttribute<CdrDrctSalesEvtLease, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(CdrDrctSalesEvtLease entity,
			SingularAttribute<CdrDrctSalesEvtLease, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private CdrDrctSalesEvtLease attach(CdrDrctSalesEvtLease entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<CdrDrctSalesEvtLease> findByEvtId(String evtId) {
		return repository.findByEvtId(evtId);
	}
	
	public List<CdrDrctSalesEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName){
		return repository.findByEvtIdAndHandlerName(evtId, handlerName);
	}
}
