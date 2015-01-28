package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2POItemEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2POItemEvtData.class)
public interface PrcmtDlvryItem2POItemEvtDataRepository extends EntityRepository<PrcmtDlvryItem2POItemEvtData, String>
{
	public List<PrcmtDlvryItem2POItemEvtData> findByDlvryItemNbr(String dlvryItemNbr);
}
