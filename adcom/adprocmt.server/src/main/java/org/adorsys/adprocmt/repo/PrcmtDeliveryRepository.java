package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDelivery.class)
public interface PrcmtDeliveryRepository extends EntityRepository<PrcmtDelivery, String>
{
}
