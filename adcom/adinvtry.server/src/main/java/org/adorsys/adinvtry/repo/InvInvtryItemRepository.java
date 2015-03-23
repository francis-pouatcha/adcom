package org.adorsys.adinvtry.repo;

import java.util.List;

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

	@Query("SELECT COUNT(e.id) FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1")
	public Long countByInvtryNbr(String invtryNbr);

	@Modifying
	@Query("DELETE e FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1")
	public Long removeByInvtryNbr(String invtryNbr);

	@Query("SELECT e FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1 AND e.section=?2")
	public QueryResult<InvInvtryItem> findByInvtryNbrAndSection(String invtryNbr, String section);

	@Query("SELECT COUNT(e.id) FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1 AND e.section=?2")
	public Long countByInvtryNbrAndSection(String invtryNbr, String section);

	@Query("SELECT e FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1 AND LOWER(SUBSTRING(e.artName,1,1))>=?2 AND LOWER(SUBSTRING(e.artName,0,1))<=?3")
	public QueryResult<InvInvtryItem> findByInvtryNbrInRange(String invtryNbr, String rangeStart, String rangeEnd);

	@Query("SELECT count(e.id) FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1 AND LOWER(SUBSTRING(e.artName,1,1))>=?2 AND LOWER(SUBSTRING(e.artName,0,1))<=?3")
	public Long countByInvtryNbrInRange(String invtryNbr, String rangeStart, String rangeEnd);

	@Query("SELECT e FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1 AND e.section=?2 AND LOWER(SUBSTRING(e.artName,1,1))>=?3 AND LOWER(SUBSTRING(e.artName,0,1))<=?4")
	public QueryResult<InvInvtryItem> findByInvtryNbrAndSection(
			String invtryNbr, String section, String rangeStart, String rangeEnd);

	@Query("SELECT count(e.id) FROM InvInvtryItem AS e WHERE e.invtryNbr = ?1 AND e.section=?2 AND LOWER(SUBSTRING(e.artName,1,1))<=?3 AND LOWER(SUBSTRING(e.artName,0,1))<=?4")
	public Long countByInvtryNbrAndSection(String invtryNbr, String section,
			String rangeStart, String rangeEnd);

	public List<InvInvtryItem> findByIdentif(String identif);

	@Query("SELECT DISTINCT e.salIndex FROM InvInvtryItem AS e WHERE e.invtryNbr IN ?1")	
	public QueryResult<String> salIndexForInvtrys(List<String> invNbrs);  

	@Query("SELECT e FROM InvInvtryItem AS e WHERE e.salIndex=?1 AND e.invtryNbr IN ?2")	
	public QueryResult<InvInvtryItem> bySalIndexForInvtrys(String salIndex, List<String> invNbrs);  
}
