package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtPrcmtEvt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPrcmtEvt.class)
public interface PrcmtPrcmtEvtRepository extends EntityRepository<PrcmtPrcmtEvt, String>
{
}
