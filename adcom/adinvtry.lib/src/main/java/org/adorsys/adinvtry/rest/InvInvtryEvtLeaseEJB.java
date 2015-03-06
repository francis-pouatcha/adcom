package org.adorsys.adinvtry.rest;

import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.persistence.metamodel.SingularAttribute;

import org.adorsys.adinvtry.jpa.InvInvtryEvtLease;
import org.adorsys.adinvtry.repo.InvInvtryEvtLeaseRepository;

@Stateless
public class InvInvtryEvtLeaseEJB {

	@Inject
	private InvInvtryEvtLeaseRepository repository;

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public InvInvtryEvtLease create(InvInvtryEvtLease entity) {
		return repository.save(attach(entity));
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String recover(String processOwner, String leaseId) {
		InvInvtryEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		lease.extend(processOwner);
		return repository.save(attach(lease)).getId();
	}

	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public String close(String processOwner, String leaseId) {
		InvInvtryEvtLease lease = findById(leaseId);
		if(lease==null) return null;
		if(lease.expired(new Date())) return null;
		lease.setValidTo(new Date());;
		return repository.save(attach(lease)).getId();
	}
	
	public InvInvtryEvtLease deleteById(String id) {
		InvInvtryEvtLease entity = repository.findBy(id);
		if (entity != null) {
			repository.remove(entity);
		}
		return entity;
	}

	public InvInvtryEvtLease update(InvInvtryEvtLease entity) {
		return repository.save(attach(entity));
	}

	public InvInvtryEvtLease findById(String id) {
		return repository.findBy(id);
	}

	public List<InvInvtryEvtLease> listAll(int start, int max) {
		return repository.findAll(start, max);
	}

	public Long count() {
		return repository.count();
	}

	public List<InvInvtryEvtLease> findBy(InvInvtryEvtLease entity,
			int start, int max,
			SingularAttribute<InvInvtryEvtLease, ?>[] attributes) {
		return repository.findBy(entity, start, max, attributes);
	}

	public Long countBy(InvInvtryEvtLease entity,
			SingularAttribute<InvInvtryEvtLease, ?>[] attributes) {
		return repository.count(entity, attributes);
	}

	public List<InvInvtryEvtLease> findByLike(InvInvtryEvtLease entity,
			int start, int max,
			SingularAttribute<InvInvtryEvtLease, ?>[] attributes) {
		return repository.findByLike(entity, start, max, attributes);
	}

	public Long countByLike(InvInvtryEvtLease entity,
			SingularAttribute<InvInvtryEvtLease, ?>[] attributes) {
		return repository.countLike(entity, attributes);
	}

	private InvInvtryEvtLease attach(InvInvtryEvtLease entity) {
		if (entity == null)
			return null;

		return entity;
	}

	public List<InvInvtryEvtLease> findByEvtId(String evtId) {
		return repository.findByEvtId(evtId);
	}
	
	public List<InvInvtryEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName){
		return repository.findByEvtIdAndHandlerName(evtId, handlerName);
	}
}
