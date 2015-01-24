package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem.class)
public interface PrcmtDlvryItemRepository extends EntityRepository<PrcmtDlvryItem, String>
{
}
