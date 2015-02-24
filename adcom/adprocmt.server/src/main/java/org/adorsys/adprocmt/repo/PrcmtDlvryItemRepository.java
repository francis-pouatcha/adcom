package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtDlvryItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtDlvryItem.class)
public interface PrcmtDlvryItemRepository extends EntityRepository<PrcmtDlvryItem, String>
{
	public List<PrcmtDlvryItem> findByDlvryNbrAndArtPic(String dlvryNbr, String artPic);

	
	@Query("SELECT e FROM PrcmtDlvryItem AS e WHERE e.dlvryNbr = ?1")
	public QueryResult<PrcmtDlvryItem> findByDlvryNbr(String dlvryNbr);


	@Query("SELECT COUNT(p) FROM PrcmtDlvryItem AS p WHERE p.dlvryNbr = ?1")
	public Long countByDlvryNbr(String dlvryNbr);


	@Query("SELECT e FROM PrcmtDlvryItem AS e WHERE e.identif = ?1")
	public QueryResult<PrcmtDlvryItem> findByIdentif(String identif);
}
