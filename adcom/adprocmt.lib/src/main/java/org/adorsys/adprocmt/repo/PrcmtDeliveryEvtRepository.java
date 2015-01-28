package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryEvt.class)
public interface PrcmtDeliveryEvtRepository extends EntityRepository<PrcmtDeliveryEvt, String>
{
	public List<PrcmtDeliveryEvt> findByLstnrNameAndEvtName(String lstnrName,String evtName);
}
