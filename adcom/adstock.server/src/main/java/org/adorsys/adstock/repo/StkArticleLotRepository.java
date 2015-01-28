package org.adorsys.adstock.repo;

import org.adorsys.adstock.jpa.StkArticleLot;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot.class)
public interface StkArticleLotRepository extends EntityRepository<StkArticleLot, String>
{
	@Query("SELECT e FROM StkArticleLot AS e WHERE e.identif = ?1")
	public QueryResult<StkArticleLot> findByIdentif(String identif);
}
