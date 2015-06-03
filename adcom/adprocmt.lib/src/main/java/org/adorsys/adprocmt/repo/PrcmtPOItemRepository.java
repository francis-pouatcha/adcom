package org.adorsys.adprocmt.repo;

import java.util.List;

import org.adorsys.adprocmt.jpa.PrcmtPOItem;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = PrcmtPOItem.class)
public interface PrcmtPOItemRepository extends EntityRepository<PrcmtPOItem, String>
{
	public List<PrcmtPOItem> findByPoNbrAndArtPic(String poNbr, String artPic);
	
	public QueryResult<PrcmtPOItem> findByPoNbr(String poNbr);

	public List<PrcmtPOItem> findByPoNbrAndArtPicAndRcvngOrgUnit(String poNbr,
			String artPic, String rcvngOrgUnit);

	public List<PrcmtPOItem> findByPoNbrAndArtPicAndStrgSection(String poNbr,
			String artPic, String strgSection);
}
