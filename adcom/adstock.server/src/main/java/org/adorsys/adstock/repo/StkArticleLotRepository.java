package org.adorsys.adstock.repo;

import java.util.List;

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
	
	@Query("SELECT e FROM StkArticleLot AS e WHERE LOWER(e.artPic) LIKE(LOWER(?1))")
	public List<StkArticleLot> findByArtPicLike(String artPick);

	@Query("SELECT e.lotPic FROM StkArticleLot AS e WHERE e.artPic=?1")
	public List<String> findLotPicByArtPic(String artPic);

	@Query("SELECT e.id FROM StkArticleLot AS e WHERE e.dlvryNbr=?1")
	public List<String> findIdByDlvryNbr(String dlvryNbr);

	@Query("SELECT count(e.id) FROM StkArticleLot AS e WHERE e.dlvryNbr=?1")
	public Long countByDlvryNbr(String dlvryNbr);

	@Query("SELECT e.id FROM StkArticleLot AS e WHERE e.dlvryItemNbr=?1")
	public List<String> findIdByDlvryItemNbr(String dlvryItemNbr);
}
