package org.adorsys.adinvtry.repo;

import java.util.Date;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtry.class)
public interface InvInvtryRepository extends EntityRepository<InvInvtry, String>
{
	public QueryResult<InvInvtry> findByIdentif(String identif);
	
	@Query("SELECT e FROM InvInvtry AS e WHERE e.invtryDt BETWEEN ?1 AND ?2")
	public QueryResult<InvInvtry> findByInvtryDtBtw(Date from, Date to);
	
	@Query("SELECT COUNT(e) FROM InvInvtry AS e WHERE e.invtryDt BETWEEN ?1 AND ?2")
	public Long countByInvtryDtBtw(Date from, Date to);

	@Query("SELECT e FROM InvInvtry AS e WHERE e.preparedDt IS NULL")
	public QueryResult<InvInvtry> findPreparingInvtrys();

	@Query("SELECT e FROM InvInvtry AS e WHERE e.containerId IS NOT NULL AND e.mergedDate IS NULL")
	public QueryResult<InvInvtry> findMergingInvtrys();

	@Query("SELECT e FROM InvInvtry AS e WHERE e.containerId IS NOT NULL AND e.mergedDate IS NOT NULL")
	public QueryResult<InvInvtry> findMergedInvtrys();
}
