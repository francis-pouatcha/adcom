package org.adorsys.adinvtry.repo;

import java.util.List;

import org.adorsys.adinvtry.jpa.InvInvtryHstry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryHstry.class)
public interface InvInvtryHstryRepository extends EntityRepository<InvInvtryHstry, String>
{
	public List<InvInvtryHstry> findByEntIdentif(String entIdentif);

	@Query("SELECT count(e.id) FROM InvInvtryHstry AS e WHERE e.entIdentif = ?1 AND e.entStatus=?2")
	public Long countByEntIdentifAndEntStatus(String entIdentif, String entStatus);
	
}
