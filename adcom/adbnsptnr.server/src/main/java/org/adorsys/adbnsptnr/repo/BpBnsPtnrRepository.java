package org.adorsys.adbnsptnr.repo;

import java.util.Date;

import org.adorsys.adbnsptnr.jpa.BpBnsPtnr;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = BpBnsPtnr.class)
public interface BpBnsPtnrRepository extends EntityRepository<BpBnsPtnr, String>
{
	@Query("SELECT e FROM BpBnsPtnr AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<BpBnsPtnr> findByIdentif(String identif, Date validOn);
}
