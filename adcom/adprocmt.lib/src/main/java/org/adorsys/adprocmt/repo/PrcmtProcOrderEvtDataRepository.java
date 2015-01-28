package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtProcOrderEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtProcOrderEvtData.class)
public interface PrcmtProcOrderEvtDataRepository extends EntityRepository<PrcmtProcOrderEvtData, String>
{
}
