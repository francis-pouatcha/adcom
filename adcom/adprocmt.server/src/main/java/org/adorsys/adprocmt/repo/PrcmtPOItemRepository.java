package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPOItem.class)
public interface PrcmtPOItemRepository extends EntityRepository<PrcmtPOItem, String>
{
}
