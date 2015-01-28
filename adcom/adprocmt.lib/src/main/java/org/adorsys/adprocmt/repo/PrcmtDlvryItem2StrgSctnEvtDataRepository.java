package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem2StrgSctnEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem2StrgSctnEvtData.class)
public interface PrcmtDlvryItem2StrgSctnEvtDataRepository extends EntityRepository<PrcmtDlvryItem2StrgSctnEvtData, String>
{
	public List<PrcmtDlvryItem2StrgSctnEvtData> findByDlvryItemNbr(String dlvryItemNbr);
}
