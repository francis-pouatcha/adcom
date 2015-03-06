package org.adorsys.adinvtry.repo;

import java.util.Date;

import org.adorsys.adinvtry.jpa.InvInvtryEvtDataCstr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryEvtDataCstr.class)
public interface InvInvtryEvtDataCstrRepository extends EntityRepository<InvInvtryEvtDataCstr, String>
{
	@Query("SELECT e FROM InvInvtryEvtDataCstr AS e WHERE e.entIdentif = ?1 AND e.cstrType = ?2")
	public QueryResult<InvInvtryEvtDataCstr> findByEntIdentifAndCstrType(String entIdentif, String cstrType);

	@Query("SELECT e FROM InvInvtryEvtDataCstr AS e WHERE e.cstrType = ?1 AND e.cstrDt <= ?2")
	public QueryResult<InvInvtryEvtDataCstr> findDeleted(String cstrType, Date cstrDt);
}
