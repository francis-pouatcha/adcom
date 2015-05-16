package org.adorsys.adcshdwr.repo;

import org.adorsys.adcshdwr.jpa.CdrDsArtItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CdrDsArtItem.class)
public interface CdrDsArtItemRepository extends EntityRepository<CdrDsArtItem, String>
{
	
	public QueryResult<CdrDsArtItem> findByDsNbr(String dsNbr);

	@Query("SELECT c FROM CdrDsArtItem AS c GROUP BY c.artPic ORDER BY SUM(c.soldQty) DESC")
	public QueryResult<CdrDsArtItem> topSales();
}
