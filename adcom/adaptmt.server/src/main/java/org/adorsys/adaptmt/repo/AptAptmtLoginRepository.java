package org.adorsys.adaptmt.repo;

import org.adorsys.adaptmt.jpa.AptAptmtLogin;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = AptAptmtLogin.class)
public interface AptAptmtLoginRepository extends
		EntityRepository<AptAptmtLogin, String> {
	
	@Query("SELECT a from AptAptmtLogin AS a WHERE a.id > ?1 ORDER BY a.id ASC ")
	public QueryResult<AptAptmtLogin> findNextAptAptmtLogin(String id);

	@Query("SELECT a from AptAptmtLogin AS a WHERE a.id < ?1 ORDER BY a.id ASC")
	public QueryResult<AptAptmtLogin> findPreviousAptAptmtLogin(String id);
	
}
