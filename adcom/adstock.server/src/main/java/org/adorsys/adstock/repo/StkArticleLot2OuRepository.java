package org.adorsys.adstock.repo;

import java.util.List;

import org.adorsys.adstock.jpa.StkArticleLot2Ou;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = StkArticleLot2Ou.class)
public interface StkArticleLot2OuRepository extends EntityRepository<StkArticleLot2Ou, String>
{
	public List<StkArticleLot2Ou> findByArtPicAndLotPic(String artPic, String lotPic);
}
