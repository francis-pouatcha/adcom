package org.adorsys.adinvtry.repo;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtryEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryEvt.class)
public interface InvInvtryEvtRepository extends EntityRepository<InvInvtryEvt, String>
{
	public List<InvInvtryEvt> findByEvtName(String evtName);
}
