package org.adorsys.adbase.repo;

import java.math.BigDecimal;

import org.adorsys.adbase.jpa.Login;
import org.adorsys.adbase.jpa.LoginRebate;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.Query;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = LoginRebate.class)
public interface LoginRebateRepository extends
		EntityRepository<LoginRebate, String> {

	@Query("SELECT e FROM LoginRebate AS e WHERE e.identif = ?1")
	public QueryResult<LoginRebate> findByIdentif(String identif);

	@Query("SELECT e FROM LoginRebate AS e WHERE e.loginName = ?1")
	public QueryResult<LoginRebate> findByLogin(String loginName);

	@Query("SELECT e FROM LoginRebate AS e WHERE e.maxRebate = ?1")
	public QueryResult<LoginRebate> findByMaxRebate(BigDecimal maxRebate);

	@Query("SELECT l from LoginRebate AS l WHERE l.identif > ?1 ORDER BY l.identif ASC ")
	public QueryResult<LoginRebate> findNextLoginRebate(String identif);

	@Query("SELECT l from LoginRebate AS l WHERE l.identif < ?1 ORDER BY l.identif ASC ")
	public QueryResult<LoginRebate> findPreviousLoginRebate(String identif);

}
