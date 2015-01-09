package org.adorsys.adcatal.repo;

import java.util.Date;

import org.adorsys.adcatal.jpa.CatalPkgMode;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalPkgMode.class)
public interface CatalPkgModeRepository extends EntityRepository<CatalPkgMode, String>
{
	@Query("SELECT e FROM CatalPkgMode AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<CatalPkgMode> findByIdentif(String identif, Date validOn);
}
