package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpCtgryDscnt;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpCtgryDscnt.class)
public interface BpCtgryDscntRepository extends EntityRepository<BpCtgryDscnt, String>
{
	@Query("SELECT e FROM BpCtgryDscnt AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpCtgryDscnt> findByIdentif(String identif, Date validOn);
}
