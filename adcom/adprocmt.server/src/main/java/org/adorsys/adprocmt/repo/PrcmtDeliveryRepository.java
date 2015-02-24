package org.adorsys.adprocmt.repo;

import org.adorsys.adprocmt.jpa.PrcmtDelivery;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDelivery.class)
public interface PrcmtDeliveryRepository extends EntityRepository<PrcmtDelivery, String>
{

	@Query("SELECT e FROM PrcmtDelivery AS e WHERE e.identif = ?1")
	public QueryResult<PrcmtDelivery> findByIdentif(String identif);
}
