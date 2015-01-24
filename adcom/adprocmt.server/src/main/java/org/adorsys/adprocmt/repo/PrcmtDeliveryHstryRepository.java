package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryHstry.class)
public interface PrcmtDeliveryHstryRepository extends EntityRepository<PrcmtDeliveryHstry, String>
{
}
