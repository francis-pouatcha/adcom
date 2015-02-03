package org.adorsys.adcatal.repo;

import java.util.Date;
import java.util.List;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adcatal.jpa.CatalArticle;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = CatalArticle.class)
public interface CatalArticleRepository extends EntityRepository<CatalArticle, String>
{
	@Query("SELECT e FROM CatalArticle AS e WHERE e.identif = ?1 AND e.validFrom <= ?2 AND (e.validTo IS NULL OR e.validTo > ?2)")
	public QueryResult<CatalArticle> findByIdentif(String identif, Date validOn);

	public List<CatalArticle> findByPic(String pic);

	@Query("SELECT c from CatalArticle AS c WHERE c.pic < ?1 ORDER BY c.pic ASC")
	public QueryResult<CatalArticle> findPrevious(String pic);
	
	@Query("SELECT c from CatalArticle AS c WHERE c.pic > ?1 ORDER BY c.pic ASC")
	public QueryResult<CatalArticle> findNext(String pic);
}
