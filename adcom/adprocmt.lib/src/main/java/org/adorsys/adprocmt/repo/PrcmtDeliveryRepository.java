package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryEvtData.class)
public interface PrcmtDeliveryRepository extends EntityRepository<PrcmtDeliveryEvtData, String>
{
}
