package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtPOItemEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPOItemEvtData.class)
public interface PrcmtPOItemRepository extends EntityRepository<PrcmtPOItemEvtData, String>
{
}
