package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItemEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItemEvtData.class)
public interface PrcmtDlvryItemEvtDataRepository extends EntityRepository<PrcmtDlvryItemEvtData, String>
{
	@Query("SELECT count(e.id) FROM PrcmtDlvryItemEvtData AS e WHERE e.dlvryNbr = ?1")
	public Long countByDlvryNbr(String dlvryNbr);

	@Query("SELECT e FROM PrcmtDlvryItemEvtData AS e WHERE e.dlvryNbr = ?1")
	public QueryResult<PrcmtDlvryItemEvtData> findByDlvryNbr(String dlvryNbr);
}
