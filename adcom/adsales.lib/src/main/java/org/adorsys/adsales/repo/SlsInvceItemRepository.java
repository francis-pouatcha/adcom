package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsInvceItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsInvceItem.class)
public interface SlsInvceItemRepository extends EntityRepository<SlsInvceItem, String>
{
	@Query("SELECT s FROM SlsInvceItem AS s WHERE s.invNbr = ?1")
	public QueryResult<SlsInvceItem> findByInvceNbr(String invNbr);
}
