package org.adorsys.adcatal.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adcatal.jpa.CatalArtDetailConfig;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtDetailConfig.class)
public interface CatalArtDetailConfigRepository extends EntityRepository<CatalArtDetailConfig, String>
{
	@Query("SELECT e FROM CatalArtDetailConfig AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<CatalArtDetailConfig> findByIdentif(String identif, Date validOn);
	

	@Query("SELECT e FROM CatalArtDetailConfig AS e WHERE e.pic = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public List<CatalArtDetailConfig> findByArtPicAndIdentif(String pic, Date validOn);
}
