package org.adorsys.adcshdwr.repo;

import java.util.List;

import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvtLease;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesEvtLease.class)
public interface CdrDrctSalesEvtLeaseRepository extends
		EntityRepository<CdrDrctSalesEvtLease, String> {

	public List<CdrDrctSalesEvtLease> findByEvtId(String evtId);

	public List<CdrDrctSalesEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName);
}
