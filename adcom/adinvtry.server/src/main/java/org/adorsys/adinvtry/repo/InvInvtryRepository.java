package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtry;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtry.class)
public interface InvInvtryRepository extends EntityRepository<InvInvtry, String>
{
	public QueryResult<InvInvtry> findByIdentif(String identif);
}
