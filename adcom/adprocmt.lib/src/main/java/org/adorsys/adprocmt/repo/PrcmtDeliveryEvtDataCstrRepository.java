package org.adorsys.adprocmt.repo;

import java.util.Date;

import org.adorsys.adprocmt.jpa.PrcmtDeliveryEvtDataCstr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDeliveryEvtDataCstr.class)
public interface PrcmtDeliveryEvtDataCstrRepository extends EntityRepository<PrcmtDeliveryEvtDataCstr, String>
{
	@Query("SELECT e FROM PrcmtDeliveryEvtDataCstr AS e WHERE e.entIdentif = ?1 AND e.cstrType = ?2")
	public QueryResult<PrcmtDeliveryEvtDataCstr> findByEntIdentifAndCstrType(String entIdentif, String cstrType);

	@Query("SELECT e FROM PrcmtDeliveryEvtDataCstr AS e WHERE e.cstrType = ?1 AND e.cstrDt <= ?2")
	public QueryResult<PrcmtDeliveryEvtDataCstr> findDeleted(String cstrType, Date cstrDt);
}
