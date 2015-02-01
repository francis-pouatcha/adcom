package org.adorsys.adcatal.repo;

import java.util.List;

import org.adorsys.adcatal.jpa.CatalArt2ProductFamily;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArt2ProductFamily.class)
public interface CatalArt2ProductFamilyRepository extends EntityRepository<CatalArt2ProductFamily, String>
{
	@Query("SELECT e FROM CatalArt2ProductFamily AS e WHERE e.identif = ?1")
	public QueryResult<CatalArt2ProductFamily> findByIdentif(String identif);

	public List<CatalArt2ProductFamily> findByArtPic(String artPic);

	public List<CatalArt2ProductFamily> findByFamCode(String famCode);

	public List<CatalArt2ProductFamily> findByFamCodeAndLangIso2(
			String famCode, String langIso2);
}
