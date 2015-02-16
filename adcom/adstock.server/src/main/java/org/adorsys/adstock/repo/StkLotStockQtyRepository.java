package org.adorsys.adstock.repo;

import java.util.List;

import org.adorsys.adstock.jpa.StkLotStockQty;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkLotStockQty.class)
public interface StkLotStockQtyRepository extends EntityRepository<StkLotStockQty, String>
{
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1")
	public List<StkLotStockQty> findByArtPic(String artPic);
	
	@Query("SELECT e FROM StkLotStockQty AS e WHERE e.artPic = ?1 AND e.lotPic = ?2")
	public List<StkLotStockQty> findByArtPicAndLotPic(String artPic,String lotPic);
}
