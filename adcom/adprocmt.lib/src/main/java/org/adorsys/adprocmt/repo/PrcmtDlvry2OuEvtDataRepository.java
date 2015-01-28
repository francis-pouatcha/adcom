package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvry2OuEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvry2OuEvtData.class)
public interface PrcmtDlvry2OuEvtDataRepository extends EntityRepository<PrcmtDlvry2OuEvtData, String>
{
	public List<PrcmtDlvry2OuEvtData> findByDlvryNbr(String dlvryNbr);
}
