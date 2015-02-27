package org.adorsys.adstock.repo;

import java.util.List;

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotStockQty.class)
public interface StkLotStockQtyRepository extends EntityRepository<StkLotStockQty, String>
{
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1")
	public List<StkLotStockQty> findByArtPic(String artPic);
	
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPic(String artPic,String lotPic);

	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.cnsldtd=?3")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndCnsldtd(String artPic,String lotPic, Boolean cnsldtd);

	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2 AND e.seqNbr > ?3")
	public QueryResult<StkLotStockQty> findByArtPicAndLotPicAndSeq(String artPic,String lotPic, int seqNbr);
}
