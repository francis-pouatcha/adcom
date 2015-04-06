package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryHstry.class)
public interface InvInvtryHstryRepository extends EntityRepository<InvInvtryHstry, String>
{
	@Query("SELECT e FROM InvInvtryHstry AS e WHERE e.entIdentif = ?1")
	public QueryResult<InvInvtryHstry> byEntIdentif(String entIdentif);

	@Query("SELECT count(e.id) FROM InvInvtryHstry AS e WHERE e.entIdentif = ?1 AND e.entStatus=?2")
	public Long countByEntIdentifAndEntStatus(String entIdentif, String entStatus);
	
}
