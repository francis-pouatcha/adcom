package org.adorsys.adcshdwr.repo;

import java.util.Date;

import org.adorsys.adcshdwr.jpa.CdrDrctSalesEvtDataCstr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDrctSalesEvtDataCstr.class)
public interface CdrDrctSalesEvtDataCstrRepository extends EntityRepository<CdrDrctSalesEvtDataCstr, String>
{
	@Query("SELECT e FROM CdrDrctSalesEvtDataCstr AS e WHERE e.entIdentif = ?1 AND e.cstrType = ?2")
	public QueryResult<CdrDrctSalesEvtDataCstr> findByEntIdentifAndCstrType(String entIdentif, String cstrType);

	@Query("SELECT e FROM CdrDrctSalesEvtDataCstr AS e WHERE e.cstrType = ?1 AND e.cstrDt <= ?2")
	public QueryResult<CdrDrctSalesEvtDataCstr> findDeleted(String cstrType, Date cstrDt);
}
