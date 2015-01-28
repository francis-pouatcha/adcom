package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryEvtLstnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryEvtLstnr.class)
public interface PrcmtDlvryEvtLstnrRepository extends EntityRepository<PrcmtDlvryEvtLstnr, String>
{
	public List<PrcmtDlvryEvtLstnr> findByEvtName(String evtName);
}
