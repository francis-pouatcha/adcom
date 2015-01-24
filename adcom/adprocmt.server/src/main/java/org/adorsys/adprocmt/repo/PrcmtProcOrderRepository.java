package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtProcOrder;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrder.class)
public interface PrcmtProcOrderRepository extends EntityRepository<PrcmtProcOrder, String>
{
}
