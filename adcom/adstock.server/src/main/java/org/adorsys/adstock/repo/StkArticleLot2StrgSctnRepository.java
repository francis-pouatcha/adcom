package org.adorsys.adstock.repo;

import java.util.List;

import org.adorsys.adstock.jpa.StkArticleLot2StrgSctn;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot2StrgSctn.class)
public interface StkArticleLot2StrgSctnRepository extends EntityRepository<StkArticleLot2StrgSctn, String>
{
	public List<StkArticleLot2StrgSctn> findByArtPicAndLotPic(String artPic, String lotPic);
}
