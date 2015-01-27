package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItemEvtData.class)
public interface PrcmtDlvryItemEvtDataRepository extends EntityRepository<PrcmtDlvryItemEvtData, String>
{
}
