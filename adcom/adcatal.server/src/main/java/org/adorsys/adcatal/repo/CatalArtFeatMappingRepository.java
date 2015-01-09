package org.adorsys.adcatal.repo;

import java.util.Date;

import org.adorsys.adcatal.jpa.CatalArtFeatMapping;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArtFeatMapping.class)
public interface CatalArtFeatMappingRepository extends EntityRepository<CatalArtFeatMapping, String>
{
	@Query("SELECT e FROM CatalArtFeatMapping AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<CatalArtFeatMapping> findByIdentif(String identif, Date validOn);
}
