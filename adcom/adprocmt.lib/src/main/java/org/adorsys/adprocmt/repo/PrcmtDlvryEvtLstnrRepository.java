package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDlvryEvtLstnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryEvtLstnr.class)
public interface PrcmtDlvryEvtLstnrRepository extends EntityRepository<PrcmtDlvryEvtLstnr, String>
{
}
