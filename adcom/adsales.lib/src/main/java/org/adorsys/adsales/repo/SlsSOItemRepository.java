package org.adorsys.adsales.repo;

import org.adorsys.adsales.jpa.SlsSOItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = SlsSOItem.class)
public interface SlsSOItemRepository extends EntityRepository<SlsSOItem, String>
{
	@Query("SELECT s FROM SlsSOItem AS s WHERE s.soNbr = ?1")
	public QueryResult<SlsSOItem> findBySoNbr(String soNbr);
}
