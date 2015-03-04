package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtryItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Modifying;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryItem.class)
public interface InvInvtryItemRepository extends EntityRepository<InvInvtryItem, String> {
	
	@Query("SELECT e FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1")
	public QueryResult<InvInvtryItem> findByInvtryNbr(String invtryNbr);
/*
	@Query("SELECT COUNT(e) FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1")
	public Long countByInvtryNbr(String invtryNbr);*/

	@Modifying
	@Query("DELETE e FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1")
	public Long removeByInvtryNbr(String invtryNbr);
}
