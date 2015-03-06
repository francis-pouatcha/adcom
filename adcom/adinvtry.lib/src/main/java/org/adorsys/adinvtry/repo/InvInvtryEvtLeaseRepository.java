package org.adorsys.adinvtry.repo;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtryEvtLease;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryEvtLease.class)
public interface InvInvtryEvtLeaseRepository extends
		EntityRepository<InvInvtryEvtLease, String> {

	public List<InvInvtryEvtLease> findByEvtId(String evtId);

	public List<InvInvtryEvtLease> findByEvtIdAndHandlerName(String evtId, String handlerName);
}
