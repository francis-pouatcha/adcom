package org.adorsys.adinvtry.repo;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtryEvtLstnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryEvtLstnr.class)
public interface InvInvtryEvtLstnrRepository extends EntityRepository<InvInvtryEvtLstnr, String>
{

	public List<InvInvtryEvtLstnr> findByEvtName(String evtName);
}
