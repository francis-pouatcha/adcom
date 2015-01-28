package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2OuEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2OuEvtData.class)
public interface PrcmtDlvryItem2OuEvtDataRepository extends EntityRepository<PrcmtDlvryItem2OuEvtData, String>
{
	public List<PrcmtDlvryItem2OuEvtData> findByDlvryItemNbr(String dlvryItemNbr);
}
