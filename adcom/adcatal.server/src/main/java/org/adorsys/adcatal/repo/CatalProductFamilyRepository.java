package org.adorsys.adcatal.repo;

import org.adorsys.adcatal.jpa.CatalProductFamily;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalProductFamily.class)
public interface CatalProductFamilyRepository extends EntityRepository<CatalProductFamily, String>
{
	@Query("SELECT e FROM CatalProductFamily AS e WHERE e.identif = ?1")
	public QueryResult<CatalProductFamily> findByIdentif(String identif);
}
