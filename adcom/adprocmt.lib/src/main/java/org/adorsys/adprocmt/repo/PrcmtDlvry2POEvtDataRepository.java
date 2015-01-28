package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvry2POEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvry2POEvtData.class)
public interface PrcmtDlvry2POEvtDataRepository extends EntityRepository<PrcmtDlvry2POEvtData, String>
{
	public List<PrcmtDlvry2POEvtData> findByDlvryNbr(String dlvryNbr);
}
