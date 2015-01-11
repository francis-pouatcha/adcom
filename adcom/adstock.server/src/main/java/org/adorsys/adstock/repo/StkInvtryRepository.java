package org.adorsys.adstock.repo;

import java.util.Date;

import org.adorsys.adstock.jpa.StkInvtry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkInvtry.class)
public interface StkInvtryRepository extends EntityRepository<StkInvtry, String>
{
	@Query("SELECT e FROM StkInvtry AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<StkInvtry> findByIdentif(String identif, Date validOn);
}
