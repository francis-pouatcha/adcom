package org.adorsys.adcatal.repo;

import java.util.List;

import org.adorsys.adcatal.jpa.CatalFamilyFeatMaping;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalFamilyFeatMaping.class)
public interface CatalFamilyFeatMapingRepository extends EntityRepository<CatalFamilyFeatMaping, String>
{
	@Query("SELECT e FROM CatalFamilyFeatMaping AS e WHERE e.identif = ?1")
	public QueryResult<CatalFamilyFeatMaping> findByIdentif(String identif);

	public List<CatalFamilyFeatMaping> findByPfIdentif(String pfIdentif);
}
