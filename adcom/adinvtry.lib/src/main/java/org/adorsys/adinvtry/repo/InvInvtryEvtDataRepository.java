package org.adorsys.adinvtry.repo;

import org.adorsys.adinvtry.jpa.InvInvtryEvtData;
import org.apache.deltaspike.data.api.EntityRepository;
import org.apache.deltaspike.data.api.QueryResult;
import org.apache.deltaspike.data.api.Repository;

@Repository(forEntity = InvInvtryEvtData.class)
public interface InvInvtryEvtDataRepository extends EntityRepository<InvInvtryEvtData, String>
{
	public QueryResult<InvInvtryEvtData> findByIdentif(String identif);
}
