package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtLease;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryEvtLease.class)
public interface PrcmtDeliveryEvtLeaseRepository extends
		EntityRepository<PrcmtDeliveryEvtLease, String> {

	public List<PrcmtDeliveryEvtLease> findByEvtId(String evtId);

	public List<PrcmtDeliveryEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName);
}
