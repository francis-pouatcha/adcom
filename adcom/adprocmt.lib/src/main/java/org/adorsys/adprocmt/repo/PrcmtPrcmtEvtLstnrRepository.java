package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtPrcmtEvtLstnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPrcmtEvtLstnr.class)
public interface PrcmtPrcmtEvtLstnrRepository extends EntityRepository<PrcmtPrcmtEvtLstnr, String>
{
}
