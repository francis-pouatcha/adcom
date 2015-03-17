package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtryItemEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryItemEvtData.class)
public interface InvInvtryItemEvtDataRepository extends EntityRepository<InvInvtryItemEvtData, String>
{
	@Query("SELECT count(e.id) FROM InvInvtryItemEvtData AS e WHERE e.invtryNbr = ?1")
	public Long countByInvtryNbr(String invtryNbr);

	@Query("SELECT e FROM InvInvtryItemEvtData AS e WHERE e.invtryNbr = ?1")
	public QueryResult<InvInvtryItemEvtData> findByInvtryNbr(String invtryNbr);

	@Query("SELECT e.id FROM InvInvtryItemEvtData AS e WHERE e.invtryNbr = ?1")
	public QueryResult<String> findIdByInvtryNbr(String invtryNbr);
}
